package com.crazymouse.util;

import org.junit.Assert;
import org.junit.Test;

public class ProtocelUtilTest {

    @Test
    public void testGetAuthString() throws Exception {
        String name = "abcdefg";
        String pwd = "hijks1232";
        String timeStr = "0703101233";
        int timInt = 703101233;
        byte[] b1 = ProtocelUtil.getAuthString(name,pwd,timeStr);
        byte[] b2 = ProtocelUtil.getAuthString(name,pwd,timInt);
        Assert.assertArrayEquals(b1,b2);
    }
}