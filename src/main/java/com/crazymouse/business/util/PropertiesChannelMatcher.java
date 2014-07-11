package com.crazymouse.business.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;
import io.netty.util.AttributeKey;

/**
 * Title ：Channel属性Matcher
 * Description :
 * Create Time: 14-7-11 上午10:38
 */
public class PropertiesChannelMatcher<T> implements ChannelMatcher{
    private AttributeKey key;
    private T value;

    public PropertiesChannelMatcher(AttributeKey key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean matches(Channel channel) {
        return value.equals(channel.attr(key).get());
    }
}
