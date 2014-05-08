package com.crazymouse.business.handler;

import com.crazymouse.entity.CmppHead;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-17 上午11:18
 */
public class CmppEncoder extends MessageToByteEncoder<CmppHead> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CmppHead msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.getMsgBytes());
    }
}
