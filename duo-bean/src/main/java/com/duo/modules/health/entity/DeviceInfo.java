package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "device_info")
public class DeviceInfo extends BaseEntity {

    @Id
    private String device_id;//主键id

    private String project_id;//项目id

    private String type_id;//type_id

    private String position_id;//点位_id

    private String position_code;//点位编号

    private String group_id;//group_id

    private String group_name;//设备分组

    /**
     * 机器状态
     * 0：机器故障
     * 1：空闲
     * 2：问诊中
     * 3：消毒作业中
     * 4：
     */
    private String auditing;//设备状态

    private String sell_ststue;//售卖状态

    private String online_ststue;//在线状态

    private Double temperature_value;//温度

    private Double humidity_value;//湿度

    private String soft_version;//软件版本

    private String advert_version;//广告版本

    private String dbm_value;//信号强度

    private String device_kind;//设备类型

    private String device_name;//设备名称

    private String device_code;//设备编号

    private String device_factory;//生产厂家

    private String device_type;//设备型号

    private String device_size;//设备规格

    private String screen_ratio;//分辨率

    private Double device_price;//出厂价

    private String system_version;//操作系统

    private java.util.Date out_date;//出厂日期

    private String mem_info;//内存

    private String pic_url;//图片

    private java.util.Date install_date;//安装日期

//    private String disk_info;//硬盘

    private java.util.Date use_date;//启用日期

    private String mem_use;//内存使用

    private Integer use_age;//使用寿命

    private String disk_use;//硬盘使用

    private Double device_weight;//设备重量(KG)

    private Double device_energy;//能耗(KWH/日)

    private Integer cargo_num;//货道容量

    private String has_stock;//是否有备库

    private String company_name;//运营商户

    private String company_id;//运营商户id

    private String fix_ip;//限制ip

    private String dept_name;//管理部门

    private String dept_id;//管理部门id

    private String temperature_status;//温控状态

    private String temperature_type;//温控模式

    private Double temperature_set;//目标温度

    private Double humidity_set;//目标湿度

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private String dor_opened;//是否已开门 0：否  1：是

}