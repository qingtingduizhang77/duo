package com.duo.client.init;

import com.duo.client.encode.ClientDecoder;
import com.duo.client.encode.ClientEncoder;
import com.duo.client.handle.ClientHandle;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                //10 秒没发送消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(0, 10, 0))
                .addLast(new ClientDecoder())
                .addLast(new ClientEncoder())
                .addLast(new ClientHandle());
    }
}
