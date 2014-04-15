package com.crazymouse.entity;

import java.nio.ByteBuffer;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午2:47
 */
public class ActiveTest extends CmppHead {
    public ActiveTest() {
        totalLength = 12;
        commandId = CMPPConstant.APP_ACTIVE_TEST;
    }

    @Override
    protected byte[] doSubEncode() {

        return new byte[0];
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {

    }
}
