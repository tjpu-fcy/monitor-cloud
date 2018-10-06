package com.init;

import com.common.CommonInfo;
import com.common.QueueConsume;
import com.common.QueueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class QueueConsumeInit implements CommandLineRunner{

    @Autowired
    private QueueConsume queueConsume;

    @Override
    public void run(String... strings) throws Exception {
        //线程池初始化
        CommonInfo.cachedThread = Executors.newFixedThreadPool(CommonInfo.ExecutorLength);
        System.out.println("线程池创建成功");

        //创建阻塞队列
        CommonInfo.NoticeQueue = new LinkedBlockingQueue<QueueData>(CommonInfo.QueueLength);
        System.out.println("队列创建成功");

        //创建阻塞队列的消费者线程

        queueConsume.start();
        System.out.println("队列消费者线程创建成功");

    }
}

