package com.duo.modules.view.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="view_system_role")
public class ViewSystemRole extends BaseEntity{ 

    private String det_id;//主键

    @Id
     private String role_id;//role_id

    private String one_moduleid;//一级module_id

    private String one_module;//一级模块名称

    private String module_id;//module_id

    private String module_name;//模块名称

    private String fun_id;//fun_id

    private String fun_name;//功能名称

    private Integer order_index1;//排序1

    private Integer order_index2;//排序2

    private Integer order_index;//排序3

    private String dataright_sql;//数据权限SQL

    private String event_audit;//审批事件权限

    private String event_delete;//删除事件权限

    private String event_edit;//编辑事件权限

    private String event_print;//打印事件权限

    private String event_sign;//复核事件权限

    private String is_access;//访问权限

 }