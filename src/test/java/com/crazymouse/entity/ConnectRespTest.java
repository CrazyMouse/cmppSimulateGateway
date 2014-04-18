package com.crazymouse.entity;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-15 下午4:50
 */
public class ConnectRespTest {
    @Test
        public void testEncodeAndDecode() throws Exception {
        Random random = new Random();
            ConnectResp cr2 = new ConnectResp(Constants.PROTOCALTYPE_VERSION_CMPP2);
            ConnectResp cr3 = new ConnectResp(Constants.PROTOCALTYPE_VERSION_CMPP3);
        cr2.setStatus(2);
        random.nextBytes(cr2.getAuthenticatorIsmg());
        cr2.setVersion((byte) 1);
        byte[] cr2Bytes = cr2.doEncode();
        ConnectResp cr2n = new ConnectResp(Constants.PROTOCALTYPE_VERSION_CMPP2);
        cr2n.doDecode(cr2Bytes);
        assertEquals(cr2, cr2n);

        cr3.setStatus(1);
        random.nextBytes(cr3.getAuthenticatorIsmg());
        cr3.setVersion((byte) 2);

        byte[] cr3Bytes = cr3.doEncode();
        ConnectResp cr3n = new ConnectResp(Constants.PROTOCALTYPE_VERSION_CMPP3);
        cr3n.doDecode(cr3Bytes);
        assertEquals(cr3, cr3n);
        cr3n.protocalType = Constants.PROTOCALTYPE_VERSION_CMPP2;
        assertFalse(cr3.equals(cr3n));


        }
}
