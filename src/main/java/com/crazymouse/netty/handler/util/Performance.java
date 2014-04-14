package com.crazymouse.netty.handler.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Title ：计数器
 * Description :
 * Create Time: 12-5-2 上午10:32
 *
 * @version 1.0
 * @author: tanyang
 */


public class Performance {
    //计数器名称
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public  AtomicLong submitCount = new AtomicLong(0L);
    public  AtomicLong respCount = new AtomicLong(0L);
    public  AtomicLong reportCount = new AtomicLong(0L);


}
