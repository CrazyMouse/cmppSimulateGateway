package com.crazymouse.business.impl;

import com.crazymouse.business.ClientAuthentication;
import com.crazymouse.util.ConfigUtil;
import com.crazymouse.util.ProtocelUtil;
import com.google.common.primitives.Ints;

import java.util.Arrays;

/**
 * Title ：
 * Description :
 * Create Time: 14-7-3 下午2:14
 */
public class DefaultClientAuthentication implements ClientAuthentication {
    private ConfigUtil configUtil;

    public void setConfigUtil(ConfigUtil configUtil) {
        this.configUtil = configUtil;
    }

    @Override
    public boolean authenticateClient(byte[] authenticatorSource, byte[] timeStamp, String remoteAddr) {
        if (!Boolean.valueOf(configUtil.getConfig("needValidate"))) return true;
        String user = configUtil.getConfig("user");
        String password = configUtil.getConfig("password");
        return Arrays.equals(ProtocelUtil.getAuthString(user, password, Ints.fromByteArray(timeStamp)), authenticatorSource);
    }
}
