package com.duo.thread.parse;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseService;
import com.duo.core.utils.RedisUtil;
import com.duo.core.utils.StringUtils;
import com.duo.modules.health.entity.DeviceControlMessage;
import com.duo.netty.bean.BaseMessage;
import com.duo.netty.config.NettyServerHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author [ Yonsin ] on [2020/8/18]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class ThreadPoolsParseMain extends Thread {
    public int _threadnum = 10;
    private Semaphore semaphore = null;

    public void init(String num) {
        try {
            if (num == null || num.equals("")) {
                num = "10";
            }
            _threadnum = Integer.valueOf(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        semaphore = new Semaphore(_threadnum);
        while (!NettyServerHolder.isStop) {
            try {
                sleep(500);// 0.5s
               // while (NettyServerHolder.MSGlinkedList.size() > 0 ) {
                while (RedisUtil.lGetListSize("nettylinkedList") > 0 ) {
                    boolean bacquire = semaphore.tryAcquire(10000, TimeUnit.MILLISECONDS);
                    if (bacquire) {
                        BaseMessage msg=null;
                        try {
                             msg = JSONObject.toJavaObject(JSONObject.parseObject((String)RedisUtil.rPop("nettylinkedList", 3)), BaseMessage.class);//左边入，右边出。实现先进先出
                        }catch (Exception e){

                        }
                        if(msg!=null) {
                            new MsgDealThread(msg, semaphore).start();
                        }
                    } else {
                        log.warn("MsgParseThread Can't acquire permit from semaphore! _threadnum:" + _threadnum + " availablePermits:" + semaphore.availablePermits());
                    }
                }
            } catch (Exception e) {
                log.error("MsgParseThread error !", e);
            }
        }
    }

    /**
     * 处理agent发送上来的回报报文
     *
     */
    private class MsgDealThread extends Thread {
        private BaseMessage _msg = null;
        private Semaphore _semaphore = null;

        public MsgDealThread(BaseMessage msg, Semaphore semaphore) {
            this._msg = msg;
            this._semaphore = semaphore;
        }

        @Override
        public void run() {

            try {
                //解释message
                log.info("收到客户端消息---->" + JSON.toJSONString(this._msg));
                Map<String, Object> msgMap = JSON.parseObject(this._msg.getConnect_message());
                if (msgMap==null || (!msgMap.containsKey("msgId") && StringUtils.isBlank(msgMap.get("msgId").toString()))) {
                    return;
                }
                DynamicDataSource.setDataSource("health");
                DeviceControlMessage msg = (DeviceControlMessage) BaseService.getMapper("DeviceControlMessage").selectByPrimaryKey(msgMap.get("msgId").toString());
                msg.setAuditing("1");
                msg.setModify_date(new Date());
                BaseService.getMapper("DeviceControlMessage").updateByPrimaryKey(msg);
                DynamicDataSource.clearDataSource();
            } catch (Exception e) {
                log.error("MsgDealThread deal msg error !", e);
            } finally {
                _semaphore.release();
            }

        }
    }

}
