package com;

import com.device.ProtocolClassManage;
import com.common.CommonInfo;
import com.device.DeviceInitTask;
import com.listener.MyApplicationReadyEventListener;
import com.netty.NettyInit;
import com.rabbitmq.FanoutSendTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.File;
import java.util.concurrent.Executors;

/**
 *
 * @version 1.0.0
 * @blog http://blog.fdway.com
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class DeviceManagementApplication {


	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(DeviceManagementApplication.class);
        application.addListeners(new MyApplicationReadyEventListener());
		application.run(args);



	}

}
