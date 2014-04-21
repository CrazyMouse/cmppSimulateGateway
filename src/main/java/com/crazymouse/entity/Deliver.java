package com.crazymouse.entity;

import com.google.common.primitives.UnsignedBytes;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static java.lang.System.arraycopy;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午2:37
 */
public class Deliver extends CmppHead {
    private byte[] msgId = new byte[8];
    private byte[] destId = new byte[21];
    private byte[] serviceId = new byte[10];
    private byte tpPid;
    private byte tpUdhi;
    private byte msgFmt;

    private byte[] srcTerminalId;
    private byte srcTerminalType;
    private byte registeredDelivery;
    private int msgLength;//由于符号位关系，这里用int替代byte
    private byte[] msgContent;

    private byte[] msg_Id = new byte[8];//状态报告中msgid与响应中对应
    private byte[] stat = new byte[7];
    private byte[] submitTime = new byte[10];
    private byte[] doneTime = new byte[10];
    private byte[] destTerminalId;
    private byte[] smscSequence = new byte[4];
    private byte[] reservedOrLinkId;

    public Deliver(int protocalType) {
        super.protocalType = protocalType;
        super.commandId = CMPPConstant.APP_DELIVER;
    }


    @Override
    protected void doSubEncode(ByteBuffer bb) {
        boolean isCmpp2 = protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2;
        bb.put(msgId);
        bb.put(destId);
        bb.put(serviceId);
        bb.put(tpPid);
        bb.put(tpUdhi);
        bb.put(msgFmt);
        bb.put(srcTerminalId);
        if (!isCmpp2) {
            bb.put(srcTerminalType);
        }
        bb.put(registeredDelivery);
        bb.put((byte) msgLength);
        if (msgContent != null) {
            bb.put(msgContent);
        }else {
            bb.put(msg_Id);
            bb.put(stat);
            bb.put(submitTime);
            bb.put(doneTime);
            bb.put(destTerminalId);
            bb.put(smscSequence);
        }
        bb.put(reservedOrLinkId);
    }

    @Override
    protected void processHead() {
        boolean isCmpp2 = protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2;
        if (null == msgContent) {
            totalLength = isCmpp2 ? 145 : 180;
        }else {
            processTotalLength(isCmpp2);
        }
    }

