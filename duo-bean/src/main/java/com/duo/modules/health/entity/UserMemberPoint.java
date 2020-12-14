package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="user_member_point")
public class UserMemberPoint extends BaseEntity{ 

    @Id
     private String vary_id;//主键id

    private String project_id;//项目id

    private String vary_type;//变动类型

    private Integer pre_point;//原积分

    private Integer vary_point;//变动积分

    private Integer post_point;//变动后积分

    private String vary_source;//变动来源

    private String source_id;//变动来源id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }