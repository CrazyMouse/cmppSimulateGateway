package com.crazymouse.entity;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午2:46
 */
public class DeliverResp extends CmppHead {
    private byte[] msgId = new byte[8];
    private int result;

    public DeliverResp(int protocalType) {
        super.protocalType = protocalType;
    }


    @Override
    protected void doSubEncode(ByteBuffer bb) {
        boolean isCmpp2 = protocalType == Constants.PROTOCALTYPE_CMPP2;
        bb.put(msgId);
        if (isCmpp2) {
            bb.put((byte) result);
        }else {
            bb.putInt(result);
        }
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        bb.get(msgId);
        if (totalLength == 21) {
            result = bb.get();
        }else {
            result = bb.getInt();
        }
    }

    @Override
    protected void processHead() {
        boolean isCmpp2 = protocalType == Constants.PROTOCALTYPE_CMPP2;
        totalLength = isCmpp2 ? 21 : 24;
        commandId = CMPPConstant.APP_DELIVER_RESP;
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

        DeliverResp that = (DeliverResp) o;

        if (result != that.result) {
            return false;
        }
        if (!Arrays.equals(msgId, that.msgId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (msgId != null ? Arrays.hashCode(msgId) : 0);
        result = 31 * result + this.result;
        return result;
    }

    public byte[] getMsgId() {
        return msgId;
    }

    public void setMsgId(byte[] msgId) {
        this.msgId = msgId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
