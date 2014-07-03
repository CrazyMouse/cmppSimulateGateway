package com.crazymouse.util;

import com.google.common.base.Charsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Title ：协议相关工具类
 * Description :
 * Create Time: 14-7-3 上午11:02
 */
public class ProtocelUtil {
    /**
     * 用户鉴权数据生成
     *
     * @param user      协议中 Source_Addr
     * @param passwd    协议中 shared secret
     * @param timeStamp MMDDHHMMSS 格式时间字符串，不足十位左则补0，例如『0708101023』
     * @return
     */
    public static byte[] getAuthString(String user, String passwd, String timeStamp) {
        byte[] bytes = new byte[user.length() + 9 + passwd.length() + timeStamp.length()];
        System.arraycopy(user.getBytes(Charsets.US_ASCII), 0, bytes, 0, user.length());
        System.arraycopy(passwd.getBytes(Charsets.US_ASCII), 0, bytes, user.length() + 9, passwd.length());
        System.arraycopy(timeStamp.getBytes(Charsets.US_ASCII), 0, bytes, user.length() + 9 + passwd.length(), 10);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md.digest(bytes);
    }
    /**
         * 用户鉴权数据生成
         *
         * @param user      协议中 Source_Addr
         * @param passwd    协议中 shared secret
         * @param timeStamp 整型时间，例如『708101023』
         * @return
         */
    public static byte[] getAuthString(String user, String passwd, int timeStamp){
        String timeStr = String.valueOf(timeStamp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10 - timeStr.length(); i++) {
             sb.append("0");
        }
        sb.append(timeStr);
        return getAuthString(user,passwd,sb.toString());
    }
}
