package com.crazymouse.util;

/**
 * Title ：流量控制接口
 * Description :
 */
public interface FlowControl {
    /**
     * 检测是否超出流量
     *
     * @return
     */
    boolean isOverFlow();

    /**
     * 检测是否超出流量,流量限额<=0时返回false，否则返回true
     *
     * @return
     */
    boolean isOverFlow(int checkNum);

    /**
     * 修改流量
     *
     * @param speed
     */
    void changeSpeed(int speed);


    /**
     * 获取当前速度
     */
    Integer getSpeed();

    /**
     * 重置流量
     */
    void resetFlow();
}
