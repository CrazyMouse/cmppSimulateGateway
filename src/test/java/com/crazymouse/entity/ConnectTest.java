package com.crazymouse.entity;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * Title ：
 * Description :
 * Create Time: 14-4-15 下午4:47
 */
public class ConnectTest {
    @Test
    public void testEncodeAndDecode() throws Exception {
        Random random = new Random();
        Connect c1 = new Connect();
        random.nextBytes(c1.getSourceAddr());
        random.nextBytes(c1.getAuthenticatorSource());
        c1.setVersion((byte) 1);
        random.nextBytes(c1.getTimeStamp());
        Connect c2 =  new Connect();
        byte[] bytes = c1.doEncode();
        c2.doDecode(bytes);
        assertEquals(c1,c2);
        c2.setVersion((byte) 2);
        assertFalse(c1.equals(c2));
    }
}
