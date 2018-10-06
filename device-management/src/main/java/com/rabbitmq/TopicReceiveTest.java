package com.rabbitmq;




import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Component
public class TopicReceiveTest {

    //队列1监听器
    @RabbitListener(queues = "myQueue1")
    public void process1(Message message, Channel channel) {

        String mes= new String(message.getBody());
        System.out.println("队列1收到  : " + mes);

        // 消息处理完毕后，根据处理结果返回ack,或者nack
        if(true) {

            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                //返回nack,重新入队
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //队列2监听器
    @RabbitListener(queues = "myQueue2")
    public void process2(Message message, Channel channel) {
        String mes= new String(message.getBody());
        System.out.println("队列2收到  : " + mes);

        // 消息处理完毕后，根据处理结果返回ack,或者nack
        if(true) {
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                //返回nack,重新入队
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //队列3监听器
    @RabbitListener(queues = "myQueue3")
    public void process3(Message message, Channel channel) {

        String mes= new String(message.getBody());
        System.out.println("队列3收到  : " + mes);

        // 消息处理完毕后，根据处理结果返回ack,或者nack
        if(true) {

            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                //返回nack,重新入队
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


}