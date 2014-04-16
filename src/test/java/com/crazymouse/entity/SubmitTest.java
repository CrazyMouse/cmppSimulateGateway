package com.crazymouse.entity;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-15 下午4:50
 */
public class SubmitTest {
    Random r = new Random();

    @Test
    public void testSbumit2() throws Exception {
        Submit s = new Submit(Constants.PROTOCALTYPE_CMPP2);

        processCommon(s);
        s.setFeeTerminalId(new byte[21]);
        r.nextBytes(s.getFeeTerminalId());

        s.setDestTerminalIds(new byte[21 * s.getDestUsrTl()]);
        r.nextBytes(s.getDestTerminalIds());
        s.setReserveOrLinkId(new byte[8]);
        r.nextBytes(s.getReserveOrLinkId());
        Submit s1 = new Submit(Constants.PROTOCALTYPE_CMPP2);
        s1.doDecode(s.doEncode());
        Assert.assertEquals(s, s1);
    }

    private void processCommon(Submit s) {
        r.nextBytes(s.getMsgId());
        s.setPkTotal((byte) 1);
        s.setPkNumber((byte) 1);
        s.setRegisteredDelivery((byte) 1);
        s.setMsgLevel((byte) 1);
        r.nextBytes(s.getServiceId());
        s.setFeeUserType((byte) 1);
        s.setTppId((byte) 1);
        s.setMsgFmt((byte) 2);
        r.nextBytes(s.getMsgSrc());
        r.nextBytes(s.getFeeType());
        r.nextBytes(s.getFeeCode());
        r.nextBytes(s.getValidTime());
        r.nextBytes(s.getAtTime());
        r.nextBytes(s.getSrcId());
        s.setDestUsrTl((byte) 2);
        s.setMsgContent(new byte[120]);
        r.nextBytes(s.getMsgContent());
    }

    @Test
    public void testSubmit3() throws Exception {
        Submit s = new Submit(Constants.PROTOCALTYPE_CMPP3);

        processCommon(s);
        s.setFeeTerminalId(new byte[32]);
        r.nextBytes(s.getFeeTerminalId());
        s.setFeeTerminalType((byte) 1);
        s.setDestTerminalType((byte) 2);
        s.setDestTerminalIds(new byte[32 * s.getDestUsrTl()]);
        r.nextBytes(s.getDestTerminalIds());
        s.setReserveOrLinkId(new byte[20]);
        r.nextBytes(s.getReserveOrLinkId());
        Submit s1 = new Submit(Constants.PROTOCALTYPE_CMPP3);
        s1.doDecode(s.doEncode());
        Assert.assertEquals(s, s1);
    }
}
