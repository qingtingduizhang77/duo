package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="user_member")
public class UserMember extends BaseEntity{ 

    @Id
     private String member_id;//主键id

    private String rec_method;//取药方式

    private String project_id;//项目id

    private String rec_id;//默认收件地址id

    private String auditing;//激活状态

    private String member_name;//会员姓名

    private String sex;//性别

    private Date both_date;//出生日期

    private String address;//住址

    private String mobile_no;//手机号

    private String e_mail;//邮箱

    private String id_card;//身份证号

    private String social_card;//社保卡号

    private String img_url;//头像地址

    private String member_level;//会员级别

    private Integer usable_point;//可用积分

    private Integer total_point;//总积分

    private Integer order_num;//累计订单数

    private Double order_money;//累计金额

    private String nick_name;//昵称

    private String user_code;//登录帐号

    private String user_password;//登录密码

    private Date limit_date;//会员有效期

    private String memo;//备注

    private String add_userid;//创建人id

    private Date add_date;//创建时间

    private String modify_userid;//修改人id

    private Date modify_date;//修改时间

    private String record_flag;//数据标识

 }