package com.crazymouse.business.handler;

import com.crazymouse.entity.*;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-17 上午11:49
 */
@ChannelHandler.Sharable
public class CmppServerHandler extends ChannelDuplexHandler {
    Logger logger = LoggerFactory.getLogger(CmppServerHandler.class);


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Handler 异常!,异常信息:{},连接关闭!", cause);
        ctx.close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Received Connection From:【{}】", ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CmppHead cmppMsg = (CmppHead) msg;
        switch (cmppMsg.getCommandId()) {
            case CMPPConstant.APP_SUBMIT:
                processSubmit(ctx,(Submit) cmppMsg);
                break;
            case CMPPConstant.APP_ACTIVE_TEST:
                processActiveTest(ctx,(ActiveTest) cmppMsg);
                break;
            case CMPPConstant.CMPP_CONNECT:
                processConnect(ctx, (Connect) cmppMsg);
                break;
            case CMPPConstant.APP_DELIVER_RESP:
                processDeliverResp((DeliverResp) cmppMsg);
                break;
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("Client idle time too long,close clinet:【{}】",ctx.channel().remoteAddress());
        ctx.close();
    }

    private void processDeliverResp(DeliverResp deliverResp) {
        logger.debug("Received Deliver Resp");
    }

    private void processConnect(ChannelHandlerContext ctx, Connect connect) {
        logger.debug("Received Connect Request,Version:{}", connect.getVersion());
        if(connect.getVersion()!=Constants.PROTOCALTYPE_VERSION_CMPP2&&connect.getVersion()!= Constants.PROTOCALTYPE_VERSION_CMPP3){
            logger.info("Unknown ProtocalVersion Close Clinet:【{}】",ctx.channel().remoteAddress());
            ctx.close();
            return;
        }
        ctx.channel().attr(Constants.PROTOCALTYPE_VERSION).set((int) connect.getVersion());
        ConnectResp  connectResp = new ConnectResp((Integer) ctx.channel().attr(Constants.PROTOCALTYPE_VERSION).get());
        connectResp.setSecquenceId(connect.getSecquenceId());
        connectResp.setStatus(0);
        System.arraycopy(connect.getAuthenticatorSource(),0,connectResp.getAuthenticatorIsmg(),0,16);
        connectResp.setVersion(connect.getVersion());
        ctx.writeAndFlush(connectResp);
//        ctx.write(connectResp);

    }

    private void processActiveTest(ChannelHandlerContext ctx, ActiveTest activeTest) {
        logger.info("Received heartbeat From:【{}】",ctx.channel().remoteAddress());
        ActiveTestResp resp= new ActiveTestResp();
        resp.setReserved((byte) 0);
        resp.setSecquenceId(activeTest.getSecquenceId());
        ctx.write(resp);
    }

    private void processSubmit(ChannelHandlerContext ctx, Submit submit) {
        logger.debug("Received Submit");
        SubmitResp resp = new SubmitResp((Integer) ctx.channel().attr(Constants.PROTOCALTYPE_VERSION).get());
        resp.setSecquenceId(submit.getSecquenceId());
        resp.setResult(0);//todo errcode?
        ctx.writeAndFlush(resp);


    }
}
