package com.crazymouse.netty.handler;

import com.lxt2.protocol.cmpp20.Cmpp_MsgHead;

import java.nio.ByteBuffer;

public class Cmpp_Connect_Resp extends Cmpp_MsgHead {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1805173622659921308L;
	private int status;
	private String AuthenticatorSource;
	private int Version;

	private static int PackageLength = 33;
	public static final int SuccessStatus = 0;

	public int getPackageLength() {
		return PackageLength;
	}

	public Cmpp_Connect_Resp() {
		super(PackageLength, CONNECTRESP);
	}

	public int readPackage(ByteBuffer inBuf) throws Exception {
		super.readPackage(inBuf);
		this.setStatus(inBuf.getInt());
		byte[] aut = new byte[16];
		inBuf.get(aut);
		// this.setAuthenticatorSource(inBuf.getString(16,Message.charset.newDecoder()));
		int s = 0;
		s |= 0x00FF & inBuf.get();
		this.setVersion(s);
		return status;
	}

	public int writePackage(ByteBuffer outBuf) throws Exception {
		super.writePackage(outBuf);
		outBuf.putInt(this.status);
		str2bytebuffer(outBuf, this.AuthenticatorSource, 16);
		byte b = 0;
		outBuf.put(b);
		
		return 0;
	}

	public String toString() {
		StringBuffer sf = new StringBuffer();
		sf.append(super.toString());
		sf.append("[Status = ").append(status).append("]<<<<");
		return sf.toString();
	}

	public String getAuthenticatorSource() {
		return AuthenticatorSource;
	}

	public void setAuthenticatorSource(String AuthenticatorSource) {
		this.AuthenticatorSource = AuthenticatorSource;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getVersion() {
		return Version;
	}

	public void setVersion(int Version) {
		this.Version = Version;
	}
}