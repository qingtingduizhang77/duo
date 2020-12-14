package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_sequence")
public class SystemSequence extends BaseEntity{ 

    @Id
     private String sequence_id;//表名或标识

    private Integer max_num;//次数

    private String project_id;//项目id

 }