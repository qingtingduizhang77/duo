package com.duo.modules.health.service;

import com.duo.core.utils.*;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.health.entity.*;
import com.duo.modules.health.mapper.*;
import com.duo.modules.health.model.DeviceVO;
import com.duo.modules.health.model.DiagnoseVO;
import com.duo.modules.health.model.ManInfoVO;
import com.duo.modules.tool.entity.ToolData;
import com.duo.modules.tool.mapper.ToolDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Slf4j
@Service
public class TerminalService {

    @Autowired(required = false)
    public DeviceConnectMapper deviceConnectMapper;

    @Autowired(required = false)
    public AdvertContentMapper advertContentMapper;

    @Autowired(required = false)
    public UserMemberMapper userMemberMapper;

    @Autowired(required = false)
    public UserMemberAddressMapper userMemberAddressMapper;

    @Autowired(required = false)
    public UserMemberManMapper userMemberManMapper;

    @Autowired(required = false)
    public HospitalInfoMapper hospitalInfoMapper;

    @Autowired(required = false)
    public HospitalRoomMapper officeRoomMapper;

    @Autowired(required = false)
    public UserDoctorMapper userDoctorMapper;

    @Autowired(required = false)
    public DeviceInfoMapper deviceInfoMapper;

    @Autowired
    public LayoutService layoutService;

    @Autowired(required = false)
    public BasePositionMapper basePositionMapper;

    @Autowired(required = false)
    public TreatmentRecordMapper treatmentRecordMapper;

    @Autowired(required = false)
    public TreatmentRecordGoodsMapper treatmentRecordGoodsMapper;

    @Autowired(required = false)
    public OrderRecordMapper orderRecordMapper;

    @Autowired(required = false)
    public OrderRecordGoodsMapper orderRecordGoodsMapper;

    @Autowired(required = false)
    public TreatmentReserveMapper treatmentReserveMapper;

    @Autowired(required = false)
    public ToolDataMapper toolDataMapper;

    @Autowired
    public CommService commService;

