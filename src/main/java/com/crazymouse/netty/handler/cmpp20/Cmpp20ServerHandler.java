package com.crazymouse.netty.handler.cmpp20;

//import com.lxt2.protocol.cmpp20.*;

import com.lxt2.protocol.cmpp20.*;
import com.crazymouse.netty.handler.Cmpp_Connect_Resp;
import com.crazymouse.netty.handler.util.Performance;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Cmpp20ServerHandler extends SimpleChannelHandler {

    private static String curTime = "";
    private static AtomicInteger seqid = new AtomicInteger(0);
    private static AtomicInteger count = new AtomicInteger(0);
    private boolean resp_send = true;
    private boolean rpt_send = true;
    private int reValue = 0;
    private boolean reflag = true;
    private static final Logger LOG = LoggerFactory.getLogger(Cmpp20ServerHandler.class);
    private Performance performance;

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public void setResp_send(boolean resp_send) {
        this.resp_send = resp_send;
    }

    public void setRpt_send(boolean rpt_send) {
        this.rpt_send = rpt_send;
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);
        LOG.info("***{} Connected ***",ctx.getChannel().getRemoteAddress());
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelDisconnected(ctx, e);
        LOG.info("***{} DisConnected ***",ctx.getChannel().getRemoteAddress());
    }
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        seqid.incrementAndGet();
        Cmpp_MsgHead cmppMsg = (Cmpp_MsgHead) e.getMessage();

        switch (cmppMsg.getCmdId()) {
            case Cmpp_MsgHead.SUBMIT: {
                Cmpp_Submit cs = (Cmpp_Submit) cmppMsg;
                performance.submitCount.incrementAndGet();
                byte[] msgId = getMsgId();
                if (resp_send) {
                    Cmpp_Submit_Resp csr = new Cmpp_Submit_Resp();
                    csr.setResult(reValue);
                    csr.setSeqNum(cs.getSeqNum());

                    csr.setbMsgId(msgId);
                    e.getChannel().write(csr);
                    performance.respCount.incrementAndGet();
                }
                if (rpt_send) {
                    Cmpp_Deliver cd = new Cmpp_Deliver();
                    cd.setRegistered_Delivery(1);
                    cd.setMsgByte(msgId);
                    cd.setDest_Id("0000");
                    cd.setDest_terminal_Id(cs.getDest_terminal_Id().length > 0 ? cs.getDest_terminal_Id()[0] : "");
                    cd.setService_Id("");
                    cd.setSeqNum(cs.getSeqNum());
                    cd.setLinkId("");
                    cd.setMsg_Fmt(8);
                    cd.setStat("DELIVRD");
                    e.getChannel().write(cd);
                    performance.reportCount.incrementAndGet();
                }

                break;
            }
            case Cmpp_MsgHead.SUBMITRESP: {
                Cmpp_Submit_Resp csr = (Cmpp_Submit_Resp) cmppMsg;
                break;
            }
            case Cmpp_MsgHead.DELIVER: {
                Cmpp_Deliver deliver = (Cmpp_Deliver) cmppMsg;
                break;
            }
            case Cmpp_MsgHead.DELIVERRESP: {
                Cmpp_Deliver_Resp resp = (Cmpp_Deliver_Resp) cmppMsg;
                break;
            }
            case Cmpp_MsgHead.TERMNATE: {
                break;
            }
            case Cmpp_MsgHead.CONNECT: {
                Cmpp_Connect ccr = (Cmpp_Connect) cmppMsg;
                Cmpp_Connect_Resp resp = new Cmpp_Connect_Resp();
                resp.setAuthenticatorSource(ccr.getAuthenticatorSource());
                resp.setVersion(ccr.getVersion());
                resp.setSeqNum(ccr.getSeqNum());
                resp.setStatus(0);
                e.getChannel().write(resp);
                break;
            }
            case Cmpp_MsgHead.CONNECTRESP: {
                Cmpp_Connect_Resp resp = (Cmpp_Connect_Resp) cmppMsg;
                break;
            }
            case Cmpp_MsgHead.ACTIVETEST: {
                Cmpp_Active_Test cat = (Cmpp_Active_Test) cmppMsg;
                Cmpp_Active_Test_Resp catr = new Cmpp_Active_Test_Resp(cat.getSeqNum());
                LOG.info("--Received HeartBeat From:{}",e.getChannel().getRemoteAddress());
                e.getChannel().write(catr);
                break;
            }

            case Cmpp_MsgHead.ACTIVETESTRESP: {
                Cmpp_Active_Test_Resp catr = (Cmpp_Active_Test_Resp) cmppMsg;
                break;
            }

            default: {

            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        super.exceptionCaught(ctx, e);
        LOG.error("发生异常:{}", e.getCause().getStackTrace());
    }

    public static byte[] getMsgId() {
        String currDateTimeString = new SimpleDateFormat("MMddhhmmss").format(new Date());
        String msgId = currDateTimeString;
        String strMsgId = "";
        if (currDateTimeString.equalsIgnoreCase(curTime)) {//当前时间
            seqid.getAndIncrement();
        }else { //下一秒
            curTime = currDateTimeString;
            seqid.set(0);
        }

        //月
        String s = Integer.toBinaryString(Integer.parseInt(msgId.substring(0, 2)));
        s = "0000" + s;
        strMsgId += s.substring(s.length() - 4, s.length());

        //日
        s = Integer.toBinaryString(Integer.parseInt(msgId.substring(2, 4)));
        s = "00000" + s;
        strMsgId += s.substring(s.length() - 5, s.length());

        //小时
        s = Integer.toBinaryString(Integer.parseInt(msgId.substring(4, 6)));
        s = "00000" + s;
        strMsgId += s.substring(s.length() - 5, s.length());

        //分钟
        s = Integer.toBinaryString(Integer.parseInt(msgId.substring(6, 8)));
        s = "000000" + s;
        strMsgId += s.substring(s.length() - 6, s.length());

        //秒
        s = Integer.toBinaryString(Integer.parseInt(msgId.substring(8, 10)));
        s = "000000" + s;
        strMsgId += s.substring(s.length() - 6, s.length());

        //网关id
        strMsgId += "0000000000000000000000";

        //seqid
        s = Integer.toBinaryString(seqid.get());
        s = "0000000000000000" + s;
        strMsgId += s.substring(s.length() - 16, s.length());

        byte[] b = new byte[8];
        for (int i = 0; i < strMsgId.length(); i += 8) {
            b[i / 8] = Integer.valueOf(Integer.parseInt(strMsgId.substring(i, i + 8), 2)).byteValue();
        }

        return b;
    }

    public static void main(String[] args) {
        System.err.println(getMsgId());
    }

    public void setReValue(int reValue) {
        this.reValue = reValue;
    }

    public int getReValue() {
        return reValue;
    }

    public void setReflag(boolean reflag) {
        this.reflag = reflag;
    }

    public boolean isReflag() {
        return reflag;
    }
}
