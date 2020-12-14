package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_table_index")
public class ToolTableIndex extends BaseEntity {

    @Id
    private String index_id;

    private String table_id;

    private String index_name;

    private String index_desc;

    private String column_id;

    private String column_name;

    private String memo;

    private Integer order_index;

    private String add_userid;

    private Date add_date;

    private Date modify_date;

    private String modify_userid;

    private String record_flag;
}