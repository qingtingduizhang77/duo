package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_majorfunc")
public class SystemMajorfunc extends BaseEntity{ 

    @Id
     private String major_id;//主键id

    private String user_id;//用户id

    private String fun_id;//fun_id

    private Integer hit_num;//次数

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }