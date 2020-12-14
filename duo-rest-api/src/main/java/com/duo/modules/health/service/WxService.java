package com.duo.modules.health.service;

import com.duo.core.BaseService;
import com.duo.core.utils.DateUtils;
import com.duo.core.utils.RedisUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.utils.WzReserveEnum;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.health.entity.*;
import com.duo.modules.health.mapper.*;
import com.duo.modules.health.model.DeviceVO;
import com.duo.modules.health.model.UserNumber;
import com.duo.modules.health.model.WxUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Slf4j
@Service
public class WxService extends BaseService {

    @Autowired(required = false)
    public UserMemberMapper userMemberMapper;

    @Autowired(required = false)
    public UserMemberManMapper userMemberManMapper;

    @Autowired(required = false)
    public UserMemberAddressMapper expressAddressMapper;

    @Autowired(required = false)
    public TreatmentRecordMapper treatmentRecordMapper;

    @Autowired
    public RedisUtil redisUtil;

    @Autowired(required = false)
    public TreatmentReserveMapper treatmentReserveMapper;

    @Autowired(required = false)
    public DeviceInfoMapper deviceInfoMapper;

    @Autowired(required = false)
    public BasePositionMapper basePositionMapper;

    @Autowired
    public LayoutService layoutService;

    @Autowired(required = false)
    public DeviceControlMessageMapper deviceControlMessageMapper;

    @Autowired(required = false)
    public DeviceInfoPartMapper deviceInfoPartMapper;

    public UserMemberMan getDefaultUser(String memberId) {
        UserMemberMan userMemberMan = new UserMemberMan();
        userMemberMan.setMember_id(memberId);
        userMemberMan.setU_default("1");
        UserMemberMan man = this.userMemberManMapper.selectOne(userMemberMan);
        if (man == null) {
            userMemberMan.setU_default("0");
            List<UserMemberMan> list = this.userMemberManMapper.select(userMemberMan);
            man = list.get(0);
        }
        return man;
    }

    //获取未使用的排号信息
    public TreatmentReserve getReserveByMemberId(String devCode,String memberId, String numStatus) {
        Example example = new Example(TreatmentReserve.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("member_id", memberId);
        criteria.andEqualTo("device_code",devCode);
        String resDate = DateUtils.toDateString(new Date());
        criteria.andEqualTo("reserve_date", DateUtils.format(resDate, "yyyy-MM-dd"));
        criteria.andEqualTo("reserve_source", "app");
        if ("1".equals(numStatus)) {
            criteria.andLessThan("auditing", WzReserveEnum.PASS_CALL.getValue());
        } else if ("3".equals(numStatus)) {
            criteria.andEqualTo("auditing", WzReserveEnum.VISIT_NUM.getValue());
        }
        return this.treatmentReserveMapper.selectOneByExample(example);
    }

    //判断用户今天是否取过号，如果取过而且有效则返回，无效则新增后返回
    // 预约号码状态0：等待叫号 1：正在叫号  2：超时过号 3：正在就诊 4：已问诊
    public UserNumber checkUserNumber(String devCode,String memberId) {
        TreatmentReserve treatmentReserve = this.getReserveByMemberId(devCode,memberId, WzReserveEnum.CALLING_NUM.getValue());
        if (treatmentReserve == null) {
            treatmentReserve = this.getReserveByMemberId(devCode,memberId, WzReserveEnum.VISIT_NUM.getValue());
            if (treatmentReserve != null) {
                return this.getUserNumber(treatmentReserve);
            }
            return null;
        }
        return this.getUserNumber(treatmentReserve);
    }

    //生产最新号码
    public String createNewNumber(String deviceCode) {
        String numKey = deviceCode + "-" + DateUtils.getDateString(new Date());
        if (!redisUtil.hHasKey("AutoNum", numKey)) {
            redisUtil.hset("AutoNum", numKey, 0, DateUtils.getMillisOfDay(new Date()) / 1000);
        }
        redisUtil.hincr("AutoNum", numKey, 1);
        return redisUtil.hget("AutoNum", numKey).toString();
    }

    //获取用户排号
    public UserNumber getUserNumber(TreatmentReserve treatmentReserve) {
        DeviceInfo deviceInfo = this.deviceInfoMapper.selectByPrimaryKey(treatmentReserve.getDevice_id());
        return this.setUserNumber(treatmentReserve, deviceInfo);
    }

    public Map<String, Object> getUserInfo(String memberId) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        UserMember userMember = this.userMemberMapper.selectByPrimaryKey(memberId);
        rtMap.put("userMember", userMember);
        UserMemberMan defaultUser = this.getDefaultUser(memberId);
        rtMap.put("userMemberMan", defaultUser);
        UserMemberAddress expressAddress = null;
        if (userMember.getRec_id() != null) {
            expressAddress = this.expressAddressMapper.selectByPrimaryKey(userMember.getRec_id());
        }
        rtMap.put("expressAddress", expressAddress);
        return rtMap;
    }


    //查找当前设备，今日状态的列表
    public List<UserNumber> getRankNumberListByStatus(String memberId, String status) {
        Example example = new Example(TreatmentReserve.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("member_id", memberId);
        criteria.andEqualTo("reserve_date", DateUtils.format(DateUtils.toDateString(new Date())));
        criteria.andEqualTo("reserve_source", "app");
//        criteria.andEqualTo("device_id",treatmentReserve.getDevice_id());
        if ("1".equals(status)) { //状态0：等待叫号 1：正在叫号
            criteria.andLessThan("auditing", WzReserveEnum.PASS_CALL.getValue());
        }
        if ("2".equals(status)) {//2：超时过号 3：正在就诊 4：已问诊
            criteria.andGreaterThanOrEqualTo("auditing", WzReserveEnum.PASS_CALL.getValue());
        }

        //获取当天所有的号码
        List<TreatmentReserve> reserveList = this.treatmentReserveMapper.selectByExample(example);
        List<UserNumber> rtlist = new ArrayList<UserNumber>();
        for (TreatmentReserve rt : reserveList) {
            DeviceInfo deviceInfo = this.deviceInfoMapper.selectByPrimaryKey(rt.getDevice_id());
            rtlist.add(this.setUserNumber(rt, deviceInfo));
        }
        return rtlist;
    }

    public List<TreatmentRecord> getTreatmenRecord(TreatmentRecord treatmentRecord) {
        return this.treatmentRecordMapper.select(treatmentRecord);
    }

    public void setAllUserManNoDefault(String memberId) {
        Example example = new Example(UserMemberMan.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("member_id", memberId);
        UserMemberMan umm = new UserMemberMan();
        umm.setU_default("0");
        this.userMemberManMapper.updateByExampleSelective(umm, example);
    }

    public void setAllExpAddrNoDefault(String memberId) {
        Example example = new Example(UserMember.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("member_id", memberId);
        UserMemberAddress expressAddress = new UserMemberAddress();
        expressAddress.setRec_default("0");
        this.expressAddressMapper.updateByExampleSelective(expressAddress, example);
    }

    public DeviceVO getDevInfoByDevCode(String deviceCode) {
        DeviceVO deviceVO = new DeviceVO();
        DeviceInfo devInfo = new DeviceInfo();
        devInfo.setDevice_code(deviceCode);
        List<DeviceInfo> deviceInfoList = this.deviceInfoMapper.select(devInfo);
        if (deviceInfoList.size() == 0) {
            return null;
        }
        BeanUtils.copyProperties(deviceInfoList.get(0),deviceVO);
        DeviceInfoPart part = new DeviceInfoPart();
        part.setDevice_id(deviceInfoList.get(0).getDevice_id());
        List<DeviceInfoPart> partList = this.deviceInfoPartMapper.select(part);
        deviceVO.setPartList(partList);
        return deviceVO;
    }


    public UserNumber getNewUserNumber(String deviceCode, String memberId) {
        DeviceInfo deviceInfo = getDevInfoByDevCode(deviceCode);
        UserMember userMember = this.userMemberMapper.selectByPrimaryKey(memberId);
        if (userMember == null || deviceInfo == null) {
            return null;
        }
        String newNumber = this.createNewNumber(deviceCode);
        TreatmentReserve treatmentReserve = new TreatmentReserve();
        treatmentReserve.setReserve_id(layoutService.getKeyUID());
        treatmentReserve.setMember_id(memberId);
        treatmentReserve.setMember_name(userMember.getMember_name());
        treatmentReserve.setDevice_id(deviceInfo.getDevice_id());
        treatmentReserve.setDevice_code(deviceCode);
        treatmentReserve.setDevice_name(deviceInfo.getDevice_name());
        treatmentReserve.setReserve_date(DateUtils.format(DateUtils.toDateString(new Date())));
        treatmentReserve.setAuditing(WzReserveEnum.WAITING_CALL.getValue());
        treatmentReserve.setAdd_date(new Date());
        treatmentReserve.setReserve_source("app");
        treatmentReserve.setReserve_no(newNumber);
        this.treatmentReserveMapper.insertSelective(treatmentReserve);

        return this.setUserNumber(treatmentReserve, deviceInfo);

    }

    public UserNumber setUserNumber(TreatmentReserve treatmentReserve, DeviceInfo deviceInfo) {
        Example example = new Example(TreatmentReserve.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("reserve_date", DateUtils.format(DateUtils.toDateString(new Date())));
        criteria.andEqualTo("reserve_source", "app");
        //获取当天所有的号码
        List<TreatmentReserve> reserveList = this.treatmentReserveMapper.selectByExample(example);

        UserNumber userNumber = new UserNumber();
        userNumber.setDevice_id(deviceInfo.getDevice_id());
        userNumber.setDevice_code(deviceInfo.getDevice_code());
        userNumber.setU_number(Integer.valueOf(treatmentReserve.getReserve_no()));
        userNumber.setMember_id(treatmentReserve.getMember_id());
        userNumber.setNum_date(DateUtils.toDateString(treatmentReserve.getReserve_date()));
        userNumber.setCreate_time(treatmentReserve.getAdd_date());
        String pointId = deviceInfo.getPosition_id();
        if (StringUtils.isNotBlank(pointId)) {
            BasePosition position = this.basePositionMapper.selectByPrimaryKey(pointId);
            userNumber.setDevice_address(position.getAddress());
        }
        if (reserveList.size() > 0) {
            for (TreatmentReserve tr : reserveList) {
                //3：正在就诊号
                if ("3".equals(tr.getAuditing())) {
                    userNumber.setCurrent_number(Integer.valueOf(tr.getReserve_no()));
                }
                //1：正在叫号
                else if ("1".equals(tr.getAuditing())) {
                    userNumber.setWaiting_number(Integer.valueOf(tr.getReserve_no()));
                } else {
                    userNumber.setCurrent_number(0);
                    userNumber.setWaiting_number(Integer.valueOf(treatmentReserve.getReserve_no()));
                }
            }
        } else {
            userNumber.setCurrent_number(0);
            userNumber.setWaiting_number(Integer.valueOf(treatmentReserve.getReserve_no()));
        }

        return userNumber;
    }

    public void doSaveOrUpdateMember(String wxOpenId,WxUserVO wxUserVO,UserMemberAddress expressAddress) {
        UserMember userMember = new UserMember();
        userMember.setMember_id(wxOpenId);
        userMember.setMember_name(wxUserVO.getMember_name());
        userMember.setMobile_no(wxUserVO.getPhone());
        userMember.setNick_name(wxUserVO.getNickname());
        userMember.setSex(wxUserVO.getGender());
        userMember.setImg_url(wxUserVO.getHeadimgurl());
        userMember.setAdd_date(new Date());
        userMember.setAuditing("0");
        userMember.setMember_level("1");
        userMember.setUsable_point(0);
        userMember.setTotal_point(0);
        userMember.setOrder_num(0);
        userMember.setOrder_money(0.0);

        //如果选择送货上门，则保存地址
        if ("1".equals(userMember.getRec_method())) {
            expressAddress.setRec_id(layoutService.getKeyUID());
            userMember.setRec_id(expressAddress.getRec_id());
            this.expressAddressMapper.insertSelective(expressAddress);
        }else{
            userMember.setRec_method("0");
        }

        UserMember member = this.userMemberMapper.selectOne(userMember);
        if (member == null) {
            this.userMemberMapper.insertSelective(userMember);
        } else {
            this.userMemberMapper.updateByPrimaryKeySelective(userMember);
        }
    }

    public void doSaveMemberMan(String wxOpenId,UserMemberMan userMemberMan) {
        userMemberMan.setMember_id(wxOpenId);
        userMemberMan.setMan_id(layoutService.getKeyUID());
        userMemberMan.setU_default("1");
        userMemberMan.setAdd_date(new Date());
        this.userMemberManMapper.insertSelective(userMemberMan);
    }

    public void doUpdateMemberAndRecDfAddr(String memberId, String recId,String recMethod) {
        UserMember userMember = this.userMemberMapper.selectByPrimaryKey(memberId);
        this.setAllExpAddrNoDefault(memberId);
        if(StringUtils.isNotBlank(recId)) {
            userMember.setRec_id(recId);
        }
        if(StringUtils.isNotBlank(recMethod)){
            userMember.setRec_method(recMethod);
        }
        this.userMemberMapper.updateByPrimaryKeySelective(userMember);
        if(StringUtils.isNotBlank(recId)) {
            UserMemberAddress expressAddress = new UserMemberAddress();
            expressAddress.setRec_id(recId);
            expressAddress.setRec_default("1");
            this.expressAddressMapper.updateByPrimaryKeySelective(expressAddress);
        }
    }
}
