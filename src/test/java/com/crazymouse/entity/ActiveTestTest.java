package com.crazymouse.entity;

import org.junit.Assert;
import org.junit.Test;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午4:34
 */
public class ActiveTestTest {
    @Test
    public void testEncodeAndDecode() throws Exception {
        ActiveTest activeTest = new ActiveTest();
        byte[] bytes = activeTest.doEncode();
        ActiveTest activeTest1 = new ActiveTest();
        activeTest1.doDecode(bytes);
        Assert.assertEquals(activeTest,activeTest1);
    }
}
