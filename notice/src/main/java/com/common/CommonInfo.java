package com.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * 全局公共信息
 */
@Component
public class CommonInfo {

	//线程池大小
	public  static  int ExecutorLength ;

	//创建一个固定大小线程池
	public static ExecutorService cachedThread = null;

	//通知消息的阻塞队列
	public static  BlockingQueue<QueueData> NoticeQueue = null;

	//阻塞队列的长度
	public static  int QueueLength  ;



	@Value("${userConfig.executorLength}")
	public  void setExecutorLength(int executorLength) {
		CommonInfo.ExecutorLength = executorLength;
	}

	@Value("${userConfig.queueLength}")
	public  void setQueueLength(int queueLength) {
		CommonInfo.QueueLength =  queueLength;
	}


}
