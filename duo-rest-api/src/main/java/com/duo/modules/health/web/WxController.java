package com.duo.modules.health.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.config.DynamicDataSource;
import com.duo.config.properties.WxProperties;
import com.duo.core.enums.ResultCodeEnum;
import com.duo.core.utils.*;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.health.entity.*;

import com.duo.modules.health.model.DeviceVO;
import com.duo.modules.health.model.UserNumber;
import com.duo.modules.health.model.WxUserVO;
import com.duo.modules.health.service.CommService;
import com.duo.modules.health.service.PcService;
import com.duo.modules.health.service.WxService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/api/wx")
public class WxController {

    @Autowired
    public WxProperties wxProperties;
    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private WxService wxService;

    @Autowired
    private PcService pcService;

    @Autowired
    private LayoutService layoutService;

    @Autowired
    public RedisUtil redisUtil;

    @Autowired
    public CommService commService;

    //登录
    @GetMapping("/login")
    public Result login(@RequestParam(value = "code") String code) {
        JSONObject OauthToken = getOauthToken(code);
        Map<String, Object> map = new HashMap<String, Object>();
        if (OauthToken == null) {
            map.put("login_status", 0); //登录失败
            return Result.failure(map, ResultCodeEnum.UNAUTHORIZED.getCode(), ResultCodeEnum.UNAUTHORIZED.getMsg());
        }
        String wxOpenId = OauthToken.getString("openid");// 获取openId
        String assessToken = OauthToken.getString("access_token");// 获取access_token
        DynamicDataSource.setDataSource("health");
//        UserNumber userNumber = this.wxService.userNumberMapper.selectByPrimaryKey(wxOpenId);
//        if(userNumber == null){
//            map.put("login_status", 3);
//            map.put("msg","用户未授权");
//            return Result.success();
//        }
        UserMemberMan userMemberMan = new UserMemberMan();
        userMemberMan.setMember_id(wxOpenId);
        int count = wxService.userMemberManMapper.selectCount(userMemberMan);

        if (count == 0) {
            OauthToken.put("login_status", 2); //未绑定就诊人
        } else {
            OauthToken.put("login_status", 1); //登录成功
            redisUtil.hset("wxToken", assessToken, wxOpenId, 1800);
            OauthToken.put("token", assessToken);
        }
        DynamicDataSource.clearDataSource();
        return Result.success(OauthToken);

    }

    //注册
    @PostMapping("/registerMan")
    public Result registerMan(@RequestParam(value = "code") String code,
                              @Valid WxUserVO wxUserVO,
                              @Valid UserMemberMan userMemberMan,
                              @Valid UserMemberAddress expressAddress) {
        JSONObject OauthToken = getOauthToken(code);
        if (OauthToken == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        try {
            String wxOpenId = OauthToken.getString("openid");
            String assessToken = OauthToken.getString("access_token");

            DynamicDataSource.setDataSource("health");
            this.wxService.doSaveOrUpdateMember(wxOpenId, wxUserVO, expressAddress);
            this.wxService.doSaveMemberMan(wxOpenId, userMemberMan);
            OauthToken.put("reg_status", 1);
            OauthToken.put("login_status", 1); //登录成功
            redisUtil.hset("wxToken", assessToken, wxOpenId, 1800);
            OauthToken.put("token", assessToken);
        } catch (Exception e) {
            OauthToken.put("reg_status", 0);
            return Result.failure(OauthToken, ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), ResultCodeEnum.INTERNAL_SERVER_ERROR.getMsg());
        } finally {
            DynamicDataSource.clearDataSource();
        }
        return Result.success(OauthToken);
    }

