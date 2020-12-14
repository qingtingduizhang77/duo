package com.duo.modules.health.service;

import com.alibaba.fastjson.JSON;
import com.duo.core.utils.*;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.health.entity.*;
import com.duo.modules.health.mapper.*;
import com.duo.modules.health.model.WxPayNotifyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CommService {

    @Autowired(required = false)
    public BaseFieldMapper baseFieldMapper;


    @Autowired(required = false)
    private TreatmentRecordMapper treatmentRecordMapper;

    @Autowired
    public LayoutService layoutService;

    @Autowired(required = false)
    public DeviceControlMessageMapper deviceControlMessageMapper;

    @Autowired(required = false)
    public TreatmentReserveMapper treatmentReserveMapper;

    @Autowired
    public RedisUtil redisUtil;

    public void updateOrderStatus(String orderNo) {
        TreatmentReserve treatmentReserve = new TreatmentReserve();
        treatmentReserve.setReserve_no(orderNo);
        List<TreatmentReserve> reserveList = this.treatmentReserveMapper.select(treatmentReserve);

        if (reserveList.size() >0){  //问诊缴费
            this.doUpdateOrderAndRecordStatus(reserveList.get(0));
        }else {
            TreatmentRecord treatmentRecord = new TreatmentRecord();
            treatmentRecord.setService_no(orderNo);
            TreatmentRecord order = this.treatmentRecordMapper.selectOne(treatmentRecord);
            order.setPay_time(new Date());
            order.setModify_date(new Date());
            order.setPay_status("1");
            this.treatmentRecordMapper.updateByPrimaryKeySelective(order);

            //通知内控app
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("msg", "支付成功！");
            this.setControlCmd(order.getDevice_code(), CmdEnum.CMD_PAY_FINISH,map);
        }
    }

    private void doUpdateOrderAndRecordStatus(TreatmentReserve order) {
        order.setPay_status("1");
        order.setPay_time(new Date());
        order.setModify_date(new Date());
        //更新订单状态
        this.treatmentReserveMapper.updateByPrimaryKeySelective(order);

        //更新问诊单状态
        TreatmentRecord treatmentRecord = this.treatmentRecordMapper.selectByPrimaryKey(order.getReserve_id());
        treatmentRecord.setAuditing(WzStatusEnum.VIDEO_VISIT.getValue());//问诊状态
        this.treatmentRecordMapper.updateByPrimaryKeySelective(treatmentRecord);

        //通知内控app
        this.doSendMsgToInnerApp(treatmentRecord.getDevice_code());
    }

    private void doSendMsgToInnerApp(String innerDevCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", "支付成功！");
        this.setControlCmd(innerDevCode, CmdEnum.CMD_DO_VISIT,map);
    }

    public void setControlCmd(String deviceId, CmdEnum cmd, Map<String,Object> connMsg){
        DeviceControlMessage deviceControlMessage = new DeviceControlMessage();
        deviceControlMessage.setMessage_id(layoutService.getKeyUID());
        deviceControlMessage.setAdd_date(new Date());
        deviceControlMessage.setAuditing("0");
        deviceControlMessage.setDevice_id(deviceId);
        deviceControlMessage.setMessage_type(cmd.getValue());
        if (connMsg == null){
            connMsg = new HashMap<String, Object>();
        }
        connMsg.put("typeDes", cmd.getDesc());
        connMsg.put("msgId",deviceControlMessage.getMessage_id());
        deviceControlMessage.setConnect_message(JSON.toJSONString(connMsg));
        this.deviceControlMessageMapper.insertSelective(deviceControlMessage);
    }

    public String getOrderNo(String preStr) {
        String key = DateUtils.getDateString(new Date());
        if (!redisUtil.hHasKey("OrderNo", key)) {
            redisUtil.hset("OrderNo", key, 0, DateUtils.getMillisOfDay(new Date()) / 1000);
        }
        redisUtil.hincr("OrderNo", key, 1);
        String num = redisUtil.hget("OrderNo", key).toString();
        String orderNo = preStr+ ToolsUtil.getStringDate()+ String.format("%05d",Integer.valueOf(num));
        return orderNo;
    }
}
