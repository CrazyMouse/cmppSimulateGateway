package com.crazymouse.util.impl;

import com.crazymouse.util.FlowControl;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Title ：
 * Description :
 */
public class FlowControlImpl implements FlowControl {
    private int limitNum;//流量限额
    private AtomicInteger actNum;//实际计数器

    private FlowControlImpl() {
    }

    public FlowControlImpl(int limitNum) {
        this.limitNum = limitNum;
        actNum = new AtomicInteger(limitNum);
    }

    @Override
    public boolean isOverFlow() {
        return actNum.decrementAndGet() < 0;
    }

    @Override
    public boolean isOverFlow(int checkNum) {
        return actNum.addAndGet(-checkNum) < 0;
    }

    @Override
    public void resetFlow() {
        actNum.set(limitNum);
    }

    @Override
    public void changeSpeed(int speed) {
        limitNum = speed;
    }


    @Override
    public Integer getSpeed() {
        return limitNum;
    }
}
