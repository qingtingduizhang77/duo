package com.duo.modules.health.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.duo.config.DynamicDataSource;
import com.duo.core.enums.ResultCodeEnum;
import com.duo.core.utils.*;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.health.entity.*;
import com.duo.modules.health.service.CommService;
import com.duo.modules.health.service.PcService;
import com.duo.modules.health.model.DiagnoseVO;
import com.duo.modules.health.model.WzRecordVO;
import com.duo.modules.system.entity.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
@Slf4j
@RestController
@RequestMapping("/api/pc")
public class PcController {

    @Autowired
    private PcService pcService;

    @Autowired
    private LayoutService layoutService;

    @Autowired
    public RedisUtil redisUtil;

    @Autowired
    public CommService commService;

    private String getPcTokenValue(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        if (!redisUtil.hHasKey("pcToken", accessToken)) {
            return null;
        }
        return redisUtil.hget("pcToken", accessToken).toString();
    }

    private UserDoctor getLoginDoctor(HttpServletRequest request) {
        DynamicDataSource.setDataSource("health");
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return null;
        }
        UserDoctor userDoctor = pcService.userDoctorMapper.selectByPrimaryKey(doctorId);
        DynamicDataSource.clearDataSource();
        return userDoctor;
    }

    @PostMapping("/login")
    public Result login(@RequestBody JSONObject requestJson) {
        String account = requestJson.getString("account");
        String password = requestJson.getString("password");
        String servStatus = requestJson.getString("serv_status");
        String dtType = requestJson.getString("dtType");


        Map<String, Object> rtMap = new HashMap<String, Object>();
        SystemUser systemUser = pcService.doCheckAccount(account, password, null);
        if (systemUser == null) {
            rtMap.put("login_status", 0);
            rtMap.put("msg", "账号或密码不正确！");
            return Result.success(rtMap);
        }
        DynamicDataSource.setDataSource("health");
        UserDoctor userDoctor = new UserDoctor();
        userDoctor.setUser_code(account);
        userDoctor.setDoctor_type(dtType);
        UserDoctor uDoctor = this.pcService.userDoctorMapper.selectOne(userDoctor);
        if (uDoctor == null) {
            rtMap.put("login_status", 2);
            rtMap.put("msg", "账号未绑定专家！");
            return Result.success(rtMap);
        }
        uDoctor.setServ_status(servStatus);
        pcService.userDoctorMapper.updateByPrimaryKeySelective(uDoctor);

        String token = layoutService.getKeyUID();
        rtMap.put("login_status", 1);
        rtMap.put("token", token);
        redisUtil.hset("pcToken", token, uDoctor.getDoctor_id(), 7200);
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {

        Map<String, Object> rtMap = new HashMap<String, Object>();
        String uid = getPcTokenValue(request);
        if (uid == null) {
            rtMap.put("logout_status", 0);
            return Result.failure(rtMap, ResultCodeEnum.SESSION_TIMEOUT.getCode(), ResultCodeEnum.SESSION_TIMEOUT.getMsg());
        }

        DynamicDataSource.setDataSource("health");
        UserDoctor uDoctor = new UserDoctor();
        uDoctor.setDoctor_id(uid);
        uDoctor.setServ_status("0");
        pcService.userDoctorMapper.updateByPrimaryKeySelective(uDoctor);

        rtMap.put("logout_status", 1);
        redisUtil.hdel("pcToken", request.getHeader("accessToken"));
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    @PostMapping("/rePassword")
    public Result rePassword(@RequestBody JSONObject requestJson,
                             HttpServletRequest request) {
        String password = requestJson.getString("password");
        String newPwd = requestJson.getString("newpwd");

        Map<String, Object> rtMap = new HashMap<String, Object>();
        UserDoctor userDoctor = this.getLoginDoctor(request);
        if (userDoctor == null) {
            rtMap.put("logout_status", 0);
            return Result.failure(rtMap, ResultCodeEnum.SESSION_TIMEOUT.getCode(), ResultCodeEnum.SESSION_TIMEOUT.getMsg());
        }
        SystemUser systemUser = pcService.doCheckAccount(userDoctor.getUser_code(), password, newPwd);
        if (systemUser == null) {
            rtMap.put("set_status", 2);
            rtMap.put("msg", "原密码不正确");
            return Result.success(rtMap);
        }

        rtMap.put("set_status", 1);
        return Result.success(rtMap);
    }

    //修改手机号、地址
    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody JSONObject requestJson,
                             HttpServletRequest request) {
        String phone = requestJson.getString("phone");
        String address = requestJson.getString("address");
        Map<String, Object> rtMap = new HashMap<String, Object>();
        String uid = getPcTokenValue(request);
        if (uid == null) {
            rtMap.put("set_status", 2);
            return Result.failure(rtMap, ResultCodeEnum.SESSION_TIMEOUT.getCode(), ResultCodeEnum.SESSION_TIMEOUT.getMsg());
        }
        DynamicDataSource.setDataSource("health");
        UserDoctor userDoctor = pcService.userDoctorMapper.selectByPrimaryKey(uid);
        if (StringUtils.isNotBlank(phone)) {
            userDoctor.setMobile_no(phone);
        }
        if (StringUtils.isNotBlank(address)) {
            userDoctor.setAddress(address);
        }
        pcService.userDoctorMapper.updateByPrimaryKeySelective(userDoctor);
        DynamicDataSource.clearDataSource();
        rtMap.put("set_status", 1);
        return Result.success(rtMap);
    }

    //修改头像
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(MultipartHttpServletRequest request) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        MultipartFile imgFile = request.getFile("file");
        String doctorId = getPcTokenValue(request);
        if (doctorId == null || imgFile == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        DynamicDataSource.setDataSource("health");
        UserDoctor userDoctor = pcService.userDoctorMapper.selectByPrimaryKey(doctorId);
        try {
            String floder = "/static/images/upload";
            //获取跟目录
            File srcPath = new File(ResourceUtils.getURL("classpath:").getPath());
            File upload = new File(srcPath.getAbsolutePath(),floder);
            // 创建文件夹: 路径 + 文件夹
            if(!upload.exists()) upload.mkdirs();

            // 保存路径
            String savePath = upload.getAbsolutePath();
            String fileName = imgFile.getOriginalFilename();
            //文件名
            if (imgFile != null) {
                // 图片重命名
                fileName = layoutService.getUUID() + fileName.substring(fileName.lastIndexOf('.'));
                // 保存到磁盘 :路径+目录+文件名
                imgFile.transferTo(new File(savePath + "/" + fileName));
//                log.info("保存路径："+savePath + "/" + fileName);
            }
            String imgUrl = floder+ "/" + fileName;
            userDoctor.setImg_url(imgUrl);
            pcService.userDoctorMapper.updateByPrimaryKeySelective(userDoctor);
            rtMap.put("img_url", imgUrl);
            rtMap.put("set_status", 1);
            DynamicDataSource.clearDataSource();
            return Result.success(rtMap);
        } catch (Exception e) {
            rtMap.put("set_status", 2);
            return Result.failure(rtMap, ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), ResultCodeEnum.INTERNAL_SERVER_ERROR.getMsg());
        }
    }

    //设置问诊状态
    @PostMapping("/setServStatus")
    public Result setServStatus(@RequestBody JSONObject requestJson,
                                HttpServletRequest request) {
        String servStatus = requestJson.getString("serv_status");
        if (servStatus == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        String uid = getPcTokenValue(request);
        if (uid == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        DynamicDataSource.setDataSource("health");
        UserDoctor uDoctor = new UserDoctor();
        uDoctor.setDoctor_id(uid);
        uDoctor.setServ_status(servStatus);
        pcService.userDoctorMapper.updateByPrimaryKeySelective(uDoctor);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    //获取医生信息
    @GetMapping("/getDoctorInfo")
    public Result getDoctorInfo(HttpServletRequest request) {
        DynamicDataSource.setDataSource("health");
        UserDoctor userDoctor = this.getLoginDoctor(request);
        if (userDoctor == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("doctor_id", userDoctor.getDoctor_id());
        rtMap.put("doctor_name", userDoctor.getDoctor_name());
        rtMap.put("sex", userDoctor.getSex());
        rtMap.put("birthday", userDoctor.getBirth_date());
        rtMap.put("id_card", userDoctor.getId_card());
        rtMap.put("qualify_no", userDoctor.getQualify_no());
        rtMap.put("from_date", userDoctor.getFrom_date());
        rtMap.put("office_room", userDoctor.getOffice_room());
        rtMap.put("hospital_name", userDoctor.getHospital_name());
        rtMap.put("qualify_level", userDoctor.getQualify_level());
        rtMap.put("img_url", userDoctor.getImg_url());
        rtMap.put("mobile_no", userDoctor.getMobile_no());
        rtMap.put("address", userDoctor.getAddress());
        rtMap.put("serv_status", userDoctor.getServ_status());
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //获取医生排班
    @PostMapping("/getDoctorSchedualing")
    public Result getDoctorSchedualing(@RequestBody JSONObject requestJson,
                                       HttpServletRequest request) {

        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        String startDate = requestJson.getString("start_date");
        String endDate = requestJson.getString("end_date");
        DynamicDataSource.setDataSource("health");
        Example example = new Example(UserDoctorSchedual.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("doctor_id", doctorId);
        criteria.andBetween("schedual_date", DateUtils.format(startDate, "yyyy-MM-dd"), DateUtils.format(endDate, "yyyy-MM-dd"));
        List<UserDoctorSchedual> rtList = pcService.userDoctorSchedualMapper.selectByExample(example);
        DynamicDataSource.clearDataSource();
        return Result.success(rtList);
    }

    //保存设置排班信息
    @PostMapping("/setDoctorScheduling")
    public Result setDoctorScheduling(@RequestBody JSONObject requestJson,
                                      HttpServletRequest request) {
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        UserDoctorSchedual userDoctorSchedual = JSONObject.toJavaObject(requestJson, UserDoctorSchedual.class);
        DynamicDataSource.setDataSource("health");
        Map<String, Object> rtMap = new HashMap<String, Object>();

        userDoctorSchedual.setDoctor_id(doctorId);
        if (StringUtils.isNotBlank(userDoctorSchedual.getSchedual_id())) {
            userDoctorSchedual.setModify_date(new Date());
            pcService.userDoctorSchedualMapper.updateByPrimaryKeySelective(userDoctorSchedual);
        } else { //新增
            userDoctorSchedual.setSchedual_id(layoutService.getKeyUID());
            userDoctorSchedual.setAdd_date(new Date());
            pcService.userDoctorSchedualMapper.insertSelective(userDoctorSchedual);
        }

        rtMap.put("set_status", 1);
        rtMap.put("SchedualObject", userDoctorSchedual);
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //删除排班
    @PostMapping("/delDoctorScheduling")
    public Result delDoctorScheduling(@RequestParam(value = "schedual_id") String schedualId,
                                      HttpServletRequest request) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            rtMap.put("det_status", 0);
            return Result.failure(rtMap, ResultCodeEnum.SESSION_TIMEOUT.getCode(), ResultCodeEnum.SESSION_TIMEOUT.getMsg());
        }
        DynamicDataSource.setDataSource("health");
        pcService.userDoctorSchedualMapper.deleteByPrimaryKey(schedualId);
        DynamicDataSource.clearDataSource();
        rtMap.put("det_status", 1);
        return Result.success(rtMap);
    }

    //获取医生收入
    @GetMapping("/getDoctorIncome")
    public Result getDoctorIncome(HttpServletRequest request) {

        DynamicDataSource.setDataSource("health");
        UserDoctor userDoctor = this.getLoginDoctor(request);
        if (userDoctor == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        DynamicDataSource.clearDataSource();
        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("sum_money", userDoctor.getSum_money());
        rtMap.put("balance", userDoctor.getBalance_money());
        return Result.success(rtMap);
    }

    //获取医生提现
    @GetMapping("/doTakingMoney")
    public Result doTakingMoney(@RequestParam(value = "money") Double money,
                                @RequestParam(value = "remark", required = false) String remark,
                                HttpServletRequest request) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        DynamicDataSource.setDataSource("health");
        UserDoctor userDoctor = this.getLoginDoctor(request);
        if (userDoctor == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        if (money > userDoctor.getBalance_money()) {
            rtMap.put("get_status", 2);
            rtMap.put("msg", "余额不足");
            return Result.success();
        }
        Double yue = userDoctor.getBalance_money() - money;
        userDoctor.setBalance_money(yue);
        userDoctor.setApplying_money(money);
        int i = pcService.userDoctorMapper.updateByPrimaryKeySelective(userDoctor);
        DynamicDataSource.clearDataSource();
        rtMap.put("get_status", 1);
        return Result.success(rtMap);
    }

    //收入明细
    @GetMapping("/getIncomeDetail")
    public Result getIncomeDetail(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "month", required = false) String month,
            HttpServletRequest request) {

        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        DynamicDataSource.setDataSource("health");
        Example example = new Example(TreatmentRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("doctor_id", doctorId);
        if (StringUtils.isNotBlank(year)) {
            //指定年指定月
            if (StringUtils.isNotBlank(month)) {
                String startDateStr = DateUtil.getMonthFirstDay(Integer.valueOf(year), Integer.valueOf(month));
                String endDateStr = DateUtil.getMonthLastDay(Integer.valueOf(year), Integer.valueOf(month));
                criteria.andBetween("start_time", DateUtils.format(startDateStr), DateUtils.format(endDateStr));
            } else {
                //指定年
                String startDateStr = year + "-01-01";
                String endDateStr = year + "-12-30";
                criteria.andBetween("start_time", DateUtils.format(startDateStr), DateUtils.format(endDateStr));
            }
        } else {
            //本年指定月
            if (StringUtils.isNotBlank(month)) {
                String startDateStr = DateUtil.getMonthFirstDay(Integer.valueOf(DateUtils.getYear()), Integer.valueOf(month));
                String endDateStr = DateUtil.getMonthLastDay(Integer.valueOf(DateUtils.getYear()), Integer.valueOf(month));
                criteria.andBetween("start_time", DateUtils.format(startDateStr), DateUtils.format(endDateStr));
            } else {
                //本年本月
                String startDateStr = DateUtil.getMonthFirstDay(Integer.valueOf(DateUtils.getYear()), Integer.valueOf(DateUtils.getMonth()));
                String endDateStr = DateUtil.getMonthFirstDay(Integer.valueOf(DateUtils.getYear()), Integer.valueOf(DateUtils.getMonth()));
                criteria.andBetween("start_time", DateUtils.format(startDateStr), DateUtils.format(endDateStr));
            }
        }
        MyPage<WzRecordVO> pageList = pcService.getRescordPageList(pageNo, pageSize, example);
        DynamicDataSource.clearDataSource();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (TreatmentRecord record : pageList.getContent()) {
            Map<String, Object> rtMap = new HashMap<String, Object>();
            rtMap.put("order_id", record.getService_id());
            rtMap.put("service_no", record.getService_no());
            rtMap.put("service_money", record.getService_money());
            rtMap.put("start_time", record.getStart_time());
            list.add(rtMap);
        }
        return Result.success(this.getSimplePageList(pageList, list));
    }

    //获取医生的就诊人的问诊记录列表
    @GetMapping("/getTreatmentRecordPageList")
    public Result getTreatmentRecordPageList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                             @RequestParam(value = "man_name", required = false) String manName,
                                             HttpServletRequest request) {
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        DynamicDataSource.setDataSource("health");

        Example example = new Example(TreatmentRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("doctor_id", doctorId);
        if (StringUtils.isNotBlank(manName)) {
            criteria.andLike("man_name", manName);
        }
        example.setOrderByClause("add_date DESC");
        MyPage<WzRecordVO> pageList = pcService.getRescordPageList(pageNo, pageSize, example);
        DynamicDataSource.clearDataSource();
        return Result.success(pageList);
    }

    //获取问诊记录详情
    @GetMapping("/getWzDetailRecord")
    public Result getWzDetailRecord(@RequestParam(value = "service_id") String serviceId,
                                    HttpServletRequest request) {
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        DynamicDataSource.setDataSource("health");
        Map<String, Object> rtMap = pcService.getWzDetailToMap(serviceId);
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    /**
     * 获取问诊申请列表
     * 1:等待医生接诊
     */
    @GetMapping("/getWzRequstPageList")
    public Result getWzRequstPageList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                      HttpServletRequest request) {
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        DynamicDataSource.setDataSource("health");
        Example example = new Example(TreatmentRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("doctor_id", doctorId);
        criteria.andLessThan("auditing", WzStatusEnum.FINISH_VISIT.getValue());
        example.setOrderByClause("auditing desc,add_date asc");
        MyPage<WzRecordVO> pageList = pcService.getRescordPageList(pageNo, pageSize, example);
        DynamicDataSource.clearDataSource();
        return Result.success(pageList);
    }

    //医生接诊
    @PostMapping("/doAcceptRequest")
    public Result doAcceptRequest(@RequestParam(value = "service_id") String serviceId,
                                  HttpServletRequest request) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            rtMap.put("set_status", 0);
            return Result.failure(rtMap, ResultCodeEnum.SESSION_TIMEOUT.getCode(), ResultCodeEnum.SESSION_TIMEOUT.getMsg());
        }

        //设置问诊状态、生产订单
        try {
            DynamicDataSource.setDataSource("health");
            TreatmentReserve treatmentReserve = pcService.setServStatusAndCreateOrder(serviceId);
            //通知内控app获取支付链接
            Map<String, Object> connMsg = new HashMap<String, Object>();
            connMsg.put("pay_type", "1");//问诊支付
            this.commService.setControlCmd(treatmentReserve.getDevice_code(), CmdEnum.CMD_PAY_LINK, connMsg);
            DynamicDataSource.clearDataSource();
        } catch (Exception e) {
            rtMap.put("set_status", 0);
            return Result.failure(ResultCodeEnum.INTERNAL_SERVER_ERROR);
        }

        //视频房间ID
        rtMap.put("roomID", serviceId);
        rtMap.put("set_status", 1);

        return Result.success(rtMap);
    }


    //获取视频秘钥
    @GetMapping("/getVideoKey")
    public Result getVideoKey(HttpServletRequest request) {
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        Map<String, Object> rtMap = pcService.getVideoSign(doctorId);
        return Result.success(rtMap);
    }

    /**
     * 获取问诊申请列表
     * 1:等待医生接诊
     * 2: 等待支付
     * 3: 视频问诊中
     * 4: 药剂师审核中
     * 5: 完成问诊
     * 6: 取消问诊
     */
    @PostMapping("/setAuditing")
    public Result setAuditing(@RequestBody JSONObject requestJson,
                              HttpServletRequest request) {
        String auditing = requestJson.getString("auditing");
        if (StringUtils.isBlank(auditing)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        Map<String, Object> rtMap = new HashMap<String, Object>();
        DynamicDataSource.setDataSource("health");
        TreatmentRecord treatmentRecord = new TreatmentRecord();
        treatmentRecord.setDoctor_id(doctorId);
        treatmentRecord.setAuditing(auditing);
        pcService.treatmentRecordMapper.updateByPrimaryKeySelective(treatmentRecord);
        DynamicDataSource.clearDataSource();

        return Result.success(rtMap);
    }

    //获取指定问诊状态
    @GetMapping("/getWzRequestStatus")
    public Result getWzRequestStatus(@RequestParam(value = "service_id") String serviceId,
                                     HttpServletRequest request) {
        String uid = getPcTokenValue(request);
        if (uid == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        if (StringUtils.isBlank(serviceId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        Map<String, Object> rtMap = new HashMap<String, Object>();
        DynamicDataSource.setDataSource("health");
        TreatmentRecord treatmentRecord = pcService.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        rtMap.put("service_status", Integer.valueOf(treatmentRecord.getAuditing()));
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //查看问诊请求的患者基本资料
    @GetMapping("/getWzManInfo")
    public Result getWzManInfo(@RequestParam(value = "service_id") String serviceId,
                               HttpServletRequest request) {

        String uid = getPcTokenValue(request);
        if (uid == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        if (StringUtils.isBlank(serviceId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        TreatmentRecord treatmentRecord = pcService.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        UserMemberMan userMemberMan = pcService.userMemberManMapper.selectByPrimaryKey(treatmentRecord.getMan_id());
        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("man_id", userMemberMan.getMan_id());
        rtMap.put("man_name", userMemberMan.getMan_name());
        rtMap.put("sex", userMemberMan.getSex());
        rtMap.put("nation", userMemberMan.getNation());
        rtMap.put("id_card", userMemberMan.getId_card());
        rtMap.put("marriage", userMemberMan.getMarriage());
        rtMap.put("both_date", userMemberMan.getBoth_date());
        rtMap.put("mobile_no", userMemberMan.getMobile_no());
        rtMap.put("address", userMemberMan.getAddress());
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //查看问诊请求的患者检查数据
    @GetMapping("/getWzManExamine")
    public Result getWzManExamine(@RequestParam(value = "service_id") String serviceId,
                                  HttpServletRequest request) {
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        if (StringUtils.isBlank(serviceId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        TreatmentRecord treatmentRecord = pcService.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("height", treatmentRecord.getHeight());
        rtMap.put("weight", treatmentRecord.getWeight());
        rtMap.put("temperature", treatmentRecord.getTemperature());
        rtMap.put("heart_rate", treatmentRecord.getHeart_rate());
        rtMap.put("sbp", treatmentRecord.getSbp());
        rtMap.put("dbp", treatmentRecord.getDbp());
        rtMap.put("endoscope", treatmentRecord.getDbp());
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //发送诊断信息给患者
    @PostMapping("/sendWzResultToMan")
    public Result sendWzResultToMan(@RequestBody DiagnoseVO diagnoseVO,
                                    HttpServletRequest request) {
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }

        DynamicDataSource.setDataSource("health");
        if (!pcService.saveDiagnoseInfo(diagnoseVO)) {
            return Result.failure(ResultCodeEnum.INTERNAL_SERVER_ERROR);
        }
        TreatmentRecord treatmentRecord = this.pcService.treatmentRecordMapper.selectByPrimaryKey(diagnoseVO.getService_id());
        //通知app获取诊断信息显示
        Map<String, Object> connMsg = new HashMap<String, Object>();
        connMsg.put("data", JSON.toJSONString(diagnoseVO));
        this.commService.setControlCmd(treatmentRecord.getDevice_code(), CmdEnum.SEND_WZ_RESULT, connMsg);

        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("send_status", 1);
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //发送药方清单给患者
    @PostMapping("/sendDrugListToMan")
    public Result sendDrugListToMan(@RequestBody JSONObject requestJson,
                                    HttpServletRequest request) {
        String serviceId = requestJson.getString("service_id");
        String doctorId = getPcTokenValue(request);

        if (doctorId == null || StringUtils.isBlank(serviceId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        String goodsListStr = requestJson.getString("goodsList");
        List<TreatmentRecordGoods> goodsList = JSONArray.parseArray(goodsListStr, TreatmentRecordGoods.class);
        Map<String, Object> rtMap = new HashMap<String, Object>();
        if (goodsList.size() == 0) {
            rtMap.put("send_status", 2);
            rtMap.put("msg", "没有要发送的药品");
            return Result.success(rtMap);
        }

        DynamicDataSource.setDataSource("health");
        //更新保存药品列表
        TreatmentRecord treatmentRecord = this.pcService.doUpdateRecordGoods(serviceId, goodsList);
        //通知app获取诊断信息显示
        Map<String, Object> connMsg = new HashMap<String, Object>();
        connMsg.put("data", JSON.toJSONString(goodsList));
        this.commService.setControlCmd(treatmentRecord.getDevice_code(), CmdEnum.SEND_GOODS_LIST, connMsg);

        rtMap.put("send_status", 1);
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //获取就诊人的所有问诊记录列表
    @GetMapping("/getWzManPageList")
    public Result getWzManPageList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "man_id", required = false) String manId,
                                   HttpServletRequest request) {
        String uid = getPcTokenValue(request);
        if (uid == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        DynamicDataSource.setDataSource("health");
        Example example = new Example(TreatmentRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("man_id", manId);
        MyPage<WzRecordVO> pageList = pcService.getRescordPageList(pageNo, pageSize, example);
        DynamicDataSource.clearDataSource();
        return Result.success(pageList);
    }

    //获取药品列表
    @GetMapping("/getGoodsPageList")
    public Result getGoodsPageList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "queryStr") String queryStr,
                                   HttpServletRequest request) {
        String uid = getPcTokenValue(request);
        if (uid == null) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        Example example = new Example(GoodsList.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goods_name", queryStr);
        criteria.orLike("goods_name", "%" + queryStr + "%").orLike("sick_label", "%" + queryStr + "%");
        MyPage<GoodsList> pageList = pcService.getGoodsPageList(pageNo, pageSize, example);
        DynamicDataSource.clearDataSource();
        return Result.success(pageList);
    }

    //药方提交审核
    @PostMapping("/doDrugAuditRequest")
    public Result doDrugAuditRequest(@RequestBody JSONObject requestJson,
                                     HttpServletRequest request) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        String serviceId = requestJson.getString("service_id");
        if (StringUtils.isBlank(serviceId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        String goodsListStr = requestJson.getString("goodsList");
        List<TreatmentRecordGoods> goodsList = JSONArray.parseArray(goodsListStr, TreatmentRecordGoods.class);
        if (goodsList.size() == 0) {
            rtMap.put("send_status", 2);
            rtMap.put("msg", "没有要审核的药品");
            return Result.success(rtMap);
        }
        DynamicDataSource.setDataSource("health");
        //更新保存药品列表
        TreatmentRecord treatmentRecord = this.pcService.doUpdateRecordGoods(serviceId, goodsList);
        //更新问诊单状态
        treatmentRecord.setAuditing(WzStatusEnum.WAITING_CHECK.getValue());
        treatmentRecord.setSub_check_status(1);
        treatmentRecord.setCheck_result(0);
        treatmentRecord.setSub_check_time(new Date());
        pcService.treatmentRecordMapper.updateByPrimaryKeySelective(treatmentRecord);
        rtMap.put("req_status", 1);
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //获取药方审核状态
    @GetMapping("/getDrugCheckStatus")
    public Result getDrugCheckStatus(@RequestParam(value = "service_id") String serviceId,
                                     HttpServletRequest request) {
        String uid = getPcTokenValue(request);
        if (uid == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        Map<String, Object> rtMap = new HashMap<String, Object>();
        DynamicDataSource.setDataSource("health");
        TreatmentRecord treatmentRecord = pcService.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        rtMap.put("sub_check_status", treatmentRecord.getSub_check_status());
        rtMap.put("check_result", treatmentRecord.getCheck_result());
        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }

    //获取等待审核的处方单列表
    @GetMapping("/getAuditPageList")
    public Result getAuditPageList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "check_result", required = false) Integer checkResult,
                                   HttpServletRequest request) {
        String doctorId = getPcTokenValue(request);
        if (doctorId == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        DynamicDataSource.setDataSource("health");
        Example example = new Example(TreatmentRecord.class);
        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("doctor_id", doctorId);
        criteria.andEqualTo("sub_check_status", 1);//获取已提交的问诊药方单
        criteria.andEqualTo("check_result", checkResult);//根据审核状态搜索0未审核1通过2不通过
        MyPage<WzRecordVO> pageList = pcService.getRescordPageList(pageNo, pageSize, example);
        DynamicDataSource.clearDataSource();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (TreatmentRecord record : pageList.getContent()) {
            Map<String, Object> rtMap = new HashMap<String, Object>();
            rtMap.put("service_id", record.getService_id());
            rtMap.put("doctor_name", record.getDoctor_name());
            rtMap.put("diagnosis_result", record.getDiagnosis_result());
            rtMap.put("sub_check_time", record.getSub_check_time());
            rtMap.put("check_time", record.getCheck_time());
            rtMap.put("check_remark", record.getCheck_remark());
            rtMap.put("check_result", record.getCheck_result());
            list.add(rtMap);
        }
        return Result.success(this.getSimplePageList(pageList, list));
    }

    //根据问诊id获取药品列表
    @GetMapping("/getWzGoodsList")
    public Result getWzGoodsList(@RequestParam(value = "service_id") String serviceId,
                                 HttpServletRequest request) {
        String uid = getPcTokenValue(request);
        if (uid == null || StringUtils.isBlank(serviceId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        DynamicDataSource.setDataSource("health");
        TreatmentRecordGoods tr = new TreatmentRecordGoods();
        tr.setService_id(serviceId);
        List<TreatmentRecordGoods> goodsList = pcService.treatmentRecordGoodsMapper.select(tr);
        DynamicDataSource.clearDataSource();
        return Result.success(goodsList);
    }

    //保存审核结果
    @PostMapping("/setAuditResult")
    public Result saveAuditResult(@RequestBody JSONObject requestJson,
                                  HttpServletRequest request) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        String uid = getPcTokenValue(request);
        String serviceId = requestJson.getString("service_id");
        Integer checkResult = requestJson.getInteger("check_result");
        String remark = requestJson.getString("check_remark");
        if (uid == null) {
            return Result.failure(ResultCodeEnum.SESSION_TIMEOUT);
        }
        if (StringUtils.isBlank(serviceId)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        if (checkResult == 2 && StringUtils.isBlank(remark)) {
            rtMap.put("set_status", 2);
            rtMap.put("msg", "退回原因不能为空");
            return Result.success(rtMap);
        }

        DynamicDataSource.setDataSource("health");
        TreatmentRecord treatmentRecord = this.pcService.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        treatmentRecord.setCheck_time(new Date());
        treatmentRecord.setCheck_result(checkResult);
        treatmentRecord.setCheck_remark(remark);
        if (checkResult == 2) {
            //退回，则返回视频通话状态
            treatmentRecord.setAuditing(WzStatusEnum.VIDEO_VISIT.getValue());
        } else {
            //通过，则问诊完成状态
            treatmentRecord.setAuditing(WzStatusEnum.FINISH_VISIT.getValue());
        }
        //更新问诊记录状态
        pcService.treatmentRecordMapper.updateByPrimaryKeySelective(treatmentRecord);
        if (checkResult == 1) {
            //通知内控app获取支付链接
            Map<String, Object> connMsg = new HashMap<String, Object>();
            connMsg.put("pay_type", "2");//药品支付
            this.commService.setControlCmd(treatmentRecord.getDevice_code(), CmdEnum.CMD_PAY_LINK, connMsg);
        }
        DynamicDataSource.clearDataSource();
        rtMap.put("set_status", 1);
        return Result.success(rtMap);
    }

    @PostMapping("/doExamineByType")
    public Result doExamineByType(@RequestBody JSONObject requestJson,
                                  HttpServletRequest request) {
        Integer exaType = requestJson.getInteger("exa_type");
        String serviceId = requestJson.getString("service_id");
        Integer isFinished = requestJson.getInteger("is_finished");
        String doctorId = getPcTokenValue(request);
        if (doctorId == null || StringUtils.isBlank(exaType + "") || StringUtils.isBlank(serviceId)) {
            return Result.failure(ResultCodeEnum.PARAMS_MISS);
        }
        Map<String, Object> rtMap = new HashMap<String, Object>();

        DynamicDataSource.setDataSource("health");
        TreatmentRecord treatmentRecord = this.pcService.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        if (treatmentRecord == null) {
            DynamicDataSource.clearDataSource();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
        TreatmentReserve treatmentReserve = this.pcService.treatmentReserveMapper.selectByPrimaryKey(treatmentRecord.getReserve_id());
        if (treatmentReserve == null) {
            DynamicDataSource.clearDataSource();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
        Map<String, Object> connMsg = new HashMap<String, Object>();
        if(isFinished == 1){
            connMsg.put("isFinished", 1);
        }else {
            connMsg.put("roomId", treatmentRecord.getService_id());
        }
        //通知app获取诊断信息显示
        CmdEnum cmdEnum = null;
        switch (exaType) {
            case 1:
                cmdEnum = CmdEnum.CMD_BLOOD_PRE;
                break;
            case 2:
                cmdEnum = CmdEnum.CMD_ENDOSCOPE;
                this.commService.setControlCmd(treatmentReserve.getDevice_code(), cmdEnum, connMsg);
                break;
            case 3:
                cmdEnum = CmdEnum.CMD_ELE_AUS;
                this.commService.setControlCmd(treatmentReserve.getDevice_code(), cmdEnum, connMsg);
                break;
            case 4:
                cmdEnum = CmdEnum.CMD_HEAR_RATE;
                break;
            default:
                break;
        }
        if (cmdEnum != null ) {
            if(isFinished != 1) {
                this.commService.setControlCmd(treatmentRecord.getDevice_code(), cmdEnum, null);
            }
            rtMap.put("ctl_status", 1);
        } else {
            rtMap.put("ctl_status", 2);
            rtMap.put("msg", "操作类型错误！");
        }

        DynamicDataSource.clearDataSource();
        return Result.success(rtMap);
    }


    private MyPage<Map<String, Object>> getSimplePageList(MyPage<WzRecordVO> pageList, List<Map<String, Object>> list) {
        MyPage<Map<String, Object>> rtList = new MyPage<Map<String, Object>>();
        rtList.setPageNo(pageList.getPageNo());
        rtList.setPageSize(pageList.getPageSize());
        rtList.setTotalCount(pageList.getTotalCount());
        rtList.setContent(list);
        return rtList;
    }
}
