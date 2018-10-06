package com.rabbitmq;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    @Bean
    public Queue FanoutQueue1() {
        //创建队列，并设置为持久化
        return new Queue("FanoutQueue1",true);
    }

    @Bean
    public Queue FanoutQueue2() {
        //创建队列，并设置为持久化
        return new Queue("FanoutQueue2",true);
    }


    //声明Fanout交换机
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("rabbitmq-exchange-fanout");
    }

    //创建绑定A
    @Bean
    Binding bindingFanoutExchangeMessageA(Queue FanoutQueue1,  FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(FanoutQueue1).to(fanoutExchange);
    }

    //创建绑定B
    @Bean
    Binding bindingFanoutExchangeMessageB(Queue FanoutQueue2,  FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(FanoutQueue2).to(fanoutExchange);
    }



}
