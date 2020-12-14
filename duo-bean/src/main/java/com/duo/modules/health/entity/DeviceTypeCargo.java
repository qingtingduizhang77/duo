package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_type_cargo")
public class DeviceTypeCargo extends BaseEntity{ 

    @Id
     private String cargo_id;//主键id

    private String type_id;//type_id

    private String group_name;//组

    private String cargo_code;//货道编号

    private Integer cargo_level;//层号

    private Integer cargo_column;//列号

    private String merge_column;//合并列

    private Integer safe_num;//安全库存

    private Integer max_num;//最大容量

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }