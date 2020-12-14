package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_info_part")
public class DeviceInfoPart extends BaseEntity{ 

    @Id
     private String part_id;//主键id

    private String device_id;//device_id

    private String part_code;//部件编号

    private String part_name;//部件名称

    private String part_status;//部件状态

    private String part_descript;//部件描述

    private String part_factory;//厂家

    private String part_type;//型号

    private String part_size;//规格

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }