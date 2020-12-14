package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="treatment_resource_doctor")
public class TreatmentResourceDoctor extends BaseEntity{ 

    @Id
     private String resourcedoctor_id;//主键id

    private String project_id;//项目id

    private String auditing;//发布状态

    private String doctor_name;//医生名称

    private String doctor_id;//医生id

    private java.util.Date reserve_date;//预约日期

    private Integer time_1;//8:00-8:30

    private Integer time_2;//8:30-9:00

    private Integer time_3;//9:00-9:30

    private Integer time_4;//9:30-10:00

    private Integer time_5;//10:00-10:30

    private Integer time_6;//10:30-11:00

    private Integer time_7;//11:00-11:30

    private Integer time_8;//11:30-12:00

    private Integer time_9;//13:30-14:00

    private Integer time_10;//14:00-14:30

    private Integer time_11;//14:30-15:00

    private Integer time_12;//15:00-15:30

    private Integer time_13;//15:30-16:00

    private Integer time_14;//16:00-16:30

    private Integer time_15;//16:30-17:00

    private Integer time_16;//17:00-17:30

    private Integer time_17;//17:30-18:00

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }