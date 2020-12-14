package com.duo.modules.common.web;

import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseController;
import com.duo.core.BaseEntity;
import com.duo.core.annotation.DataSource;
import com.duo.core.utils.*;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.POIService;
import com.duo.modules.system.entity.SystemAttachment;
import com.duo.modules.tool.entity.ToolExcelImport;
import com.duo.modules.tool.entity.ToolExcelImportColumn;
import com.duo.modules.tool.entity.ToolFunction;
import com.duo.modules.tool.entity.ToolFunctionColumn;
import com.duo.modules.tool.mapper.ToolExcelImportMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Controller
@Slf4j
@RequestMapping("/excel")
public class POIController extends BaseController {

    @Autowired
    POIService poiService;

    /**
     * 上传文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/import")
    public Result<?> importExcel(HttpServletRequest request) throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        String funId= (String) requestMap.get("funId");
        //判断参数是否为空
        if ( StringUtils.isEmpty(funId) ) {
            return Result.failure("500", "上传参数funId为空，请检查再上传！");
        }

        for (MultipartFile file:fileMap.values()) {
            ToolFunction funInfo = layoutService.getFunctionInfo(funId);
            log.info("导入Excel文件==="+file.getName());
            //判断文件是否为空
            if (file.isEmpty()) {
                log.error("导入Excel文件不可为空");
                throw new Exception("导入Excel文件不可为空");
            }

            if (funInfo == null || StringUtils.isEmpty(funInfo.getTable_name())) {
                log.error("excel导入配置信息有误，Funid为空或不正确！");
                throw new Exception("excel导入配置信息有误，Funid为空或不正确！");
            }
            ToolExcelImport importSet = poiService.getExcelImportInfo(funId);
            if (importSet == null) {
                log.error("没有找到excel导入配置信息");
                throw new Exception("没有找到excel导入配置信息");
            }


            if (poiService.importMain(file, funInfo, importSet)) {
                log.error("导入Excel文件出错");
                throw new Exception("导入Excel文件出错！");
            }
        }
        return Result.success();
    }
}
