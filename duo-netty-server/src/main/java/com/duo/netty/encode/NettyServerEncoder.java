package com.duo.netty.encode;

import com.alibaba.fastjson.JSON;
import com.duo.netty.bean.BaseMessage;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.ByteArrayOutputStream;

/**
 * 客户端编码器
 */
public class NettyServerEncoder extends MessageToByteEncoder<BaseMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BaseMessage msg, ByteBuf byteBuf) throws Exception {
        String json= JSON.toJSONString(msg);
        byte[] body =json.getBytes("UTF-8");
        byteBuf.writeInt(body.length);  //先将消息长度写入，也就是消息头
        byteBuf.writeBytes(body) ;
    }


//    private Kryo kryo = new Kryo();
//    @Override
//    protected void encode(ChannelHandlerContext ctx, BaseMessage msg, ByteBuf out) throws Exception {
//
//        byte[] body = convertToBytes(msg);  //将对象转换为byte
//        int dataLength = body.length;  //读取消息的长度
//        out.writeInt(dataLength);  //先将消息长度写入，也就是消息头
//        out.writeBytes(body);  //消息体中包含我们要发送的数据
//    }
//
//    private byte[] convertToBytes(BaseMessage msg) {
//
//        ByteArrayOutputStream bos = null;
//        Output output = null;
//        try {
//            bos = new ByteArrayOutputStream();
//            output = new Output(bos);
//            kryo.writeObject(output, msg);
//            output.flush();
//
//            return bos.toByteArray();
//        } catch (KryoException e) {
//            e.printStackTrace();
//        }finally{
//            IOUtils.closeQuietly(output);
//            IOUtils.closeQuietly(bos);
//        }
//        return null;
//    }

}
