package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="wf_data")
public class WfData extends BaseEntity{ 

    @Id
     private String data_id;//主键id

    private String project_id;//项目id

    private String fun_id;//功能id

    private String record_id;//业务表id

    private String wf_id;//wf_id

    private String user_id;//user_id

    private String user_name;//姓名

    private String dept_name;//部门

    private String dept_id;//dept_id

    private String post_name;//岗位

    private java.util.Date create_date;//创建时间

    private String data_no;//流程号

    private String data_title;//审批标题

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }