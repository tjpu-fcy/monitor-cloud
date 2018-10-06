package com.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * 全局公共信息
 */


public class CommonInfo {

	//线程池大小
	public static  int ExecutorLength ;

	//创建一个固定大小线程池
	public static ExecutorService cachedThread = null;


	@Value("${userConfig.executorLength}")
	public  void setExecutorLength(int executorLength) {
		CommonInfo.ExecutorLength = executorLength;
	}


}