    //获取微信用户、默认就诊人信息、收件地址详情
    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
        DynamicDataSource.setDataSource("health");
        Map<String, Object> userInfo = wxService.getUserInfo(memberId);
        DynamicDataSource.clearDataSource();
        return Result.success(userInfo);
    }

    //获取就诊人列表
    @GetMapping("/getUMemberManList")
    public Result getUMemberManList(HttpServletRequest request) {
        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }

        DynamicDataSource.setDataSource("health");
        UserMemberMan userMemberMan = new UserMemberMan();
        userMemberMan.setMember_id(memberId);
        List<UserMemberMan> userMemberManList = wxService.userMemberManMapper.select(userMemberMan);
        DynamicDataSource.clearDataSource();
        return Result.success(userMemberManList);
    }

    //保存新增、修改信息
    @PostMapping("/saveUMemberMan")
    public Result saveUMemberMan(@Valid UserMemberMan userMemberMan,
                                 HttpServletRequest request) {
        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
        DynamicDataSource.setDataSource("health");
        try {
            //修改其他默认就诊人状态
            if ("1".equals(userMemberMan.getU_default())) {
                wxService.setAllUserManNoDefault(memberId);
            }

            if (userMemberMan.getMan_id() != null) { //edit
                wxService.userMemberManMapper.updateByPrimaryKeySelective(userMemberMan);
            } else { //add
                userMemberMan.setMember_id(memberId);
                userMemberMan.setMan_id(layoutService.getKeyUID());
                wxService.userMemberManMapper.insertSelective(userMemberMan);
            }
        } catch (Exception e) {
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
        DynamicDataSource.clearDataSource();
        return Result.success(userMemberMan);
    }

    //设置修改默认收件地址
    @GetMapping("/setRecDefault")
    public Result setRecDefault(@RequestParam(value = "rec_id") String rec_id,
                                HttpServletRequest request) {
        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }

        DynamicDataSource.setDataSource("health");
        this.wxService.doUpdateMemberAndRecDfAddr(memberId,rec_id,null);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //设置取药方式
    @GetMapping("/setRecMethod")
    public Result setRecMethod(@RequestParam(value = "rec_method") String recMethod,
                               HttpServletRequest request) {
        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }

        DynamicDataSource.setDataSource("health");
        UserMember userMember = this.wxService.userMemberMapper.selectByPrimaryKey(memberId);
        userMember.setRec_method(recMethod);
        this.wxService.userMemberMapper.updateByPrimaryKeySelective(userMember);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //获取地址列表
    @GetMapping("/getExpressAddrList")
    public Result getExpressAddrList(HttpServletRequest request) {
        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
        DynamicDataSource.setDataSource("health");
        UserMemberAddress expressAddress = new UserMemberAddress();
        expressAddress.setMember_id(memberId);
        List<UserMemberAddress> expressAddressList = wxService.expressAddressMapper.select(expressAddress);
        DynamicDataSource.clearDataSource();
        return Result.success(expressAddressList);
    }

    //保存新增、修改收件地址
    @PostMapping("/saveExpressAddr")
    public Result saveUMemberMan(@Valid UserMemberAddress expressAddress,
                                 HttpServletRequest request) {
        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
        DynamicDataSource.setDataSource("health");
        if ("1".equals(expressAddress.getRec_default())) {
            Example example = new Example(UserMemberAddress.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("member_id", memberId);
            UserMemberAddress expAddr = new UserMemberAddress();
            expAddr.setRec_default("0");
            expAddr.setModify_date(new Date());
            wxService.expressAddressMapper.updateByExampleSelective(expAddr, example);
        }

        if (expressAddress.getRec_id() != null) {
            expressAddress.setModify_date(new Date());
            wxService.expressAddressMapper.updateByPrimaryKeySelective(expressAddress);
        } else {
            expressAddress.setRec_id(layoutService.getKeyUID());
            expressAddress.setCreate_time(new Date());
            wxService.expressAddressMapper.insertSelective(expressAddress);
        }
        if ("1".equals(expressAddress.getRec_default())) {
            UserMember userMember = new UserMember();
            userMember.setMember_id(memberId);
            userMember.setRec_id(expressAddress.getRec_id());
            userMember.setModify_date(new Date());
            wxService.userMemberMapper.updateByPrimaryKeySelective(userMember);
        }
        DynamicDataSource.clearDataSource();
        return Result.success(expressAddress);
    }

    //获取排队号码
    @GetMapping("/getRankNumber")
    public Result getRankNumber(@RequestParam(value = "device_code") String deviceCode,
                                HttpServletRequest request) {

        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
        if (StringUtils.isBlank(deviceCode)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        try {
            DynamicDataSource.setDataSource("health");
            //判断是否新取号，是则通知app更新号码队列
            UserNumber userNumber = wxService.checkUserNumber(deviceCode, memberId);
            if (userNumber == null) {
                userNumber = wxService.getNewUserNumber(deviceCode, memberId);
                userNumber.setNew_status(true);
                //写入通知APP取号指令
                this.commService.setControlCmd(userNumber.getDevice_code(), CmdEnum.CMD_GET_NUMBER, null);
            } else {
                userNumber.setNew_status(false);
            }
            DynamicDataSource.clearDataSource();
            return Result.success(userNumber);
        } catch (Exception e) {
            return Result.failure(ResultCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //获取当前机器等待排队号码
    @GetMapping("/getRankNumberListByStatus")
    public Result getRankNumberListByStatus(@RequestParam(value = "num_status") String numStatus,
                                            HttpServletRequest request) {

        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
        if (StringUtils.isBlank(numStatus)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        List<UserNumber> rankNumberList = wxService.getRankNumberListByStatus(memberId, numStatus);
        DynamicDataSource.clearDataSource();
        return Result.success(rankNumberList);
    }

    //获取就诊人的问诊记录列表
    @GetMapping("/getWzManRecordList")
    public Result getWzManRecordList(@RequestParam(value = "man_id", required = false) String manId,
                                     HttpServletRequest request) {
        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }

        DynamicDataSource.setDataSource("health");

        TreatmentRecord treatmentRecord = new TreatmentRecord();
        if (StringUtils.isNotBlank(manId)) {
            treatmentRecord.setMan_id(manId);
        } else {
            treatmentRecord.setMember_id(memberId);
        }
        List<TreatmentRecord> recordList = wxService.getTreatmenRecord(treatmentRecord);
        DynamicDataSource.clearDataSource();
        return Result.success(recordList);
    }


    //获取问诊记录详情
    @GetMapping("/getWzDetailRecord")
    public Result getWzDetailRecord(@RequestParam(value = "service_id") String serviceId,
                                    HttpServletRequest request) {

        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }

        DynamicDataSource.setDataSource("health");
        Map<String, Object> wzDetailToMap = pcService.getWzDetailToMap(serviceId);
        DynamicDataSource.clearDataSource();
        return Result.success(wzDetailToMap);
    }

    //扫码开门(使用事务)
    @GetMapping("/doOpenDor")
    public Result doOpenDor(@RequestParam(value = "outside_dev_code") String outsideDevCode,
                            @RequestParam(value = "inner_dev_code") String innerDevCode,
                            HttpServletRequest request) {

        String memberId = getWxTokenValue(request);
        if (memberId == null) {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
        if (StringUtils.isBlank(outsideDevCode) || StringUtils.isBlank(innerDevCode)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        Map<String, Object> rtMap = new HashMap<String, Object>();
        DynamicDataSource.setDataSource("health");
        //判断是否取号
        TreatmentReserve treatmentReserve = this.wxService.getReserveByMemberId(outsideDevCode, memberId, WzReserveEnum.CALLING_NUM.getValue());
        if (treatmentReserve == null) {
            rtMap.put("open_status", 2);
            rtMap.put("msg", "用户未取号，请先取号后使用！");
            return Result.success(rtMap);
        } else if ("0".equals(treatmentReserve.getAuditing())) {
            rtMap.put("open_status", 3);
            rtMap.put("msg", "排号未到，请等待叫号！");
            return Result.success(rtMap);
        } else if ("2".equals(treatmentReserve.getAuditing())) {
            rtMap.put("open_status", 4);
            rtMap.put("msg", "排号超时过号，请重新取号！");
            return Result.success(rtMap);
        }
        //获取开门状态
        DeviceVO deviceVO = this.wxService.getDevInfoByDevCode(innerDevCode);

        if (!"1".equals(deviceVO.getAuditing())) {
            rtMap.put("open_status", 0);
            rtMap.put("msg", "设备工作未就绪，请稍后再使用！");
        } else { //是否正在叫号
            //通知内控开门，更新叫号
            Map<String, Object> connMsg = new HashMap<String, Object>();
            connMsg.put("userId", memberId);
            this.commService.setControlCmd(deviceVO.getDevice_code(), CmdEnum.CMD_OPEN_DOR, connMsg);
            treatmentReserve.setAuditing(WzReserveEnum.VISIT_NUM.getValue());//修改状态：正在就诊
            this.wxService.treatmentReserveMapper.updateByPrimaryKeySelective(treatmentReserve);
            rtMap.put("open_status", 1);
//            //通知外控刷新
//            this.commService.setControlCmd(outsideDevCode, CmdEnum.CMD_REFRESH_NUM, null);
        }
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    private String getWxTokenValue(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        if (!redisUtil.hHasKey("wxToken", accessToken)) {
            return null;
        }
        return redisUtil.hget("wxToken", accessToken).toString();
    }

    private JSONObject getOauthToken(String code) {
        String jsonStr = wxUtil.getWxOauthStr(wxProperties.getAppId(), wxProperties.getSecret(), code);
        // 微信接口请求错误
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        // 微信接口中无法获取openId
        if (!jsonObject.containsKey("openid")) {
            return null;
        }
        return jsonObject;
    }

    private JSONObject getUserInfo(String assessToken, String openId) {
        String jsonStr = wxUtil.getWxUserInfoStr(assessToken, openId);
        // 微信接口请求错误
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        // 微信接口中无法获取openId
        if (!jsonObject.containsKey("nickname")) {
            return null;
        }
        return jsonObject;
    }

}
