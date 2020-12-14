package com.duo.modules.health.service;

import com.duo.config.DynamicDataSource;
import com.duo.config.properties.VcallProperties;
import com.duo.core.PasswordHelper;
import com.duo.core.utils.*;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.health.entity.*;
import com.duo.modules.health.mapper.*;
import com.duo.modules.health.model.*;
import com.duo.modules.system.entity.SystemUser;
import com.duo.modules.system.mapper.SystemUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencentyun.TLSSigAPIv2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Slf4j
@Service
public class PcService {

    @Autowired(required = false)
    public UserDoctorMapper userDoctorMapper;

    @Autowired(required = false)
    public TreatmentRecordMapper treatmentRecordMapper;

    @Autowired(required = false)
    public UserMemberManMapper userMemberManMapper;

    @Autowired(required = false)
    public GoodsListMapper goodsListMapper;

    @Autowired(required = false)
    public TreatmentRecordGoodsMapper treatmentRecordGoodsMapper;

    @Autowired(required = false)
    public UserDoctorSchedualMapper userDoctorSchedualMapper;

    @Autowired
    public LayoutService layoutService;

    @Autowired(required = false)
    public UserMemberAddressMapper expressAddressMapper;

    @Autowired
    public VcallProperties vcallProperties;

    @Autowired(required = false)
    public OrderRecordMapper orderRecordMapper;

    @Autowired(required = false)
    public OrderRecordGoodsMapper orderRecordGoodsMapper;

    @Autowired(required = false)
    public SystemUserMapper systemUserMapper;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired(required = false)
    public UserMemberMapper userMemberMapper;

    @Autowired(required = false)
    public TreatmentReserveMapper treatmentReserveMapper;

    @Autowired
    public CommService commService;

    public SystemUser doCheckAccount(String account, String pwd,String newPwd) {
        DynamicDataSource.setDataSource("duo");
        SystemUser systemUser = new SystemUser();
        systemUser.setUser_code(account);
        SystemUser user = this.systemUserMapper.selectOne(systemUser);
        if (user == null) {
            return null;
        }
        String uPwd = passwordHelper.getEncryptPassword(account, user.getUser_salt(), pwd);
        if (!StringUtils.equals(uPwd, user.getUser_password())) {
            return null;
        }
        //修改密码
        if(StringUtils.isNotBlank(newPwd)){
            String newPassword = passwordHelper.getEncryptPassword(account, user.getUser_salt(), newPwd);
            user.setUser_password(newPassword);
            user.setModify_date(new Date());
            this.systemUserMapper.updateByPrimaryKeySelective(user);
        }
        DynamicDataSource.clearDataSource();
        return user;
    }

    public Map<String, Object> getWzDetailToMap(String serviceId) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        TreatmentReserve treatmentReserve = this.treatmentReserveMapper.selectByPrimaryKey(serviceId);
        TreatmentRecord treatmentRecord = this.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        UserDoctor userDoctor = this.userDoctorMapper.selectByPrimaryKey(treatmentRecord.getDoctor_id());
        UserMemberMan userMemberMan = this.userMemberManMapper.selectByPrimaryKey(treatmentRecord.getMan_id());
        if (treatmentReserve == null || treatmentRecord == null || userDoctor == null || userMemberMan == null) {
            return rtMap;
        }
        rtMap.put("manInfo", userMemberMan);
        WzObjectVO wzObjectVO = new WzObjectVO();
        wzObjectVO.setService_id(treatmentRecord.getService_id());
        wzObjectVO.setService_no(treatmentRecord.getService_no());
        wzObjectVO.setStart_time(treatmentRecord.getStart_time());
        wzObjectVO.setDevice_code(treatmentRecord.getDevice_code());
        wzObjectVO.setDevice_address(treatmentRecord.getDevice_address());
        wzObjectVO.setDoctor_name(treatmentRecord.getDoctor_name());
        wzObjectVO.setHospital_name(userDoctor.getHospital_name());
        wzObjectVO.setOffice_room(userDoctor.getOffice_room());
        wzObjectVO.setAdd_date(treatmentRecord.getAdd_date());
        wzObjectVO.setEnd_time(treatmentRecord.getEnd_time());
        wzObjectVO.setService_status(treatmentRecord.getAuditing());
        rtMap.put("WzObject", wzObjectVO);
        DiagnoseVO diagnoseVO = new DiagnoseVO();
        diagnoseVO.setSymptom_describe(treatmentRecord.getSymptom_describe());
        diagnoseVO.setCurrent_history(treatmentRecord.getCurrent_history());
        diagnoseVO.setPast_history(userMemberMan.getPast_history());
        diagnoseVO.setDrug_allergy(userMemberMan.getDrug_allergy());
        diagnoseVO.setGenetic_disease(userMemberMan.getGenetic_disease());
        diagnoseVO.setDiagnosis_result(treatmentRecord.getDiagnosis_result());
        diagnoseVO.setDiagnosis_suggest(treatmentRecord.getDiagnosis_suggest());
        rtMap.put("Diagnosis", diagnoseVO);
        CheckInfoVO checkInfoVO = new CheckInfoVO();
        checkInfoVO.setHeight(treatmentRecord.getHeight());
        checkInfoVO.setWeight(treatmentRecord.getWeight());
        checkInfoVO.setTemperature(treatmentRecord.getTemperature());
        checkInfoVO.setHeart_rate(treatmentRecord.getHeart_rate());
        checkInfoVO.setDbp(treatmentRecord.getDbp());
        checkInfoVO.setSbp(treatmentRecord.getSbp());
        checkInfoVO.setEndoscope(treatmentRecord.getEndoscope());
        rtMap.put("CheckInfo", checkInfoVO);
        TreatmentRecordGoods treatmentRecordGoods = new TreatmentRecordGoods();
        treatmentRecordGoods.setService_id(serviceId);
        List<TreatmentRecordGoods> goodsList = this.treatmentRecordGoodsMapper.select(treatmentRecordGoods);
        rtMap.put("GoodsList", goodsList);
        UserMember userMember = this.userMemberMapper.selectByPrimaryKey(treatmentRecord.getMember_id());
        UserMemberAddress expressAddress = this.expressAddressMapper.selectByPrimaryKey(userMember.getRec_id());
        rtMap.put("ExpressAddr", expressAddress);
        PayVO payVO = new PayVO();
        payVO.setWz_order_no(treatmentReserve.getReserve_no());
        payVO.setWz_pay_money(treatmentReserve.getPay_money());
        payVO.setWz_pay_status(treatmentReserve.getPay_status());
        payVO.setGoods_order_no(treatmentRecord.getService_no());
        payVO.setGoods_pay_money(treatmentRecord.getPay_money());
        payVO.setGoods_pay_status(treatmentRecord.getPay_status());
        rtMap.put("PayObject", payVO);
        return rtMap;
    }

    public MyPage<WzRecordVO> getRescordPageList(Integer pageNo, Integer pageSize, Example example) {
        PageHelper.startPage(pageNo, pageSize);
        List<TreatmentRecord> activityList = treatmentRecordMapper.selectByExample(example);
        PageInfo<TreatmentRecord> pageInfo = new PageInfo<TreatmentRecord>(activityList);
        MyPage<WzRecordVO> page = new MyPage<WzRecordVO>();
        page.setPageSize(pageSize);
        page.setPageNo(pageNo);
        page.setTotalCount(pageInfo.getTotal());
        List<WzRecordVO> wzRecordVOList = new ArrayList<WzRecordVO>();
        for (TreatmentRecord record : pageInfo.getList()) {
            WzRecordVO wzRecordVO = new WzRecordVO();
            BeanUtils.copyProperties(record, wzRecordVO);
            wzRecordVO.setService_status(Integer.valueOf(record.getAuditing()));
            UserDoctor userDoctor = this.userDoctorMapper.selectByPrimaryKey(record.getDoctor_id());
            wzRecordVO.setOffice_room(userDoctor.getOffice_room());
            wzRecordVOList.add(wzRecordVO);
        }
        page.setContent(wzRecordVOList);
        return page;
    }

    public MyPage<GoodsList> getGoodsPageList(Integer pageNo, Integer pageSize, Example example) {
        PageHelper.startPage(pageNo, pageSize);
        List<GoodsList> activityList = goodsListMapper.selectByExample(example);
        PageInfo<GoodsList> pageInfo = new PageInfo<GoodsList>(activityList);
        MyPage<GoodsList> page = new MyPage<GoodsList>();
        page.setPageSize(pageSize);
        page.setPageNo(pageNo);
        page.setTotalCount(pageInfo.getTotal());
        page.setContent(pageInfo.getList());
        return page;
    }

    public Boolean saveDiagnoseInfo(DiagnoseVO diagnoseVO) {
        TreatmentRecord treatmentRecord = treatmentRecordMapper.selectByPrimaryKey(diagnoseVO.getService_id());
        if (treatmentRecord == null) {
            return false;
        }
        treatmentRecord.setSymptom_describe(diagnoseVO.getSymptom_describe());
        treatmentRecord.setCurrent_history(diagnoseVO.getCurrent_history());
        treatmentRecord.setDiagnosis_result(diagnoseVO.getDiagnosis_result());
        treatmentRecord.setDiagnosis_suggest(diagnoseVO.getDiagnosis_suggest());
        treatmentRecordMapper.updateByPrimaryKeySelective(treatmentRecord);
        UserMemberMan userMemberMan = userMemberManMapper.selectByPrimaryKey(treatmentRecord.getMan_id());
        userMemberMan.setPast_history(diagnoseVO.getPast_history());
        userMemberMan.setDrug_allergy(diagnoseVO.getDrug_allergy());
        userMemberMan.setGenetic_disease(diagnoseVO.getGenetic_disease());
        userMemberManMapper.updateByPrimaryKeySelective(userMemberMan);
        return true;
    }

    public Map<String, Object> getVideoSign(String userId) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        TLSSigAPIv2 api = new TLSSigAPIv2(vcallProperties.getSdkAppId(), vcallProperties.getSecretKey());
        String userSig = api.genSig(userId, vcallProperties.getExpireTime());
        rtMap.put("sdkAppId", vcallProperties.getSdkAppId());
        rtMap.put("userSig", userSig);
        return rtMap;
    }

    public TreatmentReserve setServStatusAndCreateOrder(String serviceId) {
        TreatmentRecord treatmentRecord = this.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        if (treatmentRecord == null) {
            return null;
        }
        treatmentRecord.setAuditing(WzStatusEnum.WAITING_PAY.getValue());
        treatmentRecord.setStart_time(new Date());
        this.treatmentRecordMapper.updateByPrimaryKeySelective(treatmentRecord);
        return this.createOrder(treatmentRecord, null, treatmentRecord.getService_money(), treatmentRecord.getService_money());
    }

    public TreatmentReserve createOrder(TreatmentRecord treatmentRecord, Double discountMoney, Double orderMoney, Double payMoney) {
        TreatmentReserve treatmentReserve = new TreatmentReserve();
        treatmentReserve.setReserve_id(treatmentRecord.getService_id());
        treatmentReserve.setReserve_no(this.commService.getOrderNo("NO"));
        treatmentReserve.setAdd_date(new Date());
        treatmentReserve.setReserve_source("app");
        treatmentReserve.setPay_type("wxPay");
        treatmentReserve.setPay_status("0");//0:未支付 1:支付成功  2:支付失败
        treatmentReserve.setOrder_money(orderMoney);
        treatmentReserve.setPay_money(payMoney);
        treatmentReserve.setDiscount_money(discountMoney);
        treatmentReserve.setDevice_id(treatmentRecord.getDevice_id());
        treatmentReserve.setDevice_code(treatmentRecord.getDevice_code());
        treatmentReserve.setDevice_name(treatmentRecord.getDevice_name());
        treatmentReserve.setMember_id(treatmentRecord.getMember_id());
        treatmentReserve.setMember_name(treatmentRecord.getMember_name());
        treatmentReserve.setDoctor_id(treatmentRecord.getDoctor_id());
        treatmentReserve.setDoctor_name(treatmentRecord.getDoctor_name());
        this.treatmentReserveMapper.insertSelective(treatmentReserve);
        return treatmentReserve;
    }

    public TreatmentRecord doUpdateRecordGoods(String serviceId, List<TreatmentRecordGoods> goodsList) {
        //删除问诊的历史药品列表
        TreatmentRecordGoods treatmentRecordGoods = new TreatmentRecordGoods();
        treatmentRecordGoods.setService_id(serviceId);
        this.treatmentRecordGoodsMapper.delete(treatmentRecordGoods);
        Double totalPrice = 0.0;
        //重新添加
        for (TreatmentRecordGoods goods : goodsList) {
            goods.setDet_id(layoutService.getKeyUID());
            goods.setService_id(serviceId);
            this.treatmentRecordGoodsMapper.insertSelective(goods);
            totalPrice += goods.getGoods_money();
        }
        TreatmentRecord treatmentRecord = this.treatmentRecordMapper.selectByPrimaryKey(serviceId);
        treatmentRecord.setPay_money(totalPrice);
        treatmentRecord.setGoods_money(totalPrice);
        treatmentRecord.setOrder_money(totalPrice);
        treatmentRecord.setDiscount_money(0.0);
        treatmentRecord.setPay_status("0");
        this.treatmentRecordMapper.updateByPrimaryKeySelective(treatmentRecord);
        return treatmentRecord;
    }

}
