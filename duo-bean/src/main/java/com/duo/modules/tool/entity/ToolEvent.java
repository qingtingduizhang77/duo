package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_event")
public class ToolEvent extends BaseEntity{ 

    @Id
     private String event_id;//event_id

    private Integer order_index;//排序

    private String event_code;//事件编号

    private String event_name;//事件名

    private String js_function;//前台方法

    private String page_type;//页面类型

    private String bo_function;//后台方法

    private String forward_function;//响应方法

    private String permission_type;//权限类型

    private String is_show;//是否显示?

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标记

    private String ico_img;//事件图标

    private String default_disabled;//是否默认不可编辑

    private String selection_type;//多选，单选控制

 }