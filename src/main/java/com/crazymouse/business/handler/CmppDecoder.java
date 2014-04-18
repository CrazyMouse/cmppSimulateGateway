package com.crazymouse.business.handler;

import com.crazymouse.entity.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-17 上午11:25
 */
public class CmppDecoder extends ReplayingDecoder {
    Logger logger = LoggerFactory.getLogger(CmppDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int totalLength = in.readInt();
        int commandId = in.readInt();
        if (!validateClient(ctx, commandId)) {
            logger.info("Clinet:【{}】 not Login,Closed!", ctx.channel().remoteAddress());//todo multi log bug
            ctx.close();
            return;
        }
        byte[] bytes = new byte[totalLength];
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.putInt(totalLength);
        bb.putInt(commandId);
        bb.putInt(in.readInt());
        logger.debug("totalLength:{}", totalLength);
        in.readBytes(bytes,12,totalLength-12);
        CmppHead head = null;
        switch (commandId) {
            case CMPPConstant.APP_SUBMIT:
                head = new Submit((Integer) ctx.channel().attr(Constants.PROTOCALTYPE_VERSION).get());
                break;
            case CMPPConstant.APP_ACTIVE_TEST:
                head = new ActiveTest();
                break;
            case CMPPConstant.CMPP_CONNECT:
                head = new Connect();
                break;
            case CMPPConstant.APP_DELIVER_RESP:
                head = new DeliverResp((Integer) ctx.channel().attr(Constants.PROTOCALTYPE_VERSION).get());
                break;
            default:
                logger.warn("Received unknown data，commandId：{},Connection Closed!",
                        "0x" + Integer.toHexString(commandId));
                ctx.close();
        }
        if (null != head) {
            head.doDecode(bytes);
            out.add(head);
        }
    }

    /**
     * check client is login
     *
     * @param ctx
     * @param commandId
     * @return
     */
    private boolean validateClient(ChannelHandlerContext ctx, int commandId) {
        return null != ctx.channel().attr(Constants.PROTOCALTYPE_VERSION).get() ||
                commandId == CMPPConstant.CMPP_CONNECT;
    }
}
