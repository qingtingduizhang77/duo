package com.duo.client.handle;

import com.duo.common.model.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  ClientHandle继承了 ChannelInboundHandlerAdapter 的一个扩展(SimpleChannelInboundHandler),
 * 而ChannelInboundHandlerAdapter是ChannelInboundHandler的一个实现
 * ChannelInboundHandler提供了可以重写的各种事件处理程序方法
 *  目前，只需继承 SimpleChannelInboundHandler或ChannelInboundHandlerAdapter 而不是自己实现处理程序接口。
 *  我们在这里重写了channelRead0（）事件处理程序方法
 */
@Slf4j
public class ClientHandle extends SimpleChannelInboundHandler<BaseMessage> {


    //定时发送心跳
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                log.info("发送心跳。。。。");
                //向服务端发送消息
                BaseMessage heartBeat = new BaseMessage();
                heartBeat.setDevice_id("123");
                heartBeat.setMessage_type("999");
                ctx.writeAndFlush(heartBeat);

            }

        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     *  每当从服务端接收到新数据时，都会使用收到的消息调用此方法 channelRead0(),在此示例中，接收消息的类型是ByteBuf。
     * @param channelHandlerContext
     * @param message
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMessage message) throws Exception {
        //从服务端收到消息时被调用

        if(!message.getMessage_type().equals("999")){
//            BaseMessage heartBeat = new BaseMessage();
//            heartBeat.setDevice_id(message.getDevice_id());
//            heartBeat.setMessage_type("已收到最新消息！");
            log.info("已收到最新消息={}", message);
            channelHandlerContext.writeAndFlush(message);

        }
    }
}
