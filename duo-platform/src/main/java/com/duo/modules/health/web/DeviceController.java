package com.duo.modules.health.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseEntity;
import com.duo.core.annotation.DataSource;
import com.duo.core.utils.DateUtils;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.RedisUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.PageResultSet;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.CommonQueryService;
import com.duo.modules.common.service.CommonService;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.health.entity.DeviceInfoCargo;
import com.duo.modules.health.entity.DeviceTypeCargo;
import com.duo.modules.health.mapper.DeviceInfoCargoMapper;
import com.duo.modules.health.mapper.DeviceTypeCargoMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author [ Yonsin ] on [2020/3/24]
 * @Description： [ 用于设备相关业务处理 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Controller
@RequestMapping("/health")
@Slf4j
public class DeviceController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private CommonQueryService commonQueryService;
    @Autowired
    private LayoutService layoutService;
    @Autowired
    private DeviceTypeCargoMapper deviceTypeCargoMapper;

    /**
     * 用于生成货架明细
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/makeCargo")
    public Result<?> makeCargo(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String device_id=(String)requestMap.get("device_id");//device_id
        String groupNum=(String)requestMap.get("groupNum");//组数
        String columnNum=(String)requestMap.get("columnNum");//列数
        String levelNum=(String)requestMap.get("levelNum");//行数
        if(device_id==null||groupNum==null||columnNum==null||levelNum==null){
            return Result.failure("500","参数缺失，请确认后再重新操作！");
        }

        String[] columns=columnNum.split(";");
        String[] levels=levelNum.split(";");
        String[] groupNums="ABCDEFG".split("");

        DynamicDataSource.setDataSource("health");
        for(int i=0;i<columns.length;i++){//组
             int column=Integer.parseInt(columns[i]);
             int level=Integer.parseInt(levels[i]);
             int code=1;

            for(int m=0;m<level;m++) {//行
                for(int n=0;n<column;n++) {//列
                    DeviceTypeCargo data = new DeviceTypeCargo();
                    data.setCargo_id(commonService.getKeyUID());
                    data.setType_id(device_id);
                    data.setGroup_name(groupNums[i]);
                    data.setCargo_level(m + 1);
                    data.setCargo_column(n+1);
                    data.setCargo_code(groupNums[i]+StringUtils.addZeroForNum(code++,2));
                    data.setMemo(groupNums[i]+"-"+StringUtils.addZeroForNum(m+1,2)+"-"+StringUtils.addZeroForNum(n+1,2));
                    data= (DeviceTypeCargo) commonService.addDataInfo(data);
                    log.info(data.getCargo_id());
                    deviceTypeCargoMapper.insertSelective(data);
                }
            }
        }
        DynamicDataSource.clearDataSource();
        return  Result.success();
    }
    /**
     * 用于生成指令
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/control")
    public Result<?> control(HttpServletRequest request) throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String ids=(String)requestMap.get("ids");
        String control_type=(String)requestMap.get("control_type");

        DynamicDataSource.setDataSource("health");
        Class clazz = MemCache._entitys.get("device_control_message");
        for (String id : ids.split(";")) {
            requestMap.put("message_id", commonQueryService.getKeyUID());
            requestMap.put("message_type", control_type);
            requestMap.put("plan_time", DateUtils.getDateTime());
            requestMap.put("auditing", "1");
            requestMap.put("device_id", id);
            BaseEntity entity = (BaseEntity) Map2EntityUtil.createModel(clazz, requestMap);
            layoutService.getMapper("device_control_message").insertSelective(entity);
        }
        DynamicDataSource.clearDataSource();
        return  Result.success();
    }
}