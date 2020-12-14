package com.duo.eam;

import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.utils.ShiroUtils;
import com.duo.modules.common.service.DBMSService;
import com.duo.modules.tool.entity.ToolDbsource;
import com.duo.modules.tool.entity.ToolTable;
import com.duo.modules.tool.entity.ToolTableColumn;
import com.duo.modules.tool.mapper.ToolTableColumnMapper;
import com.duo.modules.tool.mapper.ToolTableMapper;
import com.duo.modules.view.entity.ViewFactTableinfo;
import com.duo.modules.view.mapper.ViewFactTableinfoMapper;
//import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 将EAM系统的表结构导入新系统
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
public class DBMSImportTool {
    @Autowired
    private ViewFactTableinfoMapper viewFactTableinfoMapper;
    @Autowired
    private ToolTableMapper toolTableMapper;
    @Autowired
    private ToolTableColumnMapper toolTableColumnMapper;

//    @Test
    public void importDBMS() throws Exception {
        //数据源设置
        ToolDbsource dbsource=new ToolDbsource();
        dbsource.setDbsource_name("eam");
        dbsource.setJdbc_url("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
        dbsource.setDb_driver("oracle.jdbc.OracleDriver");//oracle.jdbc.OracleDriver  //oracle.jdbc.driver.OracleDriver
        dbsource.setUser_name("eam2020");
        dbsource.setUser_password("eam");
        DynamicDataSource.managerDbSource(dbsource);

        //获取数据
    DynamicDataSource.setDataSource("eam");//设置指定数据源
    List<ViewFactTableinfo> columnList=viewFactTableinfoMapper.selectAll();
    System.out.println("columnList==="+columnList.get(0).getColumn_id());
    DynamicDataSource.clearDataSource();

    DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);//设置指定数据源
    int n=1;
    for(ViewFactTableinfo view:columnList){
        ToolTable table=toolTableMapper.selectByPrimaryKey(view.getTable_id());
        if(table==null) {//插入表，表id在2001下
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
        if(column==null){//插入字段
            System.out.println("columnid==="+view.getColumn_id());
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
    }
}


/**
 * 需在在Oracle库下还原这个视图
 *
 create or replace view view_fact_tableinfo as
 select '2001'||t.table_id||lower( user_tab_columns.column_name) as column_id,'2001'||t.table_id as table_id,t.comments as table_comment,
 lower(user_tab_columns.TABLE_NAME) as table_name,lower( user_tab_columns.column_name) as column_name,
 case when lower(data_type)='date' then 'datetime' when lower(data_type)='number' then 'float' when lower(data_type)='clob' then 'text' else 'varchar' end column_type,case when nullable='N' then '0' else '1' end is_allownull,
 column_id*5 as order_index ,user_tab_columns. user_col_comments.comments as column_comment ,user_tab_columns.DATA_LENGTH as column_length,
 -- user_tab_columns.DATA_DEFAULT as default_value,
 ''  as default_value,
 case when user_ind_columns.INDEX_NAME is null then '0' else '1' end is_pk

 from user_tab_columns left join user_col_comments on user_tab_columns.TABLE_NAME=user_col_comments.table_name and user_tab_columns.COLUMN_NAME=user_col_comments.COLUMN_NAME
 left join user_ind_columns on user_ind_columns.table_name=user_tab_columns.TABLE_NAME and user_tab_columns.COLUMN_NAME=user_ind_columns.COLUMN_NAME
 left join   USER_CONSTRAINTS  on user_ind_columns.index_name=USER_CONSTRAINTS.constraint_name and  USER_CONSTRAINTS.constraint_type='P'
 left join (select 1000+rownum as table_id, user_tables.table_name ,user_tab_comments.comments  from user_tables,user_tab_comments where user_tables.TABLE_NAME=user_tab_comments.table_name) t on user_tab_columns.TABLE_NAME=t.table_name
 order  by table_name
 ;

 */