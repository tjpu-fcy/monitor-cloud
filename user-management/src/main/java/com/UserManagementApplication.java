package com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Timer;
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
@EnableFeignClients
public class UserManagementApplication {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(UserManagementApplication.class);

		//application.addListeners(new MyApplicationPreparedEventListener());
		application.run(args);






	}

}
