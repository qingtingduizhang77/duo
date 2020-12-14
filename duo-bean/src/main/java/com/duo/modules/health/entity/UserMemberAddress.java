package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="user_member_address")
public class UserMemberAddress extends BaseEntity{ 

    @Id
     private String rec_id;//配送地址id

    private String rec_user;//收件人姓名

    private String rec_phone;//收件人手机号

    private String rec_address;//收件地址

    private String rec_postcode;//邮编

    private String rec_default;//是否默认

    private java.util.Date create_time;//创建时间

    private String member_id;//用户id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }