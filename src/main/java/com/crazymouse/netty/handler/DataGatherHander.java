package com.crazymouse.netty.handler;

import com.crazymouse.netty.handler.util.DataGather;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.channel.MessageEvent;

/**
 * Title ：
 * Description :
 * Create Time: 12-5-31 上午11:56
 *
 * @version 1.0
 * @author: tanyang
 */


public class DataGatherHander implements ChannelUpstreamHandler {
    DataGather dataGather;

    public void setDataGather(DataGather dataGather) {
        this.dataGather = dataGather;
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        ChannelEvent ex = e;
        if(ex instanceof MessageEvent){
            MessageEvent me = (MessageEvent) ex;
            dataGather.incrementDateCount( me.getMessage().getClass().getName());
        }

    }
}
