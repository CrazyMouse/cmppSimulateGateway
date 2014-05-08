package com.crazymouse.entity;

import java.nio.ByteBuffer;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午2:48
 */
public class ActiveTestResp extends CmppHead {
    private byte reserved;

    public ActiveTestResp() {

        commandId = CMPPConstant.APP_ACTIVE_TEST_RESP;
    }

    public byte getReserved() {
        return reserved;
    }

    public void setReserved(byte reserved) {
        this.reserved = reserved;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(reserved);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        reserved = bb.get();
    }

    @Override
    protected void processHead() {
        totalLength = 13;
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

        ActiveTestResp that = (ActiveTestResp) o;

        return reserved == that.reserved;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) reserved;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ActiveTestResp{");
        sb.append(super.toString());
        sb.append("reserved=").append(reserved);
        sb.append('}');
        return sb.toString();
    }
}
