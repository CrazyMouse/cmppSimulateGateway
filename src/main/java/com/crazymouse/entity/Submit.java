package com.crazymouse.entity;

import com.google.common.primitives.UnsignedBytes;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午2:17
 */
public class Submit extends CmppHead {
    private byte[] msgId = new byte[8];
    private byte pkTotal;
    private byte pkNumber;
    private byte registeredDelivery;
    private byte msgLevel;
    private byte[] serviceId = new byte[10];
    private byte feeUserType;
    private byte[] feeTerminalId;
    private byte feeTerminalType;
    private byte tppId;
    private byte tpUdhi;
    private byte msgFmt;
    private byte[] msgSrc = new byte[6];
    private byte[] feeType = new byte[2];
    private byte[] feeCode = new byte[6];
    private byte[] validTime = new byte[17];
    private byte[] atTime = new byte[17];
    private byte[] srcId = new byte[21];
    private byte destUsrTl;
    private byte[] destTerminalIds;
    private byte destTerminalType;
    private int msgLength;
    private byte[] msgContent;
    private byte[] reserveOrLinkId;

    public Submit(int protocalType) {
        super.protocalType = protocalType;
        super.commandId = CMPPConstant.APP_SUBMIT;
        feeTerminalId = new byte[protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2 ? 21 : 32];
        reserveOrLinkId = new byte[protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2 ? 8 : 20];
    }

