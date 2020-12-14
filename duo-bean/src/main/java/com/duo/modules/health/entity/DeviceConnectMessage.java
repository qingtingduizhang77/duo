package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_connect_message")
public class DeviceConnectMessage extends BaseEntity{ 

    @Id
     private String message_id;//主键id

    private String device_id;//device_id

    private String connect_id;//connect_id

    private String connect_ip;//终端ip

    private java.util.Date connect_time;//日志时间

    private String is_sent;//是否下发?

    private String connect_message;//接收或发送信息

    private String is_callback;//是否反馈

    private String is_deal;//是否已处理

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }