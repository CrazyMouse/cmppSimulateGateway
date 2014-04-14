package com.crazymouse.netty.handler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title ：
 * Description :
 * Create Time: 12-5-2 上午10:35
 *
 * @version 1.0
 * @author: tanyang
 */


public class PerformanceThread implements Runnable {
    private Integer period = 10000;

    private static Logger log = LoggerFactory.getLogger(PerformanceThread.class);
    private long submit = 0L;
    private long resp = 0L;
    private long report = 0L;

    private boolean running = true;
    private Performance performance;

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    @Override
    public void run() {
        log.info("数据计数开始!");
        while (running) {
            long temp = performance.submitCount.longValue();
            long speed = (temp - submit) / (period / 1000);
            submit = temp;
            log.info("[--" + performance.getName() +
                    "--]Submit:{},Resp:{},Report{},SubmitSpeed:{}", new Object[]{performance.submitCount.longValue(), performance.respCount.longValue(), performance.reportCount.longValue(), speed});
            try {
                Thread.sleep(period);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        log.info("数据计数结束!");
    }

    public void stop() {
        this.running = false;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
