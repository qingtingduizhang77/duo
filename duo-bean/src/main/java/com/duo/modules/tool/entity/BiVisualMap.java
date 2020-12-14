package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="bi_visual_map")
public class BiVisualMap extends BaseEntity{ 

    @Id
     private String id;//id

    private String name;//name

    private String data;//data

 }