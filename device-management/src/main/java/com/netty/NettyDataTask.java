package com.netty;


import com.device.DeviceManage;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;

import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TimerTask;

//netty 数据定时采集任务
public class NettyDataTask  extends TimerTask {


    @Override
    public void run() {
        try {
            Map<String, SocketChannel> map = NettyCommon.nettyChannelManage.getChannels();
            for(Map.Entry<String,SocketChannel > entry:map.entrySet()){
              //  System.out.println(entry.getKey());
               if (DeviceManage.getMap(entry.getKey())!=null) {
                   Map<String, Object> map1 = DeviceManage.getMap(entry.getKey());
                   Object obj = map1.get("protocol");
                   Class<?> protocolClass =  (Class<?>)map1.get("protocolClass");
                   Method mt = protocolClass.getMethod("send");
                   byte[][] ret=(byte[][])mt.invoke(obj);
                   SocketChannel ctx = entry.getValue();

                 for(int m=0;m<ret.length;m++){//控制行数
                     ctx.writeAndFlush(Unpooled.copiedBuffer(ret[m]));
                     System.out.println(entry.getKey()+"发送数据:"+  DatatypeConverter.printHexBinary(ret[m]));
                     Thread.sleep(2000);
                }

               }
            }

        } catch (Exception e) {
            System.out.println("-------------解析信息发生异常--------------");
        }
    }

}
