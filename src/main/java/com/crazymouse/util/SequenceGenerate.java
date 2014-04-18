package com.crazymouse.util;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerate {
    private static final AtomicInteger sequenceId = new AtomicInteger((int) (System.currentTimeMillis() % 10000000 *
            100));

    /**
     * 生成短信唯一标识
     *
     * @return
     */
    public synchronized static int getSequenceId() {
        if (sequenceId.get() == Integer.MAX_VALUE) {
            sequenceId.set(1);
        }else {
            sequenceId.incrementAndGet(); //sequenceId.addAndGet(1)出错
        }
        return sequenceId.get();
    }
}


