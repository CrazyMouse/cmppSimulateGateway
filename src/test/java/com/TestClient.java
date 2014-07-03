package com;

import com.crazymouse.entity.Connect;
import com.crazymouse.entity.ConnectResp;
import com.crazymouse.entity.Constants;
import com.crazymouse.util.ProtocelUtil;
import com.google.common.primitives.Ints;
import junit.framework.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Title ：
 * Description :
 * Create Time: 14-7-3 下午3:21
 */
public class TestClient {
    /**
     * 测试登陆鉴权，正常和异常同时测试
     * @throws Exception
     */
    @Test
    public void testTestLogin() throws Exception {
        Socket socket = new Socket("127.0.0.1", 7890);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        Connect connect = new Connect();
        int timeStamp = 703152611;
        connect.setAuthenticatorSource(ProtocelUtil.getAuthString("SpiderMan", "001009", timeStamp));
        connect.setVersion(Constants.PROTOCALTYPE_VERSION_CMPP2.byteValue());
        connect.setTimeStamp(Ints.toByteArray(timeStamp));
        out.write(connect.doEncode());
        byte[] b = new byte[30];
        in.read(b);
        ConnectResp resp = new ConnectResp(Constants.PROTOCALTYPE_VERSION_CMPP2);
        resp.doDecode(b);
        socket.close();
        Assert.assertEquals(0, resp.getStatus());

        Socket socket1 = new Socket("127.0.0.1", 7890);
        OutputStream out1 = socket1.getOutputStream();
        InputStream in1 = socket1.getInputStream();
        Connect connect1 = new Connect();
        int timeStamp1 = 703152611;
        connect1.setAuthenticatorSource(ProtocelUtil.getAuthString("SpiderMan", "001008", timeStamp1));
        connect1.setVersion(Constants.PROTOCALTYPE_VERSION_CMPP2.byteValue());
        connect1.setTimeStamp(Ints.toByteArray(timeStamp1));
        out1.write(connect1.doEncode());
        byte[] b1 = new byte[30];
        in1.read(b1);
        ConnectResp resp1 = new ConnectResp(Constants.PROTOCALTYPE_VERSION_CMPP2);
        resp1.doDecode(b1);
        socket1.close();
        Assert.assertEquals(3, resp1.getStatus());
    }
}
