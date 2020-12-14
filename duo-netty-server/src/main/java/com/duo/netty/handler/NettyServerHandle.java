package com.duo.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.duo.core.utils.DateUtils;
import com.duo.core.utils.RedisUtil;
import com.duo.core.utils.StringUtils;
import com.duo.netty.bean.BaseMessage;
import com.duo.netty.config.NettyServerHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author [ Yonsin ] on [2020/7/24]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class NettyServerHandle extends SimpleChannelInboundHandler<BaseMessage> {

    /**
     * channel注册事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        log.info("客户端ip地址：{}", clientIp);
        //判断ip是否被禁止访问

        //注册连接
        NettyServerHolder.ChannelId2ChannelMAP.put(String.valueOf(ctx.channel().id()), (NioSocketChannel) ctx.channel());
        System.out.println("channel注册了");
        super.channelRegistered(ctx);
    }

    //    /**
//     * channel取消注册事件
//     * @param ctx
//     * @throws Exception
//     */
//    @Override
//    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//        NettyServerHolder.remove((NioSocketChannel) ctx.channel());
//        System.out.println("channel移除了");
//        super.channelUnregistered(ctx);
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channel 活跃");
//        super.channelActive(ctx);
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        NettyServerHolder.remove((NioSocketChannel) ctx.channel());
//        System.out.println("channel 不活跃，断开连接");
//        super.channelInactive(ctx);
//    }
//
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("channel("+ctx.channel().toString()+") 捕获到异常了，关闭了");
        String deviceId = NettyServerHolder.ChannelId2TermIdMAP.get(String.valueOf(ctx.channel().id()));
        log.error("客户端（" + deviceId + "）异常关闭了！"+ DateUtils.getDate(new Date()));
        if (StringUtils.isNotEmpty(deviceId))
            NettyServerHolder.OFFLineMap.put(deviceId, String.valueOf(ctx.channel().id()));
        NettyServerHolder.ChannelId2ChannelMAP.remove(String.valueOf(ctx.channel().id()));
        NettyServerHolder.ChannelId2TermIdMAP.remove(String.valueOf(ctx.channel().id()));
        if (StringUtils.isNotEmpty(deviceId)) {
            NettyServerHolder.TermId2ChannelIdMAP.remove(deviceId);
        }
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 用户事件触发(可以定时)
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                //向客户端发送消息
                // ctx.writeAndFlush(HEART_BEAT).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }
        super.userEventTriggered(ctx, evt);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage message) throws Exception {
        if (!message.getMessage_type().equals("999"))
            log.info("收到NettyMessage={}", message);
        if (StringUtils.isEmpty(message.getDevice_id())) {
            log.info("收到Message 缺少device_id值，不合法！");
            //向服务端发送000消息
            BaseMessage errorMsg = new BaseMessage();
            errorMsg.setDevice_id(message.getDevice_id());
            errorMsg.setMessage_type("000");//000错误指令
            errorMsg.setConnect_message("收到Message 缺少device_id值，不合法！");
            ctx.writeAndFlush(errorMsg);
            return;
        }

        if (message != null && message.getMessage_type().equals("999")) {//收到心跳返回心跳
            log.info("收到（" + message.getDevice_id() + "）心跳，返回心跳！");
            //向服务端发送消息
            BaseMessage heartBeat = new BaseMessage();
            heartBeat.setDevice_id(message.getDevice_id());
            heartBeat.setMessage_type("999");
            ctx.writeAndFlush(heartBeat);
            if (!NettyServerHolder.OnlineTermList.contains(message.getDevice_id())) {
                InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
                String clientIp = ipSocket.getAddress().getHostAddress();
                NettyServerHolder.ONLineMap.put(message.getDevice_id(), clientIp);
            }
        } else {
            //将受到的消息加入链接里
            //NettyServerHolder.MSGlinkedList.add(message);
            RedisUtil.lPush("nettylinkedList", JSONObject.toJSONString(message));
        }

        //我们调用writeAndFlush（Object）来逐字写入接收到的消息并刷新线路
        //ctx.writeAndFlush(customProtocol);
        //保存客户端与 Channel 之间的关系
        if (!NettyServerHolder.ChannelId2TermIdMAP.contains(String.valueOf(ctx.channel().id()))) {
            NettyServerHolder.TermId2ChannelIdMAP.put(message.getDevice_id(), String.valueOf(ctx.channel().id()));
            NettyServerHolder.ChannelId2TermIdMAP.put(String.valueOf(ctx.channel().id()), message.getDevice_id());
        }
    }
}
