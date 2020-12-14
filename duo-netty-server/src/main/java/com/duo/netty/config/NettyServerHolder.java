package com.duo.netty.config;

import com.duo.netty.bean.BaseMessage;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author [ Yonsin ] on [2020/7/24]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class NettyServerHolder {

    public static boolean isStop=false;
    /** 服务器建立连接的终端管道 */
    public static  Map<String, NioSocketChannel> ChannelId2ChannelMAP = new ConcurrentHashMap<String, NioSocketChannel>();
    public static ConcurrentHashMap<String, String> ChannelId2TermIdMAP = new ConcurrentHashMap<String, String>(); // Channelid termid
    public static ConcurrentHashMap<String, String> TermId2ChannelIdMAP = new ConcurrentHashMap<String, String>(); // termid Channelid
    public static void removeChannel(NioSocketChannel nioSocketChannel) {
        ChannelId2ChannelMAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> ChannelId2ChannelMAP.remove(entry.getKey()));
    }


    //本服务器的在线设备清单
    public static List<String> OnlineTermList=new Vector();
    /** 收到的消息链表 */  //改用redis
//    public static ConcurrentLinkedQueue<BaseMessage> MSGlinkedList = new ConcurrentLinkedQueue<BaseMessage>();
    /** 待处理上线 */
    public static ConcurrentHashMap<String, String> ONLineMap = new ConcurrentHashMap<String, String>();// termid ip
    /** 待处理下线 */
    public static ConcurrentHashMap<String, String> OFFLineMap = new ConcurrentHashMap<String, String>();// termid channelid
}
