package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="bi_visual_config")
public class BiVisualConfig extends BaseEntity{ 

    @Id
     private String id;//主键

    private String visualId;//visual_id

    private String detail;//detail

    private String component;//component

 }