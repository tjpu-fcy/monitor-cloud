package com.init;

import com.common.CommonInfo;

import com.device.DeviceInitTask;
import com.netty.NettyInit;
import com.rabbitmq.FanoutSendTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class NettyTaskInit implements CommandLineRunner{

    @Autowired
    private DeviceInitTask deviceInitTask;

    @Autowired
    private FanoutSendTest fanoutSendTest ;

    @Override
    public void run(String... strings) throws Exception {

        //线程池初始化
        CommonInfo.cachedThread = Executors.newFixedThreadPool(CommonInfo.ExecutorLength);
        System.out.println("线程池创建成功");

        //启动netty通信采集任务
        NettyInit nettyInit = new NettyInit();
        nettyInit.init();

//		String path= Thread.currentThread().getContextClassLoader().getResource("").getPath();
//		path = path + "com/protocol";
//		System.out.println(path);
//		// 获得该文件夹内的所有文件名字
//		File file = new File(path);
//		File[] array = file.listFiles();
//		for(int i=0;i<array.length;i++){
//			if(array[i].isFile()){
//				//获取文件名字
//				String fileName = array[i].getName();
//				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
//				//判断是否是class文件
//				if(suffix.equals("class")){
//					//得到文件名字的前缀
//					String preFileName = fileName.substring(0, fileName.lastIndexOf("."));
//					String className = "com.fdway.protocol." + preFileName;
//					try {
//						Class<?> cls = Class.forName(className);
//						ProtocolClassManage.addClassManage(preFileName , cls);
//					} catch (ClassNotFoundException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}

        //创建设备节点初始化线程
        deviceInitTask.start();
        System.out.println("设备节点初始化线程启动");

        //rabbitmq 发送测试线程
        fanoutSendTest.start();
    }
}

