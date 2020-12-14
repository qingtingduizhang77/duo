package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="wf_data_nodeuser")
public class WfDataNodeuser extends BaseEntity{ 

    @Id
     private String wf_userid;//主键id

    private String step_no;//第几步

    private String fun_id;//功能id

    private String data_id;//data_id

    private String wf_id;//wf_id

    private String node_id;//node_id

    private String node_no;//节点号

    private String node_name;//节点名称

    private String node_type;//节点类型

    private String user_id;//user_id

    private String user_name;//姓名

    private String dept_name;//部门

    private String dept_id;//dept_id

    private String is_sponsor;//主办人?

    private String is_operator;//经办人?

    private String data_status;//状态

    private String entrust_user;//委托给姓名

    private String entrust_userid;//委托给user_id

    private String is_agree;//是否同意

    private java.util.Date sign_date;//会签时间

    private String sign_advise;//会签意见

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }