package com.duo.modules.health.web;

import com.alibaba.fastjson.JSONObject;
import com.duo.config.DynamicDataSource;
import com.duo.core.enums.ResultCodeEnum;
import com.duo.core.utils.*;
import com.duo.core.utils.wxpay.PayParam;
import com.duo.core.utils.wxpay.WxPayUtil;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.health.entity.*;
import com.duo.modules.health.model.*;
import com.duo.modules.health.service.CommService;
import com.duo.modules.health.service.PcService;
import com.duo.modules.health.service.TerminalService;
import com.duo.modules.health.service.WxService;
import com.duo.modules.tool.entity.ToolData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/terminal")
public class TerminalController {

    @Autowired
    private LayoutService layoutService;

    @Autowired
    private TerminalService terminalService;

    @Autowired
    public RedisUtil redisUtil;

    @Autowired
    public WxService wxService;

    @Autowired
    public PcService pcService;

    @Autowired
    public CommService commService;


    private String getAppTokenValue(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (!redisUtil.hHasKey("appTokens", token)) {
            return null;
        }
        return redisUtil.hget("appTokens", token).toString();
    }

    /**
     * 终端设备链接服务
     *
     * @param requestJson
     * @return Json
     */
    @PostMapping("/connect")
    public Result connect(@RequestBody JSONObject requestJson,
                          HttpServletRequest request) {
        String deviceCode = requestJson.getString("connect_code");
        String password = requestJson.getString("password");

        DynamicDataSource.setDataSource("health");
        DeviceInfo deviceInfo = this.terminalService.checkConnect(deviceCode, password);
        if (deviceInfo == null || IpUtil.getIpAddr(request).equals(deviceInfo.getFix_ip())) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        Map<String, Object> reMap = new HashMap<String, Object>();
        String tokenID = layoutService.getUUID();
        reMap.put("token", tokenID);
        redisUtil.hset("appTokens", tokenID, deviceInfo.getDevice_id());
        DynamicDataSource.clearDataSource();
        return Result.success(reMap);
    }

    @PostMapping("/disConnect")
    public Result disConnect(HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
//        DeviceConnect deviceConnect = new DeviceConnect();
//        deviceConnect.setConnect_id(connId);
//        deviceConnect.setConnect_status(0);
//        this.terminalService.deviceConnectMapper.updateByPrimaryKeySelective(deviceConnect);

        RedisUtil.hdel("appTokens", request.getHeader("Authorization"));
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //获取微信用户、收件地址列表
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam(value = "member_id") String memberId,
                              HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null || StringUtils.isBlank(memberId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        Map<String, Object> rtMap = this.terminalService.getUserInfo(memberId);
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    @PostMapping("/setOrderAddr")
    public Result setOrderAddr(@RequestBody JSONObject requestJson,
                               HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        String memberId = requestJson.getString("member_id");
        String recId = requestJson.getString("rec_id");
        String recMethod = requestJson.getString("rec_method");

        if (devId == null || StringUtils.isBlank(memberId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        this.wxService.doUpdateMemberAndRecDfAddr(memberId, recId, recMethod);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //获取就诊人列表
    @GetMapping("/getUMemberManList")
    public Result getUMemberManList(@RequestParam(value = "member_id") String memberId,
                                    HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null || StringUtils.isBlank(memberId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }

        DynamicDataSource.setDataSource("health");
        UserMemberMan userMemberMan = new UserMemberMan();
        userMemberMan.setMember_id(memberId);
        List<UserMemberMan> userMemberManList = this.terminalService.userMemberManMapper.select(userMemberMan);
        DynamicDataSource.clearDataSource();
        return Result.success(userMemberManList);
    }

    //获取就诊人的问诊记录列表
    @GetMapping("/getWzManRecordList")
    public Result getWzManRecordList(@RequestParam(value = "man_id") String manId,
                                     HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null || StringUtils.isBlank(manId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        TreatmentRecord treatmentRecord = new TreatmentRecord();
        treatmentRecord.setMan_id(manId);
        List<TreatmentRecord> recordList = wxService.getTreatmenRecord(treatmentRecord);

        List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
        for (TreatmentRecord record : recordList) {
            UserDoctor userDoctor = this.terminalService.userDoctorMapper.selectByPrimaryKey(record.getDoctor_id());
            Map<String, Object> rtMap = new HashMap<String, Object>();
            rtMap.put("wzRecord", record);
            rtMap.put("wzDoctor", userDoctor);
            rtList.add(rtMap);
        }
        DynamicDataSource.clearDataSource();
        return Result.success(rtList);
    }

    //获取问诊记录详情
    @GetMapping("/getWzDetailRecord")
    public Result getWzDetailRecord(@RequestParam(value = "service_id") String serviceId,
                                    HttpServletRequest request) {

        String devId = getAppTokenValue(request);
        if (devId == null || StringUtils.isBlank(serviceId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        Map<String, Object> rtMap = pcService.getWzDetailToMap(serviceId);
        if (rtMap.isEmpty()) {
            rtMap.put("msg", "数据异常！");
        }
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //获取数据字典列表
    @GetMapping("/getDataDictByType")
    public Result getDataDictByType(@RequestParam(value = "data_value") String dataValue,
                                    HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("duo");
        List<ToolData> dictList = this.terminalService.getDataDictList(dataValue);
        DynamicDataSource.clearDataSource();
        return Result.success(dictList);
    }

    //查找医院
    @PostMapping("/queryHospitalList")
    public Result queryHospitalList(@RequestBody JSONObject requestJson,
                                    HttpServletRequest request) {

        String connId = getAppTokenValue(request);
        if (connId == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        String fieldCode = requestJson.getString("field_code");
        String hospitalName = requestJson.getString("hospital_name");
        String dataValue = requestJson.getString("data_value");
        DynamicDataSource.setDataSource("health");
        Example example = new Example(HospitalInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("field_code", fieldCode);
        if (StringUtils.isNotBlank(hospitalName)) {
            criteria.andLike("hospital_name", "%" + hospitalName + "%");
        }
        if (StringUtils.isNotBlank(dataValue)) {
            criteria.andEqualTo("hospital_category", dataValue);
        }
        List<HospitalInfo> list = this.terminalService.hospitalInfoMapper.selectByExample(example);
        DynamicDataSource.clearDataSource();
        return Result.success(list);
    }

    //查找科室
    @PostMapping("/queryOfficeRoomList")
    public Result queryOfficeRoomList(@RequestBody JSONObject requestJson,
                                      HttpServletRequest request) {

        String devId = getAppTokenValue(request);
        if (devId == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
//        String hospitalId = requestJson.getString("hospital_id");
        DynamicDataSource.setDataSource("health");
//        Example example = new Example(HospitalRoom.class);
//        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(hospitalId)) {
//            criteria.andEqualTo("hospital_id", hospitalId);
//        }
        List<HospitalRoom> list = this.terminalService.officeRoomMapper.selectAll();
        DynamicDataSource.clearDataSource();
        return Result.success(list);
    }

    //查找医生
    @PostMapping("/queryDoctorList")
    public Result queryDoctorList(@RequestBody JSONObject requestJson,
                                  HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }

        String hospitalName = requestJson.getString("hospital_name");
        String roomName = requestJson.getString("room_name");
        String queryStr = requestJson.getString("queryStr");
        DynamicDataSource.setDataSource("health");
        Example example = new Example(UserDoctor.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("doctor_type", "1");//只查询医生
        if (StringUtils.isNotBlank(roomName)) {
            criteria.andEqualTo("office_room", roomName);
        }
        if (StringUtils.isNotBlank(hospitalName)) {
            criteria.andEqualTo("hospital_name", hospitalName);
        }
        if (StringUtils.isNotBlank(queryStr)) {
            criteria.orLike("doctor_name", "%" + queryStr + "%").orLike("special_area", "%" + queryStr + "%").orLike("memo", "%" + queryStr + "%");
        }
        List<UserDoctor> list = this.terminalService.userDoctorMapper.selectByExample(example);
        List<DoctorVO> rtList = new ArrayList<DoctorVO>();
        for (UserDoctor doctor : list) {
            DoctorVO doctorVO = new DoctorVO();
            BeanUtils.copyProperties(doctor, doctorVO);
            HospitalInfo hospital = this.terminalService.hospitalInfoMapper.selectByPrimaryKey(doctor.getHospital_id());
            doctorVO.setHospital(hospital);
            rtList.add(doctorVO);
        }
        DynamicDataSource.clearDataSource();
        return Result.success(rtList);
    }

    //申请问诊
    @PostMapping("/doWzRequest")
    public Result doWzRequest(@RequestBody JSONObject requestJson,
                              HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        String outsideDevCode = requestJson.getString("outside_dev_code");
        String manId = requestJson.getString("man_id");
        String doctorId = requestJson.getString("doctor_id");
        if (StringUtils.isBlank(manId) || StringUtils.isBlank(doctorId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        //获取内控设备信息
        DeviceVO deviceInfo = this.terminalService.getConnDeviceInfo(devId);
        //获取用户信息
        ManInfoVO manInfoVo = this.terminalService.getManInfoVo(manId);
        //获取排号记录
        TreatmentReserve tr = this.wxService.getReserveByMemberId(outsideDevCode, manInfoVo.getUserMember().getMember_id(), "3");
        if (tr == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        //生产问诊记录
        TreatmentRecord treatmentRecord = this.terminalService.createWzRecord(deviceInfo, doctorId, tr.getReserve_id(), manInfoVo);
        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("service_id", treatmentRecord.getService_id());

        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //取消申请问诊
    @PostMapping("/doDisRequest")
    public Result doDisRequest(@RequestBody JSONObject requestJson,
                               HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        String serviceId = requestJson.getString("service_id");
        if (devId == null || StringUtils.isBlank(serviceId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }

        DynamicDataSource.setDataSource("health");
        TreatmentRecord treatmentRecord = this.terminalService.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        treatmentRecord.setAuditing(WzStatusEnum.CANCEL_VISIT.getValue());
        treatmentRecord.setModify_date(new Date());
        this.terminalService.treatmentRecordMapper.updateByPrimaryKeySelective(treatmentRecord);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //获取问诊支付链接
    @GetMapping("/getWzQrPayLink")
    public Result getWzQrPayLink(@RequestParam(value = "service_id") String serviceId,
                                 @RequestParam(value = "pay_type") String payType,
                                 HttpServletRequest request) {

        String devId = getAppTokenValue(request);
        if (devId == null || StringUtils.isBlank(serviceId) || StringUtils.isBlank(payType)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        Map<String, Object> rtMap = new HashMap<String, Object>();
        PayParam payParam = new PayParam();
        payParam.setPayIp(IpUtil.getIpAddr(request));

        if ("1".equals(payType)) {
            //查询订单
            TreatmentReserve order = this.terminalService.treatmentReserveMapper.selectByPrimaryKey(serviceId);
            if (order == null) {
                rtMap.put("status", 0);
                rtMap.put("msg", "没有要支付的订单");
                return Result.success(rtMap);
            } else if (!"0".equals(order.getPay_status())) { //判断支付状态：0:未支付 1:支付成功  2:支付失败
                rtMap.put("status", order.getPay_status());
                rtMap.put("msg", "1".equals(order.getPay_status()) ? "已支付成功" : "支付失败");
                return Result.success(rtMap);
            }
            if (redisUtil.hasKey(order.getReserve_no())) {
                rtMap.put("status", 0);
                rtMap.put("msg", "重复提交订单");
                return Result.success(rtMap);
            }
            redisUtil.set(order.getReserve_no(), serviceId, 60);
            payParam.setOrderNo(order.getReserve_no());
            payParam.setPayBody("问诊支付");
            payParam.setPayMoney(ToolsUtil.YuanToFen(order.getPay_money()) + "");
        } else if ("2".equals(payType)) {
            TreatmentRecord order = this.terminalService.treatmentRecordMapper.selectByPrimaryKey(serviceId);
            if (order == null) {
                rtMap.put("status", 0);
                rtMap.put("msg", "没有要支付的订单");
                return Result.success(rtMap);
            } else if (!"0".equals(order.getPay_status())) {
                rtMap.put("status", order.getPay_status());
                rtMap.put("msg", "1".equals(order.getPay_status()) ? "已支付成功" : "支付失败");
                return Result.success(rtMap);
            }
            if (redisUtil.hasKey(order.getService_no())) {
                rtMap.put("status", 0);
                rtMap.put("msg", "重复提交订单");
                return Result.success(rtMap);
            }
            redisUtil.set(order.getService_no(), serviceId, 60);
            payParam.setOrderNo(order.getService_no());
            payParam.setPayBody("药品支付");
            payParam.setPayMoney(ToolsUtil.YuanToFen(order.getPay_money()) + "");
        } else {
            return Result.failure("403", "支付类型错误！");
        }

        String payLink = WxPayUtil.getPayLink(payParam);
        rtMap.put("status", StringUtils.isBlank(payLink) ? 0 : 1);
        rtMap.put("payLink", payLink);
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);

    }

    //获取视频秘钥
    @GetMapping("/getVideoKey")
    public Result getVideoKey(@RequestParam(value = "act_type", required = false) Integer actType,
                              HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        DeviceInfo deviceInfo = this.terminalService.deviceInfoMapper.selectByPrimaryKey(devId);
        DynamicDataSource.clearDataSource();
        String userId = deviceInfo.getDevice_code();
        if (actType == null) actType = 0;
        switch (actType) {
            case 1:
                userId = userId + "_GJVIDEO1";
                break;
            case 2:
                userId = userId + "_GJVIDEO2";
                break;
            default:
                break;
        }
        Map<String, Object> rtMap = pcService.getVideoSign(userId);
        rtMap.put("userId", userId);

        return Result.success(rtMap);
    }

    //上传检查数据
    @PostMapping("/setWzManExamine")
    public Result setWzManExamine(@RequestBody JSONObject requestJson,
                                  HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        CheckInfoVO checkInfoVO = JSONObject.toJavaObject(requestJson, CheckInfoVO.class);
        if (devId == null || StringUtils.isBlank(checkInfoVO.getService_id())) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }

        DynamicDataSource.setDataSource("health");
        TreatmentRecord treatmentRecord = this.terminalService.treatmentRecordMapper.selectByPrimaryKey(checkInfoVO.getService_id());
        if (treatmentRecord == null) {
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
        BeanUtils.copyProperties(checkInfoVO, treatmentRecord);
        this.terminalService.treatmentRecordMapper.updateByPrimaryKeySelective(treatmentRecord);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //上传设备状态
    @PostMapping("/setDeviceStatus")
    public Result setDeviceStatus(@RequestBody JSONObject requestJson,
                                  HttpServletRequest request) {
        String deviceStatus = requestJson.getString("device_status");
        String dorOpened = requestJson.getString("dor_opened");
        String outsideDevCode = requestJson.getString("outside_dev_code");
        String devId = getAppTokenValue(request);
        if (devId == null || (StringUtils.isBlank(deviceStatus) && StringUtils.isBlank(dorOpened))) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        DeviceInfo deviceInfo = this.terminalService.deviceInfoMapper.selectByPrimaryKey(devId);
        if (StringUtils.isNotBlank(deviceStatus)) {
            deviceInfo.setAuditing(deviceStatus);
        }
        if (StringUtils.isNotBlank(dorOpened)) {
            deviceInfo.setDor_opened(dorOpened);
        }
        //更新设备状态
        this.terminalService.deviceInfoMapper.updateByPrimaryKeySelective(deviceInfo);

        //通知外控刷新
        this.commService.setControlCmd(outsideDevCode, CmdEnum.CMD_REFRESH_NUM, null);

        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //问诊结束
    @PostMapping("/doWzFinish")
    public Result doWzFinish(@RequestParam(value = "service_id", required = false) String serviceId,
                             @RequestParam(value = "outside_dev_code") String outsideDevCode,
                             @RequestParam(value = "member_id") String memberId,
                             HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null || StringUtils.isBlank(outsideDevCode)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        //已申请问诊，则改变问诊记录状态
        if (StringUtils.isNotBlank(serviceId)) {
            TreatmentRecord treatmentRecord = this.terminalService.doUpdateRecordStatus(serviceId, WzStatusEnum.FINISH_VISIT.getValue());
        }
        //改变排号状态
        TreatmentReserve reserve = new TreatmentReserve();
        reserve.setAuditing(WzReserveEnum.VISIT_NUM.getValue());
        reserve.setMember_id(memberId);
        reserve.setDevice_code(outsideDevCode);
        reserve.setReserve_date(DateUtils.format(DateUtils.toDateString(new Date())));
        this.terminalService.doUpdateReserveNumStatus(reserve);

        //通知外控刷新列表
        this.commService.setControlCmd(outsideDevCode, CmdEnum.CMD_REFRESH_NUM, null);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //获取诊断结果
    @GetMapping("/getManWzResult")
    public Result getManWzResult(@RequestParam(value = "service_id") String serviceId,
                                 HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        DiagnoseVO diagnoseInfo = this.terminalService.getDiagnoseInfo(serviceId);
        DynamicDataSource.clearDataSource();
        return Result.success(diagnoseInfo);
    }

    //获取药品列表
    @GetMapping("/getDrugList")
    public Result getDrugList(@RequestParam(value = "service_id") String serviceId,
                              HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        TreatmentRecordGoods treatmentRecordGoods = new TreatmentRecordGoods();
        treatmentRecordGoods.setService_id(serviceId);
        List<TreatmentRecordGoods> goodsList = this.terminalService.treatmentRecordGoodsMapper.select(treatmentRecordGoods);
        DynamicDataSource.clearDataSource();
        return Result.success(goodsList);
    }

    //获取设备排号列表
    @GetMapping("/getDeviceNumberList")
    public Result getDeviceNumberList(@RequestParam(value = "outside_dev_code") String outsideDevCode,
                                      @RequestParam(value = "inner_dev_code") String innerDevCode,
                                      HttpServletRequest request) {
        String devId = getAppTokenValue(request);
        if (devId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
        if (StringUtils.isBlank(outsideDevCode) || StringUtils.isBlank(innerDevCode)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        Map<String, Object> rtMap = this.terminalService.getDevNumInfo(outsideDevCode, innerDevCode);
        DynamicDataSource.clearDataSource();

        return Result.success(rtMap);
    }

    //更新用户排号超时状态 // 预约号码状态0：等待叫号 1：正在叫号  2：超时过号 3：正在就诊 4：已问诊
    @PostMapping("/setUserNumberStatus")
    public Result setUserNumberPass(@RequestBody JSONObject requestJson,
                                    HttpServletRequest request) {
        String reserveId = requestJson.getString("reserve_id");
        String numStatus = requestJson.getString("num_status");
        String devId = getAppTokenValue(request);
        if (devId == null || StringUtils.isBlank(reserveId) || StringUtils.isBlank(numStatus)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        TreatmentReserve treatmentReserve = new TreatmentReserve();
        treatmentReserve.setReserve_id(reserveId);
        treatmentReserve.setAuditing(numStatus);//正在叫号或超时状态
        this.terminalService.treatmentReserveMapper.updateByPrimaryKeySelective(treatmentReserve);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //扫码开门(使用事务)
    @GetMapping("/doOpenDor")
    public Result doOpenDor(@RequestParam(value = "reserve_id") String reserveId,
                            @RequestParam(value = "outside_dev_code") String outsideDevCode,
                            @RequestParam(value = "inner_dev_code") String innerDevCode,
                            HttpServletRequest request) {

        String devId = getAppTokenValue(request);
        if (devId == null || StringUtils.isBlank(reserveId) || StringUtils.isBlank(innerDevCode)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        Map<String, Object> rtMap = new HashMap<String, Object>();
        DynamicDataSource.setDataSource("health");
        TreatmentReserve treatmentReserve = this.terminalService.treatmentReserveMapper.selectByPrimaryKey(reserveId);
        //获取开门状态
        DeviceInfo deviceInfo = this.wxService.getDevInfoByDevCode(innerDevCode);
        if (!"1".equals(deviceInfo.getAuditing())) {
            rtMap.put("open_status", 0);
            rtMap.put("msg", "设备工作未就绪，请稍后再使用！");
        } else { //是否正在叫号
            //写入通知APP开门指令，更新叫号
            Map<String, Object> connMsg = new HashMap<String, Object>();
            connMsg.put("userId", treatmentReserve.getMember_id());
            this.commService.setControlCmd(deviceInfo.getDevice_code(), CmdEnum.CMD_OPEN_DOR, connMsg);
            treatmentReserve.setAuditing(WzReserveEnum.VISIT_NUM.getValue());//修改状态：正在就诊
            this.wxService.treatmentReserveMapper.updateByPrimaryKeySelective(treatmentReserve);
            rtMap.put("open_status", 1);
        }
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }
}
