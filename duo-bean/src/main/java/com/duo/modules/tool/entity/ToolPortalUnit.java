package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_portal_unit")
public class ToolPortalUnit extends BaseEntity{ 

    @Id
     private String unit_id;//主键id

    private String portal_id;//portal_id

    private String is_show;//是否显示

    private Integer location_top;//top

    private Integer location_left;//left

    private Integer location_width;//width

    private Integer location_height;//height

    private String title_hide;//隐藏标题

    private String unit_name;//组件名称

    private String unit_type;//组件类型

    private String unit_url;//url地址

    private String unit_parame1;//参数1

    private String unit_parame2;//参数2

    private String unit_parame3;//参数3

    private String unit_parame4;//参数4

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }