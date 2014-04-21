package com.crazymouse.entity;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午2:10
 */
public abstract class CmppHead implements Serializable,Cloneable {
    protected int totalLength;
    protected int commandId;
    protected int secquenceId;

    protected int protocalType;

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public int getSecquenceId() {
        return secquenceId;
    }

    public void setSecquenceId(int secquenceId) {
        this.secquenceId = secquenceId;
    }

    /**
     * 子类字节获取，要负责父类中三属性数据生成
     *
     * @param bb
     * @return
     */
    protected abstract void doSubEncode(ByteBuffer bb);

    /**
     * 子类解码，被父类回调
     *
     * @param bb
     */
    protected abstract void doSubDecode(ByteBuffer bb);

    /**
     * 对象编码为字节数组
     *
     * @return
     */
    public byte[] doEncode() {
        processHead();
        ByteBuffer bb = ByteBuffer.allocate(totalLength);
        bb.putInt(totalLength);
        bb.putInt(commandId);
        bb.putInt(secquenceId);
        doSubEncode(bb);
        return bb.array();
    }

    protected abstract void processHead();

    /**
     * 字节数组解码为对象
     *
     * @param bytes
     * @return
     */
    public void doDecode(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        totalLength = bb.getInt();
        commandId = bb.getInt();
        secquenceId = bb.getInt();
        doSubDecode(bb);
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CmppHead cmppHead = (CmppHead) o;

        if (commandId != cmppHead.commandId) {
            return false;
        }
        if (protocalType != cmppHead.protocalType) {
            return false;
        }
        if (secquenceId != cmppHead.secquenceId) {
            return false;
        }
        if (totalLength != cmppHead.totalLength) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = totalLength;
        result = 31 * result + commandId;
        result = 31 * result + secquenceId;
        result = 31 * result + protocalType;
        return result;
    }

    protected byte[] getHead() {
        byte[] head = new byte[12];
        ByteBuffer byteBuffer = ByteBuffer.wrap(head);
        byteBuffer.putInt(totalLength);
        byteBuffer.putInt(commandId);
        byteBuffer.putInt(secquenceId);
        return byteBuffer.array();
    }

    protected void setHead(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        totalLength = byteBuffer.getInt();
        commandId = byteBuffer.getInt();
        secquenceId = byteBuffer.getInt();
    }

}
