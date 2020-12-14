package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_type")
public class DeviceType extends BaseEntity{ 

    @Id
     private String type_id;//主键id

    private String screen_ratio;//分辨率

    private String project_id;//项目id

    private String device_kind;//设备类型

    private String device_name;//设备名称

    private String device_factory;//生产厂家

    private String device_type;//设备型号

    private String device_size;//设备规格

    private Integer period_make;//备货周期

    private Integer use_age;//使用寿命

    private Double device_weight;//设备重量(KG)

    private Double device_energy;//能耗(KWH/日)

    private Integer cargo_num;//货道容量

    private String has_stock;//是否有备库

    private String temperature_status;//温控状态

    private String temperature_type;//温控模式

    private Double temperature_set;//目标温度

    private Double humidity_set;//目标湿度

     private String pic_url;//图片

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }