package com;

import com.common.CommonInfo;
import com.common.QueueData;
import com.listener.MyApplicationPreparedEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author fcy
 * @version 1.0.0
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class NoticeApplication {



	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(NoticeApplication.class);

		application.addListeners(new MyApplicationPreparedEventListener());
		application.run(args);


	}

}
