package com.duo.modules.common.service;

import com.alibaba.fastjson.JSONObject;
import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseEntity;
import com.duo.core.BaseService;
import com.duo.core.utils.E2MapUtil;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.Result;
import com.duo.modules.tool.entity.ToolFunctionColumn;
import com.duo.modules.tool.entity.ToolReportArea;
import com.duo.modules.tool.entity.ToolReportAreaColumn;
import com.duo.modules.tool.entity.ToolReportAreaParam;
import com.duo.modules.tool.mapper.ToolReportAreaColumnMapper;
import com.duo.modules.tool.mapper.ToolReportAreaMapper;
import com.duo.modules.tool.mapper.ToolReportAreaParamMapper;
import com.duo.modules.tool.mapper.ToolReportMapper;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2020/1/22]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Service
//@Transactional
public class ReportService extends BaseService {
    @Autowired
    private ToolReportMapper toolReportMapper;//
    @Autowired
    private ToolReportAreaMapper toolReportAreaMapper;//
    @Autowired
    private ToolReportAreaColumnMapper toolReportAreaColumnMapper;//
    @Autowired
    private ToolReportAreaParamMapper toolReportAreaParamMapper;//

    //获取区域列表
    public Result<?> getAreaList(Map<String, Object> requestMap){
        String report_id=(String)requestMap.get("report_id");
        if(StringUtils.isEmpty(report_id)) return Result.failure("500","report_id不能为空！");
        log.info("report_id============"+report_id);
        ToolReportArea query=new ToolReportArea();
        query.setReport_id(report_id);
        DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);
        List<ToolReportArea> dataList=toolReportAreaMapper.select(query);
        DynamicDataSource.clearDataSource();
        Result result=new Result();
        result.setData(dataList);
        result.setSuccess(true);
        return result;
    }

    //获取区域参数列表
    public Result<?> getAreaParamList(Map<String, Object> requestMap){
        String area_id=(String)requestMap.get("area_id");
        if(StringUtils.isEmpty(area_id)) return Result.failure("500","area_id不能为空！");
        log.info("area_id============"+area_id);
        ToolReportAreaParam query=new ToolReportAreaParam();
        query.setArea_id(area_id);
        DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);
        List<ToolReportAreaParam> dataList=toolReportAreaParamMapper.select(query);
        DynamicDataSource.clearDataSource();
        Result result=new Result();
        result.setData(dataList);
        result.setSuccess(true);
        return result;
    }

    //获取区域字段列表
    public Result<?> getAreaColumnList(Map<String, Object> requestMap){
        String area_id=(String)requestMap.get("area_id");
        if(StringUtils.isEmpty(area_id)) return Result.failure("500","area_id不能为空！");
        log.info("area_id============"+area_id);
        ToolReportAreaColumn query=new ToolReportAreaColumn();
        query.setArea_id(area_id);
        DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);
        List<ToolReportAreaColumn> dataList=toolReportAreaColumnMapper.select(query);
        DynamicDataSource.clearDataSource();
        Result result=new Result();
        result.setData(dataList);
        result.setSuccess(true);
        return result;
    }

    //新增/保存区域列表
    public Result<?> areaSave(Map<String, Object> requestMap){
        String datas= StringEscapeUtils.unescapeHtml((String)requestMap.get("datas"));// " 被转译 成了&quot问题
        log.info("areaSave datas::"+datas);
        List<Map> list = JSONObject.parseArray(datas,Map.class);
        DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);//设置指定数据源
        try {
            for (Map mp : list) {
                String area_id = (String) mp.get("area_id");
                boolean isNew=true;
                ToolReportArea entity=null;
                if (StringUtils.isNotEmpty(area_id)) {
                    //从数据库拿出对象
                    entity =   toolReportAreaMapper.selectByPrimaryKey(area_id);
                    if(entity!=null){
                        isNew=false;
                    }
                }

                if (isNew) {//新增数据
                    //获取newid
                    area_id = getKeyUID();
                    mp.put("area_id", area_id);
                    //根据功能配置，对页面无赋值字段设置默认值
                    mp=addDefaultValue2Map(mp);
                    entity =  Map2EntityUtil.createModel(ToolReportArea.class, mp);
                    entity = (ToolReportArea)addDataInfo(entity);
                    toolReportAreaMapper.insertSelective(entity);
                } else {//修改数据
                    //根据功能配置，对页面无赋值字段设置默认值
                    mp=addDefaultValue2Map(mp);
                    //根据页面最新值更新对象//初始化对象
                    entity =  Map2EntityUtil.updateModel(entity, mp);
                    entity =(ToolReportArea) modifyDataInfo(entity);
                    toolReportAreaMapper.updateByPrimaryKey(entity);

                }

            }
        }catch (Exception e){
            log.error("保存区域数据失败!",e);
            return Result.failure("500","保存数区域数据失败!");
        }finally {
            DynamicDataSource.clearDataSource();//清理
        }

        return Result.success();
    }


    //新增/保存区域参数列表
    public Result<?> areaParamSave(Map<String, Object> requestMap){
        String datas= StringEscapeUtils.unescapeHtml((String)requestMap.get("datas"));// " 被转译 成了&quot问题
        log.info("areaParamSave datas::"+datas);
        List<Map> list = JSONObject.parseArray(datas,Map.class);
        DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);//设置指定数据源
        try {
            for (Map mp : list) {
                String param_id = (String) mp.get("param_id");
                boolean isNew=true;
                ToolReportAreaParam entity=null;
                if (StringUtils.isNotEmpty(param_id)) {
                    //从数据库拿出对象
                    entity =   toolReportAreaParamMapper.selectByPrimaryKey(param_id);
                    if(entity!=null){
                        isNew=false;
                    }
                }

                if (isNew) {//新增数据
                    //获取newid
                    param_id = getKeyUID();
                    mp.put("param_id", param_id);
                    //根据功能配置，对页面无赋值字段设置默认值
                    mp=addDefaultValue2Map(mp);
                    entity =  Map2EntityUtil.createModel(ToolReportAreaParam.class, mp);
                    entity = (ToolReportAreaParam)addDataInfo(entity);
                    toolReportAreaParamMapper.insertSelective(entity);
                } else {//修改数据
                    //根据功能配置，对页面无赋值字段设置默认值
                    mp=addDefaultValue2Map(mp);
                    //根据页面最新值更新对象//初始化对象
                    entity =  Map2EntityUtil.updateModel(entity, mp);
                    entity =(ToolReportAreaParam) modifyDataInfo(entity);
                    toolReportAreaParamMapper.updateByPrimaryKey(entity);

                }

            }
        }catch (Exception e){
            log.error("保存区域参数数据失败!",e);
            return Result.failure("500","保存数区域参数数据失败!");
        }finally {
            DynamicDataSource.clearDataSource();//清理
        }

        return Result.success();
    }


    //新增/保存区域字段列表
    public Result<?> areaColumnSave(Map<String, Object> requestMap){
        String datas= StringEscapeUtils.unescapeHtml((String)requestMap.get("datas"));// " 被转译 成了&quot问题
        log.info("areaColumnSave datas::"+datas);
        List<Map> list = JSONObject.parseArray(datas,Map.class);
        DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);//设置指定数据源
        try {
            for (Map mp : list) {
                String column_id = (String) mp.get("column_id");
                boolean isNew=true;
                ToolReportAreaColumn entity=null;
                if (StringUtils.isNotEmpty(column_id)) {
                    //从数据库拿出对象
                    entity =   toolReportAreaColumnMapper.selectByPrimaryKey(column_id);
                    if(entity!=null){
                        isNew=false;
                    }
                }

                if (isNew) {//新增数据
                    //获取newid
                    column_id = getKeyUID();
                    mp.put("column_id", column_id);
                    //根据功能配置，对页面无赋值字段设置默认值
                    mp=addDefaultValue2Map(mp);
                    entity =  Map2EntityUtil.createModel(ToolReportAreaColumn.class, mp);
                    entity = (ToolReportAreaColumn)addDataInfo(entity);
                    toolReportAreaColumnMapper.insertSelective(entity);
                } else {//修改数据
                    //根据功能配置，对页面无赋值字段设置默认值
                    mp=addDefaultValue2Map(mp);
                    //根据页面最新值更新对象//初始化对象
                    entity =  Map2EntityUtil.updateModel(entity, mp);
                    entity =(ToolReportAreaColumn) modifyDataInfo(entity);
                    toolReportAreaColumnMapper.updateByPrimaryKey(entity);

                }

            }
        }catch (Exception e){
            log.error("保存区域字段数据失败!",e);
            return Result.failure("500","保存数区域字段数据失败!");
        }finally {
            DynamicDataSource.clearDataSource();//清理
        }

        return Result.success();
    }


    //删除区域
    public Result<?> deleteArea(Map<String, Object> requestMap){
        String area_id=(String)requestMap.get("area_id");
        if(StringUtils.isEmpty(area_id)) return Result.failure("500","area_id不能为空！");
        DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);
        toolReportAreaMapper.deleteByPrimaryKey(area_id);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //删除区域参数
    public Result<?> deleteAreaParam(Map<String, Object> requestMap){
        String param_id=(String)requestMap.get("param_id");
        if(StringUtils.isEmpty(param_id)) return Result.failure("500","param_id不能为空！");
        DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);
        toolReportAreaParamMapper.deleteByPrimaryKey(param_id);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //删除区域字段
    public Result<?> deleteAreaColumn(Map<String, Object> requestMap){
        String column_id=(String)requestMap.get("column_id");
        if(StringUtils.isEmpty(column_id)) return Result.failure("500","column_id不能为空！");
        DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);
        toolReportAreaColumnMapper.deleteByPrimaryKey(column_id);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }
}
