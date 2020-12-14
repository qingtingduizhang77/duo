package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="bi_visual_category")
public class BiVisualCategory extends BaseEntity{ 

    @Id
     private String id;//主键

    private String categoryKey;//种类key

    private String categoryValue;//种类名称

    private String isDeleted;//是否删除

 }