package com.duo.modules.health.model;

import com.duo.modules.health.entity.TreatmentRecord;
import lombok.Data;

@Data
public class WzRecordVO extends TreatmentRecord {
    private String office_room;
    private Integer service_status;//诊疗状态
}
