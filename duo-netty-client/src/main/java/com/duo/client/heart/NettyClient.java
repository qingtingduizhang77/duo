package com.duo.client.heart;

import com.duo.client.init.ClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class NettyClient {
    private static EventLoopGroup group = new NioEventLoopGroup();
    @Value("${netty.server.port}")
    private int nettyPort;
    @Value("${netty.server.host}")
    private String host;

    private Channel channel;
    private static Bootstrap bootstrap = new Bootstrap();
    static {

        /**
         * NioSocketChannel用于创建客户端通道，而不是NioServerSocketChannel。
         * 请注意，我们不像在ServerBootstrap中那样使用childOption()，因为客户端SocketChannel没有父服务器。
         */
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ClientInitializer());
    }

    @PostConstruct
    public void start() throws InterruptedException {
        /**
         * 启动客户端
         * 我们应该调用connect()方法而不是bind()方法。
         */
        String[] hosts=host.split(";");
        String ip = hosts[0];
        ChannelFuture future = null;
        try {
            future = bootstrap.connect(ip, nettyPort).sync();
        }catch (Exception e){};
        if (future!=null&&future.isSuccess()) {
            log.info("启动 Netty 成功");
        }else{
            for(int i=1;i<hosts.length;i++){
                try {
                    ip = hosts[i];
                    future = bootstrap.connect(ip, nettyPort).sync();
                    if (future.isSuccess()) {
                        log.info("启动 Netty 成功");
                        break;
                    }
                }catch (Exception e){}
            }
        }
        if (future!=null&&future.isSuccess()) {
            channel =   future.channel();

            /**
             * 重连机制,每隔5s重新连接一次服务器
             */
            while(true){
                try {
                    Thread.sleep(10 * 1000);//10s后再连接

                    if (channel != null && channel.isActive()) {
                        continue;
                    }else {

                        log.info("重连::"+(channel != null)+channel.isActive());
                        future = bootstrap.connect(ip,nettyPort).sync();
                        if (future.isSuccess()) {
                            log.info("重连成功");
                            channel =   future.channel();
                            continue;
                        }
                    }
                }catch (Exception e){}
            }
        }else{
            try {
                Thread.sleep(30 * 1000);//30s后再连接
            }catch (Exception e){}
            start();
        }

    }


}
