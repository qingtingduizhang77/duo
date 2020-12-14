package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="user_doctor_schedual")
public class UserDoctorSchedual extends BaseEntity{ 

    @Id
     private String schedual_id;//主键id

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date schedual_date;//日期

    private String doctor_id;//doctor_id

    private String week_day;//星期

    private String time_1;//8:00-8:30

    private String time_2;//8:30-9:00

    private String time_3;//9:00-9:30

    private String time_4;//9:30-10:00

    private String time_5;//10:00-10:30

    private String time_6;//10:30-11:00

    private String time_7;//11:00-11:30

    private String time_8;//11:30-12:00

    private String time_9;//12:00-12:30

    private String time_10;//12:30-13:00

    private String time_11;//13:00-13:30

    private String time_12;//13:30-14:00

    private String time_13;//14:00-14:30

    private String time_14;//14:30-15:00

    private String time_15;//15:00-15:30

    private String time_16;//15:30-16:00

    private String time_17;//16:00-16:30

    private String time_18;//16:30-17:00

    private String time_19;//17:00-17:30

    private String time_20;//17:30-18:00

    private String time_21;//18:00-18:30

    private String time_22;//18:30-19:00

    private String time_23;//19:00-19:30

    private String time_24;//19:30-20:00

    private String time_25;//20:00-20:30

    private String time_26;//20:30-21:00

    private String time_27;//21:00-21:30

    private String time_28;//21:30-22:00

    private String time_29;//22:00-22:30

    private String time_30;//22:30-23:00

    private String time_31;//23:00-23:30

    private String time_32;//23:30-24:00

    private String time_33;//0:00-0:30

    private String time_34;//0:30-1:00

    private String time_35;//1:00-1:30

    private String time_36;//1:30-2:00

    private String time_37;//2:00-2:30

    private String time_38;//2:30-3:00

    private String time_39;//3:00-3:30

    private String time_40;//3:30-4:00

    private String time_41;//4:00-4:30

    private String time_42;//4:30-5:00

    private String time_43;//5:00-5:30

    private String time_44;//5:30-6:00

    private String time_45;//6:00-6:30

    private String time_46;//6:30-7:00

    private String time_47;//7:00-7:30

    private String time_48;//7:30-8:00

    private String memo;//备注

    private Date add_date;//创建时间

    private String add_userid;//创建人id

    private String modify_userid;//修改人id

    private Date modify_date;//修改时间

    private String record_flag;//数据标识

 }