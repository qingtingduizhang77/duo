package com.duo.thread.monitor;

import com.duo.core.utils.RedisUtil;
import com.duo.netty.bean.BaseMessage;
import com.duo.netty.config.NettyServerHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author [ Yonsin ] on [2020/8/18]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class ThreadPoolsMonitorMain extends Thread {


    @Override
    public void run() {
        while (!NettyServerHolder.isStop) {
            try {
                sleep(30000);// 30s
                log.info("MSGlinkedList:"+RedisUtil.lGetListSize("nettylinkedList"));
                log.info("ChannelId2ChannelMAP:"+NettyServerHolder.ChannelId2ChannelMAP.size());
                log.info("ChannelId2TermIdMAP:"+NettyServerHolder.ChannelId2TermIdMAP.size());
                log.info("TermId2ChannelIdMAP:"+NettyServerHolder.TermId2ChannelIdMAP.size());
                log.info("ONLineMap:"+NettyServerHolder.ONLineMap.size());
                log.info("OFFLineMap:"+NettyServerHolder.OFFLineMap.size());
                log.info("OnlineTermList:"+NettyServerHolder.OnlineTermList.size());

            } catch (Exception e) {
                log.error("ThreadPoolsMonitorMain error !", e);
            }
        }
    }


}
