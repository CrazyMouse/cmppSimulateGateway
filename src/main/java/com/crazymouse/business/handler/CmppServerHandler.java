package com.crazymouse.business.handler;

import com.crazymouse.entity.*;
import com.crazymouse.util.FlowControl;
import com.crazymouse.util.Statistic;
import com.google.common.base.Charsets;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.arraycopy;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-17 上午11:49
 */
@ChannelHandler.Sharable
public class CmppServerHandler extends ChannelDuplexHandler {
    Logger logger = LoggerFactory.getLogger(CmppServerHandler.class);
    private Random random = new Random();
    private DateFormat df = new SimpleDateFormat("yyMMddHHmm");
    private DateFormat msgIdHeadFormat = new SimpleDateFormat("yyyyMMdd");
    private AtomicInteger magIdTailCount = new AtomicInteger(0);
    private FlowControl flowControl;
    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void setFlowControl(FlowControl flowControl) {
        this.flowControl = flowControl;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("" + "Cinet:【{}】 closed Connection!", ctx.channel().remoteAddress());
    }

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
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                CmppHead cmppMsg = (CmppHead) msg;
                cmppMsg.doDecode();
                switch (cmppMsg.getCommandId()) {
                    case CMPPConstant.APP_SUBMIT:
                        processSubmit(ctx, (Submit) cmppMsg);
                        break;
                    case CMPPConstant.APP_ACTIVE_TEST:
                        processActiveTest(ctx, (ActiveTest) cmppMsg);
                        break;
                    case CMPPConstant.CMPP_CONNECT:
                        processConnect(ctx, (Connect) cmppMsg);
                        break;
                    case CMPPConstant.APP_DELIVER_RESP:
                        processDeliverResp((DeliverResp) cmppMsg);
                        break;
                }
            }
        });
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("Client idle time too long,close clinet:【{}】", ctx.channel().remoteAddress());
        ctx.close();
    }

    private void processDeliverResp(DeliverResp deliverResp) {
        logger.debug("Received Deliver Resp");
    }

    private void processConnect(ChannelHandlerContext ctx, Connect connect) {
        logger.debug("Received Connect Request,Version:{}", connect.getVersion());
        if (connect.getVersion() != Constants.PROTOCALTYPE_VERSION_CMPP2 &&
                connect.getVersion() != Constants.PROTOCALTYPE_VERSION_CMPP3) {
            logger.info("Unknown ProtocalVersion Close Clinet:【{}】", ctx.channel().remoteAddress());
            ctx.close();
            return;
        }
        ctx.channel().attr(Constants.PROTOCALTYPE_VERSION).set((int) connect.getVersion());
        ConnectResp connectResp = new ConnectResp((Integer) ctx.channel().attr(Constants.PROTOCALTYPE_VERSION).get());
        connectResp.setSecquenceId(connect.getSecquenceId());
        connectResp.setStatus(0);
        arraycopy(connect.getAuthenticatorSource(), 0, connectResp.getAuthenticatorIsmg(), 0, 16);
        connectResp.setVersion(connect.getVersion());
        connectResp.doEncode();
        ctx.writeAndFlush(connectResp);
    }

    private void processActiveTest(ChannelHandlerContext ctx, ActiveTest activeTest) {
        logger.debug("Received heartbeats From:【{}】", ctx.channel().remoteAddress());
        ActiveTestResp resp = new ActiveTestResp();
        resp.setReserved((byte) 0);
        resp.setSecquenceId(activeTest.getSecquenceId());
        resp.doEncode();
        ctx.writeAndFlush(resp);
    }

    private void processSubmit(ChannelHandlerContext ctx, Submit submit) {
        logger.debug("Received Submit");
        Statistic.addSubmit();
        SubmitResp resp = new SubmitResp((Integer) ctx.channel().attr(Constants.PROTOCALTYPE_VERSION).get());
        resp.setSecquenceId(submit.getSecquenceId());
        ByteBuffer.wrap(resp.getMsgId()).putInt(Integer.valueOf(msgIdHeadFormat.format(Calendar.getInstance().getTime()))).putInt(magIdTailCount.incrementAndGet());
        resp.setResult(flowControl.isOverFlow() ? 8 : 0);
        resp.doEncode();
        ctx.writeAndFlush(resp);
        if (submit.getRegisteredDelivery() == 1 && resp.getResult() == 0) {
            sendRpt(ctx, submit, resp);
        }
    }

    /**
     * 状态报告发送
     *
     * @param ctx
     * @param submit
     * @param resp
     */
    private void sendRpt(ChannelHandlerContext ctx, Submit submit, SubmitResp resp) {
        logger.debug("Send Rpt");
        Integer protocalType = (Integer) ctx.channel().attr(Constants.PROTOCALTYPE_VERSION).get();
        Deliver deliver = new Deliver((Integer) protocalType);
        ByteBuffer.wrap(deliver.getMsgId()).putInt(Integer.valueOf(msgIdHeadFormat.format(Calendar.getInstance().getTime()))).putInt(magIdTailCount.incrementAndGet());
        arraycopy(submit.getSrcId(), 0, deliver.getDestId(), 0, 21);
        arraycopy(submit.getServiceId(), 0, deliver.getServiceId(), 0, 10);
        deliver.setTpPid(submit.getTppId());
        deliver.setTpUdhi(submit.getTpUdhi());
        deliver.setMsgFmt((byte) 15);
        arraycopy(submit.getFeeTerminalId(), 0, deliver.getSrcTerminalId(), 0, submit.getFeeTerminalId().length);
        deliver.setSrcTerminalType((byte) 0);
        deliver.setRegisteredDelivery((byte) 1);
        arraycopy(resp.getMsgId(), 0, deliver.getMsg_Id(), 0, 8);
        arraycopy("DELIVRD".getBytes(Charsets.US_ASCII), 0, deliver.getStat(), 0, 7);
        arraycopy(df.format(new Date()).getBytes(Charsets.US_ASCII), 0, deliver.getSubmitTime(), 0, 10);
        arraycopy(deliver.getSubmitTime(), 0, deliver.getDoneTime(), 0, 10);
        random.nextBytes(deliver.getSmscSequence());
        int destTerminalIdLength = protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2 ? 21 : 32;
        for (int i = 0; i < submit.getDestUsrTl(); i++) {
            Statistic.addDeliver();
            if (i == 0) {
                arraycopy(submit.getDestTerminalIds(), 0, deliver.getDestTerminalId(), 0, destTerminalIdLength);
                deliver.doEncode();
                ctx.writeAndFlush(deliver);
            }else {
                Deliver delivernew = deliver.clone();//clone 防止原数据在未发出情况下被新数据覆盖
                arraycopy(submit.getDestTerminalIds(),
                        i * destTerminalIdLength, deliver.getDestTerminalId(), 0, destTerminalIdLength);
                delivernew.doEncode();
                ctx.writeAndFlush(delivernew);
            }
        }
    }
}