    public DeviceInfo checkConnect(String code, String password) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDevice_code(code);
//        deviceInfo.setRecord_flag(password);
        return this.deviceInfoMapper.selectOne(deviceInfo);

    }

    public DeviceVO getConnDeviceInfo(String devId) {
        DeviceInfo deviceInfo = this.deviceInfoMapper.selectByPrimaryKey(devId);
        BasePosition position = this.basePositionMapper.selectByPrimaryKey(deviceInfo.getPosition_id());
        DeviceVO deviceVO = new DeviceVO();
        BeanUtils.copyProperties(deviceInfo, deviceVO);
        deviceVO.setPosition(position);
        return deviceVO;
    }

    public ManInfoVO getManInfoVo(String manId) {
        UserMemberMan userMemberMan = this.userMemberManMapper.selectByPrimaryKey(manId);
        UserMember userMember = this.userMemberMapper.selectByPrimaryKey(userMemberMan.getMember_id());
        ManInfoVO manInfoVO = new ManInfoVO();
        BeanUtils.copyProperties(userMemberMan, manInfoVO);
        manInfoVO.setUserMember(userMember);
        return manInfoVO;
    }


    public TreatmentRecord createWzRecord(DeviceVO DeviceInfo, String doctorId, String reserveId, ManInfoVO manInfoVo) {
        UserDoctor userDoctor = this.userDoctorMapper.selectByPrimaryKey(doctorId);

        TreatmentRecord treatmentRecord = new TreatmentRecord();
        treatmentRecord.setService_id(layoutService.getKeyUID());
        treatmentRecord.setService_no(this.commService.getOrderNo("WZ"));
        treatmentRecord.setAuditing(WzStatusEnum.WAITING_VISIT.getValue());
        treatmentRecord.setService_money(userDoctor.getNormal_price());
        treatmentRecord.setAdd_date(new Date());
        treatmentRecord.setMan_id(manInfoVo.getMan_id());
        treatmentRecord.setMan_name(manInfoVo.getMan_name());
        treatmentRecord.setAge(DateUtil.getAge(manInfoVo.getBoth_date()) + "");
        treatmentRecord.setMember_id(manInfoVo.getUserMember().getMember_id());
        treatmentRecord.setMember_name(manInfoVo.getUserMember().getMember_name());
        treatmentRecord.setReserve_id(reserveId);
        treatmentRecord.setDoctor_id(doctorId);
        treatmentRecord.setDoctor_name(userDoctor.getDoctor_name());
        treatmentRecord.setDevice_id(DeviceInfo.getDevice_id());
        treatmentRecord.setDevice_name(DeviceInfo.getDevice_name());
        treatmentRecord.setDevice_code(DeviceInfo.getDevice_code());
        if (DeviceInfo.getPosition() != null) {
            treatmentRecord.setDevice_address(DeviceInfo.getPosition().getAddress());
        }
        this.treatmentRecordMapper.insertSelective(treatmentRecord);
        return treatmentRecord;
    }

    public DiagnoseVO getDiagnoseInfo(String serviceId) {
        TreatmentRecord treatmentRecord = this.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        UserMemberMan userMemberMan = this.userMemberManMapper.selectByPrimaryKey(treatmentRecord.getMan_id());
        DiagnoseVO diagnoseVO = new DiagnoseVO();
        diagnoseVO.setService_id(serviceId);
        diagnoseVO.setDiagnosis_suggest(treatmentRecord.getDiagnosis_suggest());
        diagnoseVO.setDiagnosis_result(treatmentRecord.getDiagnosis_result());
        diagnoseVO.setGenetic_disease(userMemberMan.getGenetic_disease());
        diagnoseVO.setDrug_allergy(userMemberMan.getDrug_allergy());
        diagnoseVO.setPast_history(userMemberMan.getPast_history());
        diagnoseVO.setCurrent_history(treatmentRecord.getCurrent_history());
        diagnoseVO.setSymptom_describe(treatmentRecord.getSymptom_describe());
        return diagnoseVO;
    }

    private DeviceInfo getDeviceByCode(String devCode) {
        DeviceInfo devInfo = new DeviceInfo();
        devInfo.setDevice_code(devCode);
        List<DeviceInfo> select = this.deviceInfoMapper.select(devInfo);
        return select.get(0);
    }

    public Map<String, Object> getDevNumInfo(String deviceCode, String innerCode) {

        DeviceInfo outDev = this.getDeviceByCode(deviceCode);
        DeviceInfo innerDev = this.getDeviceByCode(innerCode);

        Example example = new Example(TreatmentReserve.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("device_id", outDev.getDevice_id());
        criteria.andEqualTo("reserve_date", DateUtils.format(DateUtils.toDateString(new Date())));//今日
        criteria.andNotEqualTo("auditing", "2").andNotEqualTo("auditing", "4");//号码已完成问诊
        List<TreatmentReserve> reserveList = this.treatmentReserveMapper.selectByExample(example);

        Map<String, Object> rtMap = new HashMap<String, Object>();
        List<Map<String, Object>> numberList = new ArrayList<Map<String, Object>>();
        if (reserveList.size() != 0) {
            for (TreatmentReserve reserve : reserveList) {
                Map<String, Object> pMap = new HashMap<String, Object>();
                pMap.put("reserve_id", reserve.getReserve_id());
                pMap.put("member_name", reserve.getMember_name());
                pMap.put("u_number", reserve.getReserve_no());
                pMap.put("create_time", reserve.getAdd_date());
                pMap.put("num_status", reserve.getAuditing());
                //叫号
                if ("1".equals(reserve.getAuditing())) {
                    pMap.put("num_status", 1);
                    rtMap.put("waiting_number", reserve.getReserve_no());
                }
                //过号
//                else if ("2".equals(reserve.getAuditing())) {
//                    pMap.put("num_status", 3);
//                }
                //正在就诊
                else if ("3".equals(reserve.getAuditing())) {
                    rtMap.put("current_number", reserve.getReserve_no());
                }

                numberList.add(pMap);
            }
        }
        if (!rtMap.containsKey("waiting_number")) {
            rtMap.put("waiting_number", 0);
        }
        if (!rtMap.containsKey("current_number")) {
            rtMap.put("current_number", 0);
        }
        rtMap.put("device_status", innerDev.getAuditing());
        rtMap.put("numberList", numberList);
        return rtMap;
    }

    public List<ToolData> getDataDictList(String dataValue) {
        ToolData toolData = new ToolData();
        toolData.setData_value(dataValue);
        ToolData one = this.toolDataMapper.selectOne(toolData);
        if(one == null)return null;
        Example example = new Example(ToolData.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("data_value",dataValue);
        criteria.andLike("data_id", "%" + one.getData_id() + "%");
        return this.toolDataMapper.selectByExample(example);
    }

    public TreatmentRecord doUpdateRecordStatus(String serviceId,String recordStatus) {
        TreatmentRecord treatmentRecord = this.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        treatmentRecord.setAuditing(recordStatus);
        this.treatmentRecordMapper.updateByPrimaryKeySelective(treatmentRecord);
        return treatmentRecord;
    }

    public void doUpdateReserveNumStatus(TreatmentReserve reserve) {
        TreatmentReserve treatmentReserve = this.treatmentReserveMapper.selectOne(reserve);
         if (treatmentReserve != null) {
             treatmentReserve.setAuditing(WzReserveEnum.FINISH_NUM.getValue());
             this.treatmentReserveMapper.updateByPrimaryKeySelective(treatmentReserve);
         }
    }

    public Map<String, Object> getUserInfo(String memberId) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        UserMember userMember = this.userMemberMapper.selectByPrimaryKey(memberId);
        rtMap.put("userMember", userMember);
        UserMemberAddress memberAddress = new UserMemberAddress();
        memberAddress.setMember_id(userMember.getMember_id());
        List<UserMemberAddress> addressList = this.userMemberAddressMapper.select(memberAddress);
        rtMap.put("addressList", addressList);
        return rtMap;
    }
}
