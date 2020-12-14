package com.duo.thread.monitor;

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
public class ThreadPoolsOnAndOffLineMain extends Thread {
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
                //上线操作
                if (NettyServerHolder.ONLineMap.size() > 0 ) {
                    boolean bacquire = semaphore.tryAcquire(10000, TimeUnit.MILLISECONDS);
                    if (bacquire) {

                    } else {
                        log.warn("OnlineAndOfflineThread Can't acquire permit from semaphore! _threadnum:" + _threadnum + " availablePermits:" + semaphore.availablePermits());
                    }
                }
                //下线操作
                if (NettyServerHolder.OFFLineMap.size() > 0 ) {
                    boolean bacquire = semaphore.tryAcquire(10000, TimeUnit.MILLISECONDS);
                    if (bacquire) {

                    } else {
                        log.warn("OfflineAndOfflineThread Can't acquire permit from semaphore! _threadnum:" + _threadnum + " availablePermits:" + semaphore.availablePermits());
                    }
                }
            } catch (Exception e) {
                log.error("MsgParseThread error !", e);
            }
        }
    }

    /**
     * 处理上下线操作
     *
     */
    private class OnlineThread extends Thread {
        private List<String> _deviceId = null;
        private Semaphore _semaphore = null;

        public OnlineThread(List<String> deviceId, Semaphore semaphore) {
            this._deviceId = deviceId;
            this._semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                //解释message


            } catch (Exception e) {
                log.error("OnlinAndOfflineThread deal msg error !", e);
            } finally {
                _semaphore.release();
            }
        }
    }


    private class OfflineThread extends Thread {
        private List<String> _deviceId = null;
        private Semaphore _semaphore = null;

        public OfflineThread(List<String> deviceId, Semaphore semaphore) {
            this._deviceId = deviceId;
            this._semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                //解释message


            } catch (Exception e) {
                log.error("OfflinAndOfflineThread deal msg error !", e);
            } finally {
                _semaphore.release();
            }
        }
    }

}
