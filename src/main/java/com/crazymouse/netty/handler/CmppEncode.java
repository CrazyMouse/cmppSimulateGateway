package com.crazymouse.netty.handler;

import com.lxt2.protocol.IDataPackage;
import com.lxt2.protocol.cmpp20.Cmpp_MsgHead;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import java.nio.ByteBuffer;

/**
 * Title ：cmpp协议编码工具类，20 、30通用
 * Description :
 * Create Time: 12-5-20 下午4:31
 *
 * @version 1.0
 * @author: tanyang
 */


public class CmppEncode extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        IDataPackage msgHead = (IDataPackage) msg;
        ByteBuffer byteBuffer = ByteBuffer.allocate(msgHead.getPackageLength());
        ((Cmpp_MsgHead) msg).writePackage(byteBuffer);
        byteBuffer.flip();
        return ChannelBuffers.copiedBuffer(byteBuffer);
    }
}
