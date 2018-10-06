package com.netty;


import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyChannelManage {
    //绑定channel 和通道唯一标识
    private static Map<String, SocketChannel> map = new ConcurrentHashMap<String, SocketChannel>();

    public void addChannel(String signal, SocketChannel channel){
        map.put(signal, channel);
    }

    public Map<String, SocketChannel> getChannels(){
        return map;
    }

    public SocketChannel getChannel(String signal){
        return map.get(signal);
    }

    public void removeChannel(String signal){
        map.remove(signal);
    }


}
