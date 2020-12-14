package com.duo.modules.health.model;

import com.duo.modules.health.entity.BasePosition;
import com.duo.modules.health.entity.DeviceInfo;
import com.duo.modules.health.entity.DeviceInfoPart;
import lombok.Data;

import java.util.List;

@Data
public class DeviceVO extends DeviceInfo {
    private BasePosition position;//地址位置
    private List<DeviceInfoPart> partList; //设备部件
}