    private void processTotalLength(boolean isCmpp2) {
        if (isCmpp2) {
            msgLength = registeredDelivery == 1 ? 60 : msgContent.length;
            totalLength = 85 + msgLength;
        }else {
            msgLength = registeredDelivery == 1 ? 71 : msgContent.length;
            totalLength = 109 + msgLength;
        }
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        boolean isCmpp2 = protocalType == Constants.PROTOCALTYPE_VERSION_CMPP2;
        bb.get(msgId);
        bb.get(destId);
        bb.get(serviceId);
        tpPid = bb.get();
        tpUdhi = bb.get();
        msgFmt = bb.get();
        srcTerminalId = new byte[isCmpp2 ? 21 : 32];
        bb.get(srcTerminalId);
        if (!isCmpp2) {
            srcTerminalType = bb.get();
        }
        registeredDelivery = bb.get();
        msgLength = UnsignedBytes.toInt(bb.get());
        if (registeredDelivery == 1) {
            bb.get(msg_Id);
            bb.get(stat);
            bb.get(submitTime);
            bb.get(doneTime);
            destTerminalId = new byte[isCmpp2 ? 21 : 32];
            bb.get(destTerminalId);
            bb.get(smscSequence);
        }else {
            msgContent = new byte[msgLength];
            bb.get(msgContent);
        }
        reservedOrLinkId = new byte[isCmpp2 ? 8 : 20];
        bb.get(reservedOrLinkId);
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

        Deliver deliver = (Deliver) o;

        if (msgFmt != deliver.msgFmt) {
            return false;
        }
        if (msgLength != deliver.msgLength) {
            return false;
        }
        if (registeredDelivery != deliver.registeredDelivery) {
            return false;
        }
        if (srcTerminalType != deliver.srcTerminalType) {
            return false;
        }
        if (tpPid != deliver.tpPid) {
            return false;
        }
        if (tpUdhi != deliver.tpUdhi) {
            return false;
        }
        if (!Arrays.equals(destId, deliver.destId)) {
            return false;
        }
        if (!Arrays.equals(destTerminalId, deliver.destTerminalId)) {
            return false;
        }
        if (!Arrays.equals(doneTime, deliver.doneTime)) {
            return false;
        }
        if (!Arrays.equals(msgContent, deliver.msgContent)) {
            return false;
        }
        if (!Arrays.equals(msgId, deliver.msgId)) {
            return false;
        }
        if (!Arrays.equals(msg_Id, deliver.msg_Id)) {
            return false;
        }
        if (!Arrays.equals(reservedOrLinkId, deliver.reservedOrLinkId)) {
            return false;
        }
        if (!Arrays.equals(serviceId, deliver.serviceId)) {
            return false;
        }
        if (!Arrays.equals(smscSequence, deliver.smscSequence)) {
            return false;
        }
        if (!Arrays.equals(srcTerminalId, deliver.srcTerminalId)) {
            return false;
        }
        if (!Arrays.equals(stat, deliver.stat)) {
            return false;
        }
        if (!Arrays.equals(submitTime, deliver.submitTime)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (msgId != null ? Arrays.hashCode(msgId) : 0);
        result = 31 * result + (destId != null ? Arrays.hashCode(destId) : 0);
        result = 31 * result + (serviceId != null ? Arrays.hashCode(serviceId) : 0);
        result = 31 * result + (int) tpPid;
        result = 31 * result + (int) tpUdhi;
        result = 31 * result + (int) msgFmt;
        result = 31 * result + (srcTerminalId != null ? Arrays.hashCode(srcTerminalId) : 0);
        result = 31 * result + (int) srcTerminalType;
        result = 31 * result + (int) registeredDelivery;
        result = 31 * result + msgLength;
        result = 31 * result + (msgContent != null ? Arrays.hashCode(msgContent) : 0);
        result = 31 * result + (msg_Id != null ? Arrays.hashCode(msg_Id) : 0);
        result = 31 * result + (stat != null ? Arrays.hashCode(stat) : 0);
        result = 31 * result + (submitTime != null ? Arrays.hashCode(submitTime) : 0);
        result = 31 * result + (doneTime != null ? Arrays.hashCode(doneTime) : 0);
        result = 31 * result + (destTerminalId != null ? Arrays.hashCode(destTerminalId) : 0);
        result = 31 * result + (smscSequence != null ? Arrays.hashCode(smscSequence) : 0);
        result = 31 * result + (reservedOrLinkId != null ? Arrays.hashCode(reservedOrLinkId) : 0);
        return result;
    }

    @Override
    public Deliver clone()  {

        Deliver deliver = new Deliver(this.protocalType);
        deliver.totalLength = this.totalLength;
        deliver.commandId = this.commandId;
        deliver.secquenceId = this.secquenceId;
        arraycopy(this.msgId, 0, deliver.msgId, 0, 8);
        arraycopy(this.destId, 0, deliver.destId, 0, 21);
        arraycopy(this.serviceId, 0, deliver.serviceId, 0, 10);
        deliver.setTpPid(this.tpPid);
        deliver.setTpUdhi(this.tpUdhi);
        deliver.setMsgFmt(this.msgFmt);
        if (this.srcTerminalId != null) {
            deliver.srcTerminalId = new byte[this.srcTerminalId.length];
            arraycopy(this.srcTerminalId, 0, deliver.srcTerminalId, 0, this.srcTerminalId.length);
        }
        deliver.srcTerminalType = this.srcTerminalType;
        deliver.registeredDelivery = this.registeredDelivery;
        deliver.msgLength = this.msgLength;
        if (this.msgContent != null) {
            deliver.msgContent = new byte[this.msgContent.length];
            arraycopy(this.msgContent, 0, deliver.msgContent, 0, this.msgContent.length);
        }
        arraycopy(this.msg_Id, 0, deliver.msg_Id, 0, 8);
        arraycopy(this.stat, 0, deliver.stat, 0, 7);
        arraycopy(this.submitTime, 0, deliver.submitTime, 0, 10);
        arraycopy(this.doneTime, 0, deliver.doneTime, 0, 10);
        if (this.destTerminalId != null) {
            deliver.destTerminalId = new byte[this.destTerminalId.length];
            arraycopy(this.destTerminalId, 0, deliver.destTerminalId, 0, this.destTerminalId.length);
        }
        arraycopy(this.smscSequence, 0, deliver.smscSequence, 0, 4);
        if (this.reservedOrLinkId != null) {
            deliver.reservedOrLinkId = new byte[this.reservedOrLinkId.length];
            arraycopy(this.reservedOrLinkId, 0, deliver.reservedOrLinkId, 0, this.reservedOrLinkId.length);
        }
        return deliver;
    }

    public byte[] getMsgId() {
        return msgId;
    }

    public void setMsgId(byte[] msgId) {
        this.msgId = msgId;
    }

    public byte[] getDestId() {
        return destId;
    }

    public void setDestId(byte[] destId) {
        this.destId = destId;
    }

    public byte[] getServiceId() {
        return serviceId;
    }

    public void setServiceId(byte[] serviceId) {
        this.serviceId = serviceId;
    }

    public byte getTpPid() {
        return tpPid;
    }

    public void setTpPid(byte tpPid) {
        this.tpPid = tpPid;
    }

    public byte getTpUdhi() {
        return tpUdhi;
    }

    public void setTpUdhi(byte tpUdhi) {
        this.tpUdhi = tpUdhi;
    }

    public byte getMsgFmt() {
        return msgFmt;
    }

    public void setMsgFmt(byte msgFmt) {
        this.msgFmt = msgFmt;
    }

    public byte[] getSrcTerminalId() {
        return srcTerminalId;
    }

    public void setSrcTerminalId(byte[] srcTerminalId) {
        this.srcTerminalId = srcTerminalId;
    }

    public byte getSrcTerminalType() {
        return srcTerminalType;
    }

    public void setSrcTerminalType(byte srcTerminalType) {
        this.srcTerminalType = srcTerminalType;
    }

    public byte getRegisteredDelivery() {
        return registeredDelivery;
    }

    public void setRegisteredDelivery(byte registeredDelivery) {
        this.registeredDelivery = registeredDelivery;
    }

    public int getMsgLength() {
        return msgLength;
    }

    public byte[] getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(byte[] msgContent) {
        this.msgContent = msgContent;
    }

    public byte[] getMsg_Id() {
        return msg_Id;
    }

    public void setMsg_Id(byte[] msg_Id) {
        this.msg_Id = msg_Id;
    }

    public byte[] getStat() {
        return stat;
    }

    public void setStat(byte[] stat) {
        this.stat = stat;
    }

    public byte[] getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(byte[] submitTime) {
        this.submitTime = submitTime;
    }

    public byte[] getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(byte[] doneTime) {
        this.doneTime = doneTime;
    }

    public byte[] getDestTerminalId() {
        return destTerminalId;
    }

    public void setDestTerminalId(byte[] destTerminalId) {
        this.destTerminalId = destTerminalId;
    }

    public byte[] getSmscSequence() {
        return smscSequence;
    }

    public void setSmscSequence(byte[] smscSequence) {
        this.smscSequence = smscSequence;
    }

    public byte[] getReservedOrLinkId() {
        return reservedOrLinkId;
    }

    public void setReservedOrLinkId(byte[] reservedOrLinkId) {
        this.reservedOrLinkId = reservedOrLinkId;
    }
}
