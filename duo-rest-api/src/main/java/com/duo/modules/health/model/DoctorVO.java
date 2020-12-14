package com.duo.modules.health.model;

import com.duo.modules.health.entity.HospitalInfo;
import com.duo.modules.health.entity.UserDoctor;
import lombok.Data;

@Data
public class DoctorVO extends UserDoctor {
    private HospitalInfo hospital;
}
