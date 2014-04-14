package com.crazymouse.netty.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title ：
 * Description :
 * Create Time: 12-5-21 上午9:06
 *
 * @version 1.0
 * @author: tanyang
 */


public class CmppIdleHandler extends IdleStateAwareChannelHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CmppIdleHandler.class);
    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
        if(e.getState()== IdleState.ALL_IDLE){
            e.getChannel().close();
            LOG.warn("长时间未收到数据请求，连接关闭：{}", e.getChannel());
        }

    }


}
