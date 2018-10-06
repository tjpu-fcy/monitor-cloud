package com.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 上下文创建完成后执行的事件监听器
 *
 */
public class MyApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    // private Logger logger = LoggerFactory.getLogger(MyApplicationReadyEventListener.class);


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
       // System.out.println("监听器初始化");

    }

    /**
     * 传递上下文
     */
    private void passContextInfo(ApplicationContext cac) {

    }
}