package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_i18n")
public class ToolI18n extends BaseEntity{ 

    @Id
     private String language_id;//language_id

    private String language_type;//分类分组

    private String language_code;//语言代号

    private String i18n_default;//默认描述

    private String i18n_cn;//中文描述

    private String i18n_en;//英文描述

    private String i18n_tw;//繁体描述

    private String i18n_jp;//日文描述

    private String project_id;//项目id

    private String module_id;//module_id

    private String fun_id;//fun_id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }