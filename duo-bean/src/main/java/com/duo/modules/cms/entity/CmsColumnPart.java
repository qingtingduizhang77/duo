package com.duo.modules.cms.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="cms_column_part")
public class CmsColumnPart extends BaseEntity{ 

    @Id
     private String det_id;//主键id

    private String column_id;//column_id

    private String part_id;//part_id

    private String part_code;//网格编号

    private String part_name;//网格名称

    private String content_id;//内容id

    private String content_title;//内容标题

    private String content_body;//文本内容

    private String part_param;//网格参数

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }