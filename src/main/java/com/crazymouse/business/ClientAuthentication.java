package com.crazymouse.business;

/**
 * Title ：客户端鉴权接口
 * Description :
 * Create Time: 14-7-3 下午2:14
 */
public interface ClientAuthentication {
    /**
     * 客户端鉴权
     * @param authenticatorSource connect请求加密数据
     * @param timeStamp
     * @param remoteAddr 客户端地址，便于扩展IP鉴权
     * @return
     */
    boolean authenticateClient(byte[] authenticatorSource, byte[] timeStamp, String remoteAddr);
}
