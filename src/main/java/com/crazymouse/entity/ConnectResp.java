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
        super.commandId = CMPPConstant.CMPP_CONNECT_RESP;
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

        if (protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2) {
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
        totalLength = protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2 ? 30 : 33;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConnectResp{");
        sb.append(super.toString());
        sb.append("status=").append(status);
        sb.append(", authenticatorIsmg=");
        if (authenticatorIsmg == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < authenticatorIsmg.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(authenticatorIsmg[i]);
            }
            sb.append(']');
        }
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
