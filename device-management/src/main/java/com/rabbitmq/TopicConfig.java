package com.rabbitmq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    @Bean
    public Queue queue1() {
        //创建队列，并设置为持久化
        return new Queue("myQueue1",true);
    }

    @Bean
    public Queue queue2() {
        //创建队列，并设置为持久化
        return new Queue("myQueue2",true);
    }

    @Bean
    public Queue queue3() {
        //创建队列，并设置为持久化
        return new Queue("myQueue3",true);
    }

    //声明Topic交换机
    @Bean
    TopicExchange exchange1() {
        return new TopicExchange("rabbitmq-exchange-order");
    }

    //创建绑定A
    @Bean
    Binding bindingExchangeMessageA(Queue queue1, TopicExchange exchange1) {
        return BindingBuilder.bind(queue1).to(exchange1).with("fdway.#");
    }

    //创建绑定B
    @Bean
    Binding bindingExchangeMessageB(Queue queue2, TopicExchange exchange1) {
        return BindingBuilder.bind(queue2).to(exchange1).with("fdway.test1");
    }

    //创建绑定C
    @Bean
    Binding bindingExchangeMessageC(Queue queue3, TopicExchange exchange1) {
        return BindingBuilder.bind(queue3).to(exchange1).with("fdway.test2");
    }

}
