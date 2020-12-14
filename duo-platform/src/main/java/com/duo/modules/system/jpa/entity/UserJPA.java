package com.duo.modules.system.jpa.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "sys_user")
public class UserJPA extends BaseEntity {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 所属公司
     */
    @NotNull(message = "所属组织不能为空")
    private Long organizationId;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 加密密码的盐
     */
    private String salt;
    /**
     * 拥有的角色列表
     */
    private String roleIds;

    @Transient
    private List<Long> roleIdList;

    private String groupIds;

    @Transient
    private List<Long> groupIdList;

    private Boolean locked = Boolean.FALSE;

    public UserJPA(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getCredentialsSalt() {
        return username + salt;
    }


    public void setRoleIds(String roleIds) {
        String[] roleIdStrs = roleIds.split(",");
        for (String roleId : roleIdStrs) {
            if (StringUtils.isEmpty(roleId)) {
                continue;
            }
            getRoleIdList().add(Long.valueOf(roleId));
        }
        this.roleIds = roleIds;
    }

    public List<Long> getRoleIdList() {
        if (roleIdList == null) {
            roleIdList = new ArrayList<Long>();
        }
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        StringBuilder s = new StringBuilder();
        for (Long roleId : roleIdList) {
            s.append(roleId);
            s.append(",");
        }
        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }
        this.roleIds = s.toString();
        this.roleIdList = roleIdList;
    }


    public void setGroupIds(String groupIds) {
        String[] groupIdStrs = groupIds.split(",");
        for (String groupId : groupIdStrs) {
            if (StringUtils.isEmpty(groupId)) {
                continue;
            }
            getGroupIdList().add(Long.valueOf(groupId));
        }
        this.groupIds = groupIds;
    }

    public List<Long> getGroupIdList() {
        if (groupIdList == null) {
            groupIdList = new ArrayList<Long>();
        }
        return groupIdList;
    }

    public void setGroupIdList(List<Long> groupIdList) {
        StringBuilder s = new StringBuilder();
        for (Long groupId : groupIdList) {
            s.append(groupId);
            s.append(",");
        }
        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }
        this.groupIds = s.toString();
        this.groupIdList = groupIdList;
    }


}
