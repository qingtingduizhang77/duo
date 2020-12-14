package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_connect")
public class DeviceConnect extends BaseEntity{ 

    @Id
     private String connect_id;//主键id

    private String device_id;//device_id

    private String connect_code;//终端号

    private String connect_ip;//终端ip

    private java.util.Date connect_time;//连接时间

    private String accept_connect;//是否允许

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }