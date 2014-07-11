package com.crazymouse.business.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;


/**
 * Title ：client Channel 管理工具，可以通过该接口批量对某类型Channel进行操作
 * Description :
 * Create Time: 14-7-11 上午10:43
 */
public interface ClientManager {
    /**
     * 注册Channel （不需要注销，断开连接channel自动注销）
     * @param channel
     */
    void registChannel(Channel channel);

    /**
     * 活动Channle数量
     * @return
     */
    Integer activeChannels();

    /**
     * 发送消息给所有匹配Channels
     * @param message
     * @param channelMatcher
     */
    void sendMessageToMatchChannels(Object message,ChannelMatcher channelMatcher);

    /**
     * 关闭匹配Channels
     * @param channelMatcher
     */
    void closeMatchChannels(ChannelMatcher channelMatcher);
}
