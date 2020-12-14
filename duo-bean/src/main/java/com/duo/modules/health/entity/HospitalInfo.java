package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="hospital_info")
public class HospitalInfo extends BaseEntity{ 

    @Id
     private String hospital_id;//主键id

    private String hospital_name;//医院名称

    private String hospital_address;//医院地址

    private String hospital_level;//医院等级

    private String hospital_category;//医院类别

    private String hospital_telephone;//医院电话

    private String hospital_profile;//医院简介

    private String hospital_introduce;//医院详细介绍

    private String hospital_wesite;//医院官网

    private String field_name;//所属区域

    private String field_code;//所属区域编码

    private String field_id;//所属区域id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }