package com.crazymouse.netty.handler.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Title ：数据统计默认实现
 * Description :
 * Create Time: 12-5-31 下午1:42
 *
 * @version 1.0
 * @author: tanyang
 */


public class DefaultDataGather implements DataGather {
    private final Map<String, AtomicLong> dataCollections = new ConcurrentHashMap<String, AtomicLong>();
    private final Map<String, Long> speedCollections = new HashMap<String, Long>();
    private DataGatherTheread theread = new DataGatherTheread();

    @Override
    public synchronized void incrementDateCount(String name) {
        AtomicLong value = getCountValue(name);
        value.incrementAndGet();
    }

    /**
     * 获取指定类型的数据，如果不存在，初始化设置
     *
     * @param name
     * @return
     */
    private synchronized AtomicLong getCountValue(String name) {
        AtomicLong valueObj = dataCollections.get(name);
        if (null == valueObj) {
            valueObj = new AtomicLong();
            dataCollections.put(name, valueObj);
        }
        return valueObj;
    }

    @Override
    public synchronized void incrementDateCount(String name, int num) {
        AtomicLong value = getCountValue(name);
        value.addAndGet(num);
    }

    @Override
    public void start() {
        theread.start();
    }

    @Override
    public void stop() {
        theread.isRunning = false;
    }

    private class DataGatherTheread extends Thread {
        public boolean isRunning = true;

        @Override
        public void run() {
            Map<String, Long> temp = new HashMap<String, Long>();
            while (isRunning) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Map.Entry<String, AtomicLong> entry : dataCollections.entrySet()) {
                    String key = entry.getKey();
                    long value = entry.getValue().longValue();
                    long speed = temp.get(key) == null ? value : (value - temp.get(key));
                    temp.put(key,value);
                    speedCollections.put(key, speed);
                }
            }
        }
    }
}
