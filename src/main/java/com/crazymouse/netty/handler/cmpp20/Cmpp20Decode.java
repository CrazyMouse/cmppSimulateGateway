package com.crazymouse.netty.handler.cmpp20;

import com.crazymouse.netty.handler.Cmpp_Connect_Resp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title ：cmpp20解码工具
 * Description :
 * Create Time: 12-5-20 下午4:16
 *
 * @version 1.0
 * @author: tanyang
 */


public class Cmpp20Decode extends ReplayingDecoder {
    private static final Logger LOG = LoggerFactory.getLogger(Cmpp20Decode.class);
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
                LOG.debug("submit");
                break;
            }
            case Cmpp_MsgHead.SUBMITRESP:{
                Cmpp_Submit_Resp csr = new Cmpp_Submit_Resp();
                csr.readPackage(channelBuffer.toByteBuffer());
                returnObj = csr;
                LOG.debug("submitresp");
                break;
            }
            case Cmpp_MsgHead.DELIVER:{
                Cmpp_Deliver cd = new Cmpp_Deliver();
                cd.readPackage(channelBuffer.toByteBuffer());
                returnObj = cd;
                LOG.debug("deliver");
                break;
            }
            case Cmpp_MsgHead.DELIVERRESP:{
                Cmpp_Deliver_Resp resp = new Cmpp_Deliver_Resp();
                resp.readPackage(channelBuffer.toByteBuffer());
                returnObj = resp;
                LOG.debug("deliver Resp");
                break;
            }
            case Cmpp_MsgHead.CONNECT:{
                Cmpp_Connect ccr = new Cmpp_Connect();
                ccr.readPackage(channelBuffer.toByteBuffer());
                returnObj = ccr;
                LOG.debug("connect");
                break;
            }
            case Cmpp_MsgHead.CONNECTRESP:{
                Cmpp_Connect_Resp ccr = new Cmpp_Connect_Resp();
                ccr.readPackage(channelBuffer.toByteBuffer());
                returnObj = ccr;
                LOG.debug("connect resp");
                break;
            }
            case Cmpp_MsgHead.ACTIVETEST:{
                Cmpp_Active_Test cat = new Cmpp_Active_Test();
                cat.readPackage(channelBuffer.toByteBuffer());
                returnObj= cat;
                LOG.debug("activetest");
                break;
            }
            case Cmpp_MsgHead.ACTIVETESTRESP:{
                Cmpp_Active_Test_Resp catr = new Cmpp_Active_Test_Resp();
                catr.readPackage(channelBuffer.toByteBuffer());
                returnObj = catr;
                LOG.debug("activetest resp");
                break;
            }
            default:{
            LOG.warn("收到未知类型数据，类型编码:{}",cmd);
            }
        }
        if(null==returnObj){
            LOG.debug("obj is null!");
        }
        return returnObj;
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        LOG.warn("通道已关闭！");
    }

}
