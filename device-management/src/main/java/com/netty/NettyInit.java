package com.netty;

import java.util.Timer;

public class NettyInit {

    //端口号
    private  int port=8899;
    //采集周期
    private  long PERIOD_DAY =  3* 1000;

    public  void init(){
        //netty端口号
        System.out.println("netty服务开始启动");
        synchronized(this) {
             NettyServer nettyServer = new NettyServer(port);
             nettyServer.run();
        }
        System.out.println("netty服务启动成功");
        this.NettyDatetimer();

    }

    // netty数据定时采集的 timer
    private  void NettyDatetimer() {
        Timer timer = new Timer();
        NettyDataTask nettyDataTask = new NettyDataTask();
        timer.scheduleAtFixedRate(nettyDataTask, 0, PERIOD_DAY );
    }

}
