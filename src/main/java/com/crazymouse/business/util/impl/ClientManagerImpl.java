package com.crazymouse.business.util.impl;

import com.crazymouse.business.util.ClientManager;
import com.crazymouse.business.util.PropertiesChannelMatcher;
import com.crazymouse.entity.Constants;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Title ：
 * Description :
 * Create Time: 14-7-11 上午10:46
 */
public class ClientManagerImpl implements ClientManager{
    Logger logger = LoggerFactory.getLogger(ClientManagerImpl.class);
    ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    public void registChannel(Channel channel) {
        channels.add(channel);
        logger.debug("groub size:{}",channels.size());
        closeMatchChannels(new PropertiesChannelMatcher<Integer>(Constants.PROTOCALTYPE_VERSION,
                Constants.PROTOCALTYPE_VERSION_CMPP2));
    }

    @Override
    public Integer activeChannels() {
        return channels.size();
    }

    @Override
    public void sendMessageToMatchChannels(Object message, ChannelMatcher channelMatcher) {
        channels.flushAndWrite(message,channelMatcher);
    }

    @Override
    public void closeMatchChannels(ChannelMatcher channelMatcher){
        channels.close(channelMatcher);
    }
}
