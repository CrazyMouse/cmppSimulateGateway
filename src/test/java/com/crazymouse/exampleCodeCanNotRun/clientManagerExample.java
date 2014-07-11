package com.crazymouse.exampleCodeCanNotRun;

import com.crazymouse.business.util.ClientManager;
import com.crazymouse.business.util.PropertiesChannelMatcher;
import com.crazymouse.entity.Constants;

/**
 * Title ： 样例代码，不能执行
 * Description :
 * Create Time: 14-7-11 下午1:36
 */
public class clientManagerExample {
    private ClientManager clientManager;

    void closeClientByVersion() {
        //关闭所有cmpp2协议链接
        clientManager.closeMatchChannels(new PropertiesChannelMatcher<Integer>(Constants.PROTOCALTYPE_VERSION, Constants.PROTOCALTYPE_VERSION_CMPP2));
        //关闭所有cmpp3协议链接
        clientManager.closeMatchChannels(new PropertiesChannelMatcher<Integer>(Constants.PROTOCALTYPE_VERSION, Constants.PROTOCALTYPE_VERSION_CMPP3));
    }

    void SendMsgToMatchChannels(){
        //发送消息给所有cmpp2协议客户端
        clientManager.sendMessageToMatchChannels(new Object(),new PropertiesChannelMatcher<Integer>(Constants
                .PROTOCALTYPE_VERSION,Constants.PROTOCALTYPE_VERSION_CMPP2));
        //发送消息给所有cmpp3协议客户端
        clientManager.sendMessageToMatchChannels(new Object(),new PropertiesChannelMatcher<Integer>(Constants
                .PROTOCALTYPE_VERSION,Constants.PROTOCALTYPE_VERSION_CMPP3));

    }

    void activeClientCounts(){
        //查看活动客户端连接数
        clientManager.activeChannels();
    }
}
