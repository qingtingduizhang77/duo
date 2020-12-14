package com.duo.netty.config;

import com.duo.netty.encode.NettyServerDecoder;
import com.duo.netty.encode.NettyServerEncoder;
import com.duo.netty.handler.NettyServerHandle;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;


/**
 * @author [ Yonsin ] on [2020/7/24]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class NettyInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                //五秒没有收到消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(5, 0, 0))
                .addLast(new NettyServerDecoder())
                .addLast(new NettyServerEncoder())
                .addLast(new NettyServerHandle());
    }
}
