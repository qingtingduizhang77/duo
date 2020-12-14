package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_table_column")
public class ToolTableColumn extends BaseEntity{ 

    @Id
     private String column_id;//主键id

    private Integer order_index;//排序

    private String column_name;//字段名

    private String column_comment;//字段描述

    private String column_type;//字段类型

    private Integer column_length;//字段长度

    private String default_value;//默认值

    private String is_allownull;//可空？

    private String is_pk;//主键？

    private String is_has;//已建表？

    private String memo;//备注

    private String table_id;//table_id

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//记录标记

    private String tree_propertie;//树属性

 }