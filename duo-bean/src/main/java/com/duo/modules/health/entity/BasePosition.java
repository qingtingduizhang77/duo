package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="base_position")
public class BasePosition extends BaseEntity{ 

    @Id
     private String position_id;//主键id

    private String project_id;//项目id

    private String auditing;//状态

    private String position_code;//点位编号

    private String field_name;//所属区域

    private String field_id;//所属区域id

    private String address;//详细地址

    private String longitude;//经度

    private String latitude;//纬度

    private String position_property;//点位性质

    private String is_indoor;//是否室内

    private String provider_name;//所属业主

    private String provider_id;//所属业主id

    private java.util.Date contract_expire;//合同到期时间

    private String usable_area;//可用面积

    private String advantage;//位置特点

    private String video_id;//网络摄像头id

    private String owner_username;//责任人

    private String owner_userid;//责任人id

    private String dept_name;//管理部门

    private String dept_id;//管理部门id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }