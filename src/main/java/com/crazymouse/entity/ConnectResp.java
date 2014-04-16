package com.crazymouse.entity;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午2:15
 */
public class ConnectResp extends CmppHead {
    private int status;
    private byte[] authenticatorIsmg = new byte[16];
    private byte version;


    public ConnectResp(int protocalType) {
        super.protocalType = protocalType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getAuthenticatorIsmg() {
        return authenticatorIsmg;
    }

    public void setAuthenticatorIsmg(byte[] authenticatorIsmg) {
        this.authenticatorIsmg = authenticatorIsmg;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {

        if (protocalType == Constants.PROTOCALTYPE_CMPP2) {
            bb.put((byte) status);
        }else {
            bb.putInt(status);
        }
        bb.put(authenticatorIsmg);
        bb.put(version);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {

        status = totalLength == 30 ? bb.get() : bb.getInt();
        bb.get(authenticatorIsmg);
        version = bb.get();
    }

    @Override
    protected void processHead() {
        totalLength = protocalType == Constants.PROTOCALTYPE_CMPP2 ? 30 : 33;
        commandId = CMPPConstant.CMPP_CONNECT_RESP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ConnectResp that = (ConnectResp) o;

        if (status != that.status) {
            return false;
        }
        if (version != that.version) {
            return false;
        }
        if (!Arrays.equals(authenticatorIsmg, that.authenticatorIsmg)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + status;
        result = 31 * result + (authenticatorIsmg != null ? Arrays.hashCode(authenticatorIsmg) : 0);
        result = 31 * result + (int) version;
        return result;
    }
}
