package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_function")
public class ToolFunction extends BaseEntity{ 

    @Id
     private String fun_id;//fun_id

    private String module_id;//模块名称

    private String fun_name;//功能名称

    private String fun_type;//功能类型

    private Integer order_index;//排序

    private String layout_url;//布局路径

    private String grid_html;//grid页面

    private String form_html;//form页面

    private String table_name;//数据表

    private String db_source;//数据源

    private String query_function;//数据查询方法

    private String key_column;//主键列

    private String from_sql;//from_sql

    private String where_sql;//where_sql

    private String orderby_sql;//orderby_sql

    private String is_show;//是否显示?

    private String sub_funs;//子功能

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    private String tree_function;//树数据获取方法

    private String tree_name;//树名字

    private String tree_forwordwhere;//点击树触发刷新条件

    private String audit_column;//复核字段

    private String foreign_column;//外键字段

    private String is_audit;//是否归档?

    private String tree_wheresql;//过滤语句

    private String tree_orderbysql;//orderBy语句

    private String define_js;//自定义附加js

    private String define_modal;//自定义Modal窗口

    private String tree_topid;//顶级id

    private String fun_parames;//其他自定义参数

    private String ico_img;//功能图标ico

    private String fun_descript;//功能详细描述

    private String form_map;//Form设计图

 }