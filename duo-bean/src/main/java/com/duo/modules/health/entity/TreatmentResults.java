package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="treatment_results")
public class TreatmentResults extends BaseEntity{ 

    @Id
     private String results_id;//诊疗结果id

    private String results_name;//诊疗结果名称

    private String results_code;//名称首字母拼音

    private Integer results_level;//层级

    private Integer results_sort;//排序

    private String project_id;//项目id

    private String memo;//备注

    private String add_userid;//创建人id

    private Date add_date;//创建时间

    private String modify_userid;//修改人id

    private Date modify_date;//修改时间

    private String record_flag;//数据标识

 }