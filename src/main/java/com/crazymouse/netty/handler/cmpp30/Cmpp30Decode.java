package com.crazymouse.netty.handler.cmpp30;

import com.lxt2.protocol.cmpp30.*;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title ：cmpp30解码工具
 * Description :
 * Create Time: 12-5-20 下午4:16
 *
 * @version 1.0
 * @author: tanyang
 */


public class Cmpp30Decode extends ReplayingDecoder {

    private static final Logger LOG = LoggerFactory.getLogger(Cmpp30Decode.class);

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, Enum state) throws Exception {
//        System.out.println("decode");
        int cmd = buffer.getInt(buffer.readerIndex()+4);
        Object returnObj = null;
        ChannelBuffer channelBuffer =  buffer.readBytes(buffer.getInt(buffer.readerIndex()));
        switch (cmd){
            case Cmpp_MsgHead.SUBMIT:{
                Cmpp_Submit cs = new Cmpp_Submit();
                cs.readPackage(channelBuffer.toByteBuffer());
                returnObj = cs;
                break;
            }
            case Cmpp_MsgHead.SUBMITRESP:{
                Cmpp_Submit_Resp csr = new Cmpp_Submit_Resp();
                csr.readPackage(channelBuffer.toByteBuffer());
                returnObj = csr;
                break;
            }
            case Cmpp_MsgHead.DELIVER:{
                Cmpp_Deliver cd = new Cmpp_Deliver();
                cd.readPackage(channelBuffer.toByteBuffer());
                returnObj = cd;
                break;
            }
            case Cmpp_MsgHead.DELIVERRESP:{
                Cmpp_Deliver_Resp resp = new Cmpp_Deliver_Resp();
                resp.readPackage(channelBuffer.toByteBuffer());
                returnObj = resp;
                break;
            }
            case Cmpp_MsgHead.CONNECT:{
                Cmpp_Connect ccr = new Cmpp_Connect();
                ccr.readPackage(channelBuffer.toByteBuffer());
                returnObj = ccr;
                break;
            }
            case Cmpp_MsgHead.CONNECTRESP:{
                Cmpp_Connect_Resp ccr = new Cmpp_Connect_Resp();
                ccr.readPackage(channelBuffer.toByteBuffer());
                returnObj = ccr;
                break;
            }
            case Cmpp_MsgHead.ACTIVETEST:{
                Cmpp_Active_Test cat = new Cmpp_Active_Test();
                cat.readPackage(channelBuffer.toByteBuffer());
                returnObj= cat;
                break;
            }
            case Cmpp_MsgHead.ACTIVETESTRESP:{
                Cmpp_Active_Test_Resp catr = new Cmpp_Active_Test_Resp();
                catr.readPackage(channelBuffer.toByteBuffer());
                returnObj = catr;
                break;
            }
            default:{
                LOG.warn("收到未知类型数据，类型编码:{}",cmd);
            }
        }
        return returnObj;
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        LOG.warn("通道已关闭！");
    }

}
