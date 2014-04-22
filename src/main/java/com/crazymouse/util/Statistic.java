package com.crazymouse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Title ：统计输出
 * Description :调用 {@link #logOutSpeed()}方法可实现周期速度统计
 * Create Time: 14-4-4 下午12:50
 */
public class Statistic {
    private final static Logger logger = LoggerFactory.getLogger(Statistic.class);

    private static AtomicInteger submitCount = new AtomicInteger(0);
    private static AtomicInteger deliverCount = new AtomicInteger(0);
    private int lastSbumitCount = 0;
    private int lastDeliverCount = 0;

    public static void addSubmit(Integer num) {
        submitCount.addAndGet(num);
    }

    public static void addSubmit() {
        addSubmit(1);
    }

    public static void addDeliver(Integer num) {
        deliverCount.addAndGet(num);
    }

    public static void addDeliver() {
        addDeliver(1);
    }

    public void logOutSpeed() {
        int temp = submitCount.get();
        logger.info("Submit total:【{}】,Speed:【{}/s】", temp, temp - lastSbumitCount);
        lastSbumitCount = temp;
        temp = deliverCount.get();
        logger.info("Deliver total:【{}】,Speed:【{}/s】", temp, temp - lastDeliverCount);
        lastDeliverCount = temp;
    }
}