    @Override
    protected void processHead() {
        boolean isCmpp2 = protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2;
        if (isCmpp2) {
            totalLength = 138 + 21 * destUsrTl + msgLength;
        }else {
            totalLength = 163 + 32 * destUsrTl + msgLength;
        }
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        boolean isCmpp2 = protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2;

        bb.put(msgId);
        bb.put(pkTotal);
        bb.put(pkNumber);
        bb.put(registeredDelivery);
        bb.put(msgLevel);
        bb.put(serviceId);
        bb.put(feeUserType);
        bb.put(feeTerminalId);
        if (!isCmpp2) {
            bb.put(feeTerminalType);
        }
        bb.put(tppId);
        bb.put(tpUdhi);
        bb.put(msgFmt);
        bb.put(msgSrc);
        bb.put(feeType);
        bb.put(feeCode);
        bb.put(validTime);
        bb.put(atTime);
        bb.put(srcId);
        bb.put(destUsrTl);
        bb.put(destTerminalIds);
        if (!isCmpp2) {
            bb.put(destTerminalType);
        }

        bb.put((byte) msgLength);
        bb.put(msgContent);
        bb.put(reserveOrLinkId);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        boolean isCmpp2 = protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2;
        bb.get(msgId);
        pkTotal = bb.get();
        pkNumber = bb.get();
        registeredDelivery = bb.get();
        msgLevel = bb.get();
        bb.get(serviceId);
        feeUserType = bb.get();
        bb.get(feeTerminalId);
        if (!isCmpp2) { feeTerminalType = bb.get(); }
        tppId = bb.get();
        tpUdhi = bb.get();
        msgFmt = bb.get();
        bb.get(msgSrc);
        bb.get(feeType);
        bb.get(feeCode);
        bb.get(validTime);
        bb.get(atTime);
        bb.get(srcId);
        setDestUsrTl(bb.get());
        bb.get(destTerminalIds);
        if (!isCmpp2) { destTerminalType = bb.get(); }
        msgLength = UnsignedBytes.toInt(bb.get());
        msgContent = new byte[msgLength];
        bb.get(msgContent);
        bb.get(reserveOrLinkId);
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

        Submit submit = (Submit) o;

        if (destTerminalType != submit.destTerminalType) {
            return false;
        }
        if (destUsrTl != submit.destUsrTl) {
            return false;
        }
        if (feeTerminalType != submit.feeTerminalType) {
            return false;
        }
        if (feeUserType != submit.feeUserType) {
            return false;
        }
        if (msgFmt != submit.msgFmt) {
            return false;
        }
        if (msgLength != submit.msgLength) {
            return false;
        }
        if (msgLevel != submit.msgLevel) {
            return false;
        }
        if (pkNumber != submit.pkNumber) {
            return false;
        }
        if (pkTotal != submit.pkTotal) {
            return false;
        }
        if (registeredDelivery != submit.registeredDelivery) {
            return false;
        }
        if (tpUdhi != submit.tpUdhi) {
            return false;
        }
        if (tppId != submit.tppId) {
            return false;
        }
        if (!Arrays.equals(reserveOrLinkId, submit.reserveOrLinkId)) {
            return false;
        }
        if (!Arrays.equals(atTime, submit.atTime)) {
            return false;
        }
        if (!Arrays.equals(destTerminalIds, submit.destTerminalIds)) {
            return false;
        }
        if (!Arrays.equals(feeCode, submit.feeCode)) {
            return false;
        }
        if (!Arrays.equals(feeTerminalId, submit.feeTerminalId)) {
            return false;
        }
        if (!Arrays.equals(feeType, submit.feeType)) {
            return false;
        }
        if (!Arrays.equals(msgContent, submit.msgContent)) {
            return false;
        }
        if (!Arrays.equals(msgId, submit.msgId)) {
            return false;
        }
        if (!Arrays.equals(msgSrc, submit.msgSrc)) {
            return false;
        }
        if (!Arrays.equals(serviceId, submit.serviceId)) {
            return false;
        }
        if (!Arrays.equals(srcId, submit.srcId)) {
            return false;
        }
        if (!Arrays.equals(validTime, submit.validTime)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (msgId != null ? Arrays.hashCode(msgId) : 0);
        result = 31 * result + (int) pkTotal;
        result = 31 * result + (int) pkNumber;
        result = 31 * result + (int) registeredDelivery;
        result = 31 * result + (int) msgLevel;
        result = 31 * result + (serviceId != null ? Arrays.hashCode(serviceId) : 0);
        result = 31 * result + (int) feeUserType;
        result = 31 * result + (feeTerminalId != null ? Arrays.hashCode(feeTerminalId) : 0);
        result = 31 * result + (int) feeTerminalType;
        result = 31 * result + (int) tppId;
        result = 31 * result + (int) tpUdhi;
        result = 31 * result + (int) msgFmt;
        result = 31 * result + (msgSrc != null ? Arrays.hashCode(msgSrc) : 0);
        result = 31 * result + (feeType != null ? Arrays.hashCode(feeType) : 0);
        result = 31 * result + (feeCode != null ? Arrays.hashCode(feeCode) : 0);
        result = 31 * result + (validTime != null ? Arrays.hashCode(validTime) : 0);
        result = 31 * result + (atTime != null ? Arrays.hashCode(atTime) : 0);
        result = 31 * result + (srcId != null ? Arrays.hashCode(srcId) : 0);
        result = 31 * result + (int) destUsrTl;
        result = 31 * result + (destTerminalIds != null ? Arrays.hashCode(destTerminalIds) : 0);
        result = 31 * result + (int) destTerminalType;
        result = 31 * result + msgLength;
        result = 31 * result + (msgContent != null ? Arrays.hashCode(msgContent) : 0);
        result = 31 * result + (reserveOrLinkId != null ? Arrays.hashCode(reserveOrLinkId) : 0);
        return result;
    }

    public byte[] getMsgId() {
        return msgId;
    }

    public void setMsgId(byte[] msgId) {
        this.msgId = msgId;
    }

    public byte getPkTotal() {
        return pkTotal;
    }

    public void setPkTotal(byte pkTotal) {
        this.pkTotal = pkTotal;
    }

    public byte getPkNumber() {
        return pkNumber;
    }

    public void setPkNumber(byte pkNumber) {
        this.pkNumber = pkNumber;
    }

    public byte getRegisteredDelivery() {
        return registeredDelivery;
    }

    public void setRegisteredDelivery(byte registeredDelivery) {
        this.registeredDelivery = registeredDelivery;
    }

    public byte getMsgLevel() {
        return msgLevel;
    }

    public void setMsgLevel(byte msgLevel) {
        this.msgLevel = msgLevel;
    }

    public byte[] getServiceId() {
        return serviceId;
    }

    public void setServiceId(byte[] serviceId) {
        this.serviceId = serviceId;
    }

    public byte getFeeUserType() {
        return feeUserType;
    }

    public void setFeeUserType(byte feeUserType) {
        this.feeUserType = feeUserType;
    }

    public byte[] getFeeTerminalId() {
        return feeTerminalId;
    }

    public byte getFeeTerminalType() {
        return feeTerminalType;
    }

    public void setFeeTerminalType(byte feeTerminalType) {
        this.feeTerminalType = feeTerminalType;
    }

    public byte getTppId() {
        return tppId;
    }

    public void setTppId(byte tppId) {
        this.tppId = tppId;
    }

    public byte getMsgFmt() {
        return msgFmt;
    }

    public void setMsgFmt(byte msgFmt) {
        this.msgFmt = msgFmt;
    }

    public byte[] getMsgSrc() {
        return msgSrc;
    }

    public void setMsgSrc(byte[] msgSrc) {
        this.msgSrc = msgSrc;
    }

    public byte[] getFeeType() {
        return feeType;
    }

    public void setFeeType(byte[] feeType) {
        this.feeType = feeType;
    }

    public byte[] getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(byte[] feeCode) {
        this.feeCode = feeCode;
    }

    public byte[] getValidTime() {
        return validTime;
    }

    public void setValidTime(byte[] validTime) {
        this.validTime = validTime;
    }

    public byte[] getAtTime() {
        return atTime;
    }

    public void setAtTime(byte[] atTime) {
        this.atTime = atTime;
    }

    public byte[] getSrcId() {
        return srcId;
    }

    public void setSrcId(byte[] srcId) {
        this.srcId = srcId;
    }

    public byte getDestUsrTl() {
        return destUsrTl;
    }

    public void setDestUsrTl(byte destUsrTl) {
        this.destUsrTl = destUsrTl;
        destTerminalIds = new byte[destUsrTl*feeTerminalId.length];
    }

    public byte[] getDestTerminalIds() {
        return destTerminalIds;
    }

    public byte getDestTerminalType() {
        return destTerminalType;
    }

    public void setDestTerminalType(byte destTerminalType) {
        this.destTerminalType = destTerminalType;
    }

    public int getMsgLength() {
        return msgLength;
    }

    public byte[] getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(byte[] msgContent) {
        this.msgContent = msgContent;
        msgLength = msgContent.length;
    }

    public byte[] getReserveOrLinkId() {
        return reserveOrLinkId;
    }

    public byte getTpUdhi() {
        return tpUdhi;
    }

    public void setTpUdhi(byte tpUdhi) {
        this.tpUdhi = tpUdhi;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Submit{");
        sb.append(super.toString());
        sb.append("msgId=");
        if (msgId == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < msgId.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(msgId[i]);
            }
            sb.append(']');
        }
        sb.append(", pkTotal=").append(pkTotal);
        sb.append(", pkNumber=").append(pkNumber);
        sb.append(", registeredDelivery=").append(registeredDelivery);
        sb.append(", msgLevel=").append(msgLevel);
        sb.append(", serviceId=");
        if (serviceId == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < serviceId.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(serviceId[i]);
            }
            sb.append(']');
        }
        sb.append(", feeUserType=").append(feeUserType);
        sb.append(", feeTerminalId=");
        if (feeTerminalId == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < feeTerminalId.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(feeTerminalId[i]);
            }
            sb.append(']');
        }
        sb.append(", feeTerminalType=").append(feeTerminalType);
        sb.append(", tppId=").append(tppId);
        sb.append(", tpUdhi=").append(tpUdhi);
        sb.append(", msgFmt=").append(msgFmt);
        sb.append(", msgSrc=");
        if (msgSrc == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < msgSrc.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(msgSrc[i]);
            }
            sb.append(']');
        }
        sb.append(", feeType=");
        if (feeType == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < feeType.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(feeType[i]);
            }
            sb.append(']');
        }
        sb.append(", feeCode=");
        if (feeCode == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < feeCode.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(feeCode[i]);
            }
            sb.append(']');
        }
        sb.append(", validTime=");
        if (validTime == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < validTime.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(validTime[i]);
            }
            sb.append(']');
        }
        sb.append(", atTime=");
        if (atTime == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < atTime.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(atTime[i]);
            }
            sb.append(']');
        }
        sb.append(", srcId=");
        if (srcId == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < srcId.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(srcId[i]);
            }
            sb.append(']');
        }
        sb.append(", destUsrTl=").append(destUsrTl);
        sb.append(", destTerminalIds=");
        if (destTerminalIds == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < destTerminalIds.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(destTerminalIds[i]);
            }
            sb.append(']');
        }
        sb.append(", destTerminalType=").append(destTerminalType);
        sb.append(", msgLength=").append(msgLength);
        sb.append(", msgContent=");
        if (msgContent == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < msgContent.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(msgContent[i]);
            }
            sb.append(']');
        }
        sb.append(", reserveOrLinkId=");
        if (reserveOrLinkId == null) {
            sb.append("null");
        }else {
            sb.append('[');
            for (int i = 0; i < reserveOrLinkId.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(reserveOrLinkId[i]);
            }
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }
}
