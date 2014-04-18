package com.crazymouse.entity;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-15 下午4:50
 */
public class SubmitRespTest {
    @Test
        public void testEncodeAndDecode() throws Exception {
        Random r = new Random();
        SubmitResp sr2 = new SubmitResp(Constants.PROTOCALTYPE_VERSION_CMPP2);
        SubmitResp sr3 = new SubmitResp(Constants.PROTOCALTYPE_VERSION_CMPP3);
        r.nextBytes(sr2.getMsgId());
        sr2.setResult(2);
        r.nextBytes(sr3.getMsgId());
        sr3.setResult(3);
        SubmitResp sr21 = new SubmitResp(Constants.PROTOCALTYPE_VERSION_CMPP2);
        SubmitResp sr31 = new SubmitResp(Constants.PROTOCALTYPE_VERSION_CMPP3);
        sr21.doDecode(sr2.doEncode());
        sr31.doDecode(sr3.doEncode());

        assertEquals(sr2, sr21);
        assertEquals(sr3, sr31);



        }
}
