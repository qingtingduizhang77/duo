package com.duo.thread.putdown;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseService;
import com.duo.core.utils.DateUtils;
import com.duo.core.utils.RedisUtil;
import com.duo.modules.health.entity.DeviceControlMessage;
import com.duo.modules.health.mapper.DeviceControlMessageMapper;
import com.duo.modules.system.entity.SystemMajorfunc;
import com.duo.netty.bean.BaseMessage;
import com.duo.netty.config.NettyServerHolder;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author [ Yonsin ] on [2020/8/18]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class ThreadPoolsPutdownMain extends Thread {


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
        DynamicDataSource.setDataSource("health");
        semaphore = new Semaphore(_threadnum);
        while (!NettyServerHolder.isStop) {
            try {
                sleep(500);// 0.5s
                Example example = new Example(DeviceControlMessage.class);
                example.setOrderByClause("plan_time asc");
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("auditing", "0");
                //在线设备在当前服务器的
                List<DeviceControlMessage> putdownLs = BaseService.getMapper("DeviceControlMessage").selectByExample(example);
                log.info("待发送消息---->" + putdownLs.size() + " 条");

                if (putdownLs != null)
                    for (int i = 0; i < putdownLs.size(); i++) {
                        String device_id = putdownLs.get(i).getDevice_id();
                        if (!NettyServerHolder.ChannelId2TermIdMAP.contains(device_id)) {
                            log.info("客户端（" + device_id + "）未连接！");
                            continue;//判断是否是连接到本服务器
                        }

                        boolean bacquire = semaphore.tryAcquire(10000, TimeUnit.MILLISECONDS);
                        if (bacquire) {
                            DeviceControlMessage msg = putdownLs.get(i);
                            if (msg != null) new MsgPutdownThread(msg, semaphore).start();
                        } else {
                            log.warn("请求未允许！MsgPutdownThread Can't acquire permit from semaphore! _threadnum:" + _threadnum + " availablePermits:" + semaphore.availablePermits());
                        }
                    }
            } catch (Exception e) {
                log.error("出错了！MsgParseThread error !", e);
            }
        }
        DynamicDataSource.clearDataSource();
    }

    /**
     * 处理agent发送上来的回报报文
     */
    private class MsgPutdownThread extends Thread {
        private DeviceControlMessage _msg = null;
        private Semaphore _semaphore = null;

        public MsgPutdownThread(DeviceControlMessage msg, Semaphore semaphore) {
            this._msg = msg;
            this._semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                //下发message
                String device_id = _msg.getDevice_id();
                String channel_id = NettyServerHolder.TermId2ChannelIdMAP.get(device_id);
                NioSocketChannel nioSocketChannel = NettyServerHolder.ChannelId2ChannelMAP.get(channel_id);
                //发送消息
                BaseMessage heartBeat = new BaseMessage();
                heartBeat.setDevice_id(device_id);
                heartBeat.setMessage_type(_msg.getMessage_type());
                heartBeat.setConnect_message(_msg.getConnect_message());
                nioSocketChannel.writeAndFlush(heartBeat);
                log.info("设备：" + device_id + "-->发送成功！");

                JSONObject jsonObject = JSON.parseObject(_msg.getConnect_message());
                String key = jsonObject.getString("msgId");
                if (!RedisUtil.hHasKey("SendCount", key)) {
                    RedisUtil.hset("SendCount", key, 0,  60);//一分钟
                }
                RedisUtil.hincr("SendCount", key, 1);
                String sendCount = RedisUtil.hget("SendCount", key).toString();
                //连续发送超过10次，则设置为失败！
                if(Integer.valueOf(sendCount) > 10){
                    _msg.setAuditing("2");
                    DynamicDataSource.setDataSource("health");
                    BaseService.getMapper("DeviceControlMessage").updateByPrimaryKey(_msg);
                    DynamicDataSource.clearDataSource();
                }

            } catch (Exception e) {
                log.error("MsgPutdownThread deal msg error !", e);
            } finally {
                _semaphore.release();
            }
        }
    }
}
