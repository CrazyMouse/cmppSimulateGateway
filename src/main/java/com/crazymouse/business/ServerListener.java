package com.crazymouse.business;

import com.crazymouse.business.handler.CmppDecoder;
import com.crazymouse.business.handler.CmppEncoder;
import com.crazymouse.util.ConfigUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title ：服务端口监听器
 * Description :
 * Create Time: 14-4-17 下午2:46
 */
public class ServerListener {
    Logger logger = LoggerFactory.getLogger(ServerListener.class);
    private ConfigUtil configUtil;
    private ChannelHandler channelHandler;

    public void startListener(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.TCP_NODELAY,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(0, 0, Integer.valueOf(configUtil.getConfig("idleSeconds"))));
                        ch.pipeline().addLast(new CmppDecoder());
                        ch.pipeline().addLast(new CmppEncoder());
                        ch.pipeline().addLast(channelHandler);
                    }
                });
        try {
            serverBootstrap.bind(Integer.parseInt(configUtil.getConfig("listenPort"))).sync();
            logger.info("Server Start Success,Listning on Port:【{}】",configUtil.getConfig("listenPort"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setConfigUtil(ConfigUtil configUtil) {
        this.configUtil = configUtil;
    }

    public void setChannelHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
    }
}
