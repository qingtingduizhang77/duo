package com.duo.modules.view.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name="view_fact_tableinfo")
public class ViewFactTableinfo extends BaseEntity {
    @Id
    private String column_id;

    private String table_id;

    private String table_name;

    private String table_comment;

    private String column_name;

    private String column_comment;

    private String column_type;

    private Integer column_length;

    private String default_value;

    private String is_allownull;

    private String is_pk;

    private Integer order_index;
}