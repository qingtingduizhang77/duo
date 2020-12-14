package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="bi_visual")
public class BiVisual extends BaseEntity{ 

    @Id
     private String id;//主键

    private String title;//标题名称

    private String backgroundUrl;//背景图片url

    private String category;//种类

    private String password;//密码

    private String createUser;//创建用户

    private String createDept;//创建部门

    private java.util.Date createTime;//创建时间

    private String updateUser;//更新人

    private java.util.Date updateTime;//更新时间

    private String status;//状态

    private String isDeleted;//是否删除

 }