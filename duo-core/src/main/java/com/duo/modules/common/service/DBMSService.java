package com.duo.modules.common.service;

import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseService;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.ShiroUtils;
import com.duo.core.utils.StringUtils;
import com.duo.modules.tool.entity.ToolDbsource;
import com.duo.modules.tool.entity.ToolTable;
import com.duo.modules.tool.entity.ToolTableColumn;
import com.duo.modules.tool.mapper.ToolTableColumnMapper;
import com.duo.modules.tool.mapper.ToolTableMapper;
import com.duo.modules.view.entity.ViewFactTableinfo;
import com.duo.modules.view.mapper.ViewFactTableinfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class DBMSService extends BaseService {
    @Autowired
    private ToolTableMapper toolTableMapper;
    @Autowired
    private ToolTableColumnMapper toolTableColumnMapper;
    @Autowired
    private ViewFactTableinfoMapper viewFactTableinfoMapper;

    public void syn(Map<String, Object> requestMap){
        String dbsource=(String)requestMap.get("dbsource");
        log.info("dbsource================"+dbsource);
        if(StringUtils.isNotEmpty(dbsource)){
            DynamicDataSource.setDataSource(dbsource);//设置指定数据源
            List<ViewFactTableinfo> columnList=viewFactTableinfoMapper.selectAll();
            System.out.println("columnList==="+columnList.get(0).getColumn_id());
            DynamicDataSource.clearDataSource();
            DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);//设置指定数据源
            int n=1;
            for(ViewFactTableinfo view:columnList){
                ToolTable table=toolTableMapper.selectByPrimaryKey(view.getTable_id());
                if(table==null) {
                    table=new ToolTable();
                    table.setTable_id(view.getTable_id());
                    table.setTable_name(view.getTable_name());
                    table.setTable_comment(view.getTable_comment());
                    table.setIs_tree("0");
                    table.setLevel(2);
                    table.setOrder_index(n++*10);
                    table.setProject_id(ShiroUtils.getProjectId());
                    toolTableMapper.insert(table);
                }
                ToolTableColumn column=toolTableColumnMapper.selectByPrimaryKey(view.getColumn_id());
                if(column==null){
                    column=new ToolTableColumn();
                    column.setColumn_id(view.getColumn_id());
                    column.setTable_id(view.getTable_id());
                    column.setColumn_name(view.getColumn_name());
                    column.setColumn_comment(view.getColumn_comment());
                    column.setColumn_type(view.getColumn_type());
                    column.setOrder_index(view.getOrder_index());
                    column.setIs_allownull(view.getIs_allownull());
                    column.setColumn_length(view.getColumn_length());
                    column.setIs_pk(view.getIs_pk());
                    column.setProject_id(ShiroUtils.getProjectId());
                    toolTableColumnMapper.insert(column);
                }
            }
            DynamicDataSource.clearDataSource();
        }else{
            String synSQL="INSERT INTO tool_table_column(table_id,column_id,column_name,column_comment,column_type,column_length,default_value,is_allownull,is_pk,order_index) " +
                    " SELECT table_id,CONCAT(table_id,column_name) AS column_id,column_name,column_comment,column_type,column_length,default_value,is_allownull,is_pk,order_index " +
                    " FROM view_fact_tableinfo v " +
                    " WHERE table_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM tool_table_column WHERE table_id=v.table_id AND column_name=v.column_name) ";
            execute(DataSourceNames.PLATFORM,synSQL,requestMap);
        }
    }
}
