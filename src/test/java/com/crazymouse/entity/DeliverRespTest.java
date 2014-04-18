package com.crazymouse.entity;

import org.junit.Test;

import java.util.Random;

import static junit.framework.Assert.assertEquals;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-15 下午4:50
 */
public class DeliverRespTest {
    @Test
    public void testEncodeAndDecode() throws Exception {
        DeliverResp dr2 = new DeliverResp(Constants.PROTOCALTYPE_VERSION_CMPP2);
        DeliverResp dr3 = new DeliverResp(Constants.PROTOCALTYPE_VERSION_CMPP3);
        Random random = new Random();
        random.nextBytes(dr2.getMsgId());
        random.nextBytes(dr3.getMsgId());
        dr2.setResult(2);
        dr3.setResult(1);

        DeliverResp dr21 = new DeliverResp(Constants.PROTOCALTYPE_VERSION_CMPP2);
        DeliverResp dr31 = new DeliverResp(Constants.PROTOCALTYPE_VERSION_CMPP3);
        dr21.doDecode(dr2.doEncode());
        dr31.doDecode(dr3.doEncode());

        assertEquals(dr2, dr21);
        assertEquals(dr3,dr31);

    }
}
