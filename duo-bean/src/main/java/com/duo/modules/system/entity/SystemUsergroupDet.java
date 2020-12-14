package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_usergroup_det")
public class SystemUsergroupDet extends BaseEntity{ 

    @Id
     private String det_id;//主键id

    private String group_id;//group_id

    private String user_id;//user_id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }