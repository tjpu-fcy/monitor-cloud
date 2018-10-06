package com.listener;


import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

/**
 * 上下文创建完成后执行的事件监听器
 *
 */
public class MyApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {
    // private Logger logger = LoggerFactory.getLogger(MyApplicationPreparedEventListener.class);


    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {



    }

    /**
     * 传递上下文
     */
    private void passContextInfo(ApplicationContext cac) {

    }
}