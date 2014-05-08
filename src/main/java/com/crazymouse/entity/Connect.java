package com.crazymouse.entity;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午2:13
 */
public class Connect extends CmppHead {
    private byte[] sourceAddr = new byte[6];
    private byte[] authenticatorSource = new byte[16];
    private byte version;
    private byte[] timeStamp = new byte[4];

    public Connect() {
        commandId = CMPPConstant.CMPP_CONNECT;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(sourceAddr);
        bb.put(authenticatorSource);
        bb.put(version);
        bb.put(timeStamp);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        bb.get(sourceAddr);
        bb.get(authenticatorSource);
        version = bb.get();
        bb.get(timeStamp);
    }

    @Override
    protected void processHead() {
        totalLength = 39;
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

        Connect connect = (Connect) o;

        if (version != connect.version) {
            return false;
        }
        if (!Arrays.equals(authenticatorSource, connect.authenticatorSource)) {
            return false;
        }
        if (!Arrays.equals(sourceAddr, connect.sourceAddr)) {
            return false;
        }
        if (!Arrays.equals(timeStamp, connect.timeStamp)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sourceAddr != null ? Arrays.hashCode(sourceAddr) : 0);
        result = 31 * result + (authenticatorSource != null ? Arrays.hashCode(authenticatorSource) : 0);
        result = 31 * result + (int) version;
        result = 31 * result + (timeStamp != null ? Arrays.hashCode(timeStamp) : 0);
        return result;
    }

    public byte[] getSourceAddr() {
        return sourceAddr;
    }

    public void setSourceAddr(byte[] sourceAddr) {
        this.sourceAddr = sourceAddr;
    }

    public byte[] getAuthenticatorSource() {
        return authenticatorSource;
    }

    public void setAuthenticatorSource(byte[] authenticatorSource) {
        this.authenticatorSource = authenticatorSource;
    }

    public byte[] getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(byte[] timeStamp) {
        this.timeStamp = timeStamp;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Connect{");
        sb.append(super.toString());
        sb.append("sourceAddr=");
        if (sourceAddr == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < sourceAddr.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(sourceAddr[i]);
            }
            sb.append(']');
        }
        sb.append(", authenticatorSource=");
        if (authenticatorSource == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < authenticatorSource.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(authenticatorSource[i]);
            }
            sb.append(']');
        }
        sb.append(", version=").append(version);
        sb.append(", timeStamp=");
        if (timeStamp == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < timeStamp.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(timeStamp[i]);
            }
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }
}
