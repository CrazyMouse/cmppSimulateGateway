package com.crazymouse.netty.handler.util;

/**
 * Title ：数据计数器
 * Description :
 * Create Time: 12-5-31 下午12:00
 *
 * @version 1.0
 * @author: tanyang
 */
public interface DataGather {
    /**
     * 数据计数器自增
     * @param name 数据名称
     */
    void  incrementDateCount(String name);

    /**
     * 数据计数器增加计数
     * @param name 数据名称
     * @param num 增加数量
     */
    void  incrementDateCount(String name,int num);

    /**
     * 开始数据统计
     */
    void start();

    /**
     * 结束数据统计
     */
    void stop();
}
