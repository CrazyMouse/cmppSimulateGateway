package com.crazymouse.entity;

import org.junit.Assert;
import org.junit.Test;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午4:53
 */
public class ActiveTestRespTest {
    @Test
    public void testEncodeAndDecode() throws Exception {
        ActiveTestResp resp = new ActiveTestResp();
        resp.setReserved((byte) 1);
        ActiveTestResp resp1 = new ActiveTestResp();
        resp1.doDecode(resp.doEncode());
        Assert.assertEquals(resp,resp1);
    }
}
