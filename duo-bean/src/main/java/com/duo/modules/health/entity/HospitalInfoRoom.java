package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="hospital_info_room")
public class HospitalInfoRoom extends BaseEntity{ 

    @Id
     private String office_room_id;//科室id

    private String office_room;//科室名称

    private String room_id;//room_id

    private String hospital_id;//医院id

    private String parent_id;//父级id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }