package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_info_cargo_store")
public class DeviceInfoCargoStore extends BaseEntity{ 

    @Id
     private String store_id;//主键id

    private String device_id;//device_id

    private String cargo_id;//cargo_id

    private String goods_id;//goods_id

    private String store_code;//仓库号

    private Integer store_num;//库存数量

    private Integer machine_num;//实际数量

    private Double sell_price;//售价

    private Double goods_price;//指导价

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }