package com.duo.client.encode;

import com.alibaba.fastjson.JSONObject;
import com.duo.common.model.BaseMessage;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.ByteArrayInputStream;
import java.util.List;


/**
 * @author [ Yonsin ] on [2020/7/24]
 * @Description： [ 解码器 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class ClientDecoder extends ByteToMessageDecoder {
    public static final int HEAD_LENGTH = 4;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() < HEAD_LENGTH) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于Encoder中我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
            return;
        }
        in.markReaderIndex();                  //我们标记一下当前的readIndex的位置
        int dataLength = in.readInt();       // 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4
        if (dataLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
            ctx.close();
        }

        if (in.readableBytes() < dataLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        String content = new String(bytes,"UTF-8");
        JSONObject json  = JSONObject.parseObject(content);
        BaseMessage msg=JSONObject.toJavaObject(json,BaseMessage.class);
        list.add(msg);
    }
//
//
//    private Kryo kryo = new Kryo();
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
//        log.info("............");
//        if (in.readableBytes() < HEAD_LENGTH) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于Encoder中我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
//            return;
//        }
//        in.markReaderIndex();                  //我们标记一下当前的readIndex的位置
//        int dataLength = in.readInt();       // 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4
//        if (dataLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
//            ctx.close();
//        }
//
//        if (in.readableBytes() < dataLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
//            in.resetReaderIndex();
//            return;
//        }
//
//        byte[] body = new byte[dataLength];  //传输正常
//        in.readBytes(body);
//        Object o = convertToObject(body);  //将byte数据转化为我们需要的对象
//        list.add(o);
//    }
//
//    private Object convertToObject(byte[] body) {
//
//        Input input = null;
//        ByteArrayInputStream bais = null;
//        try {
//            bais = new ByteArrayInputStream(body);
//            input = new Input(bais);
//
//            return kryo.readObject(input, BaseMessage.class);
//        } catch (KryoException e) {
//            e.printStackTrace();
//        }finally{
//            IOUtils.closeQuietly(input);
//            IOUtils.closeQuietly(bais);
//        }
//
//        return null;
//    }
}

