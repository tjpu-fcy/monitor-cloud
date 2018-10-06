package com.rabbitmq;




import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FanoutSendTest extends Thread{

    @Autowired
    private  RabbitTemplate rabbitTemplate ;


    @Override
    public void run() {

        rabbitTemplate.setMandatory(true);
        //发送端确认回调函数
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                     System.out.println("消息成功到达交换机");
                }else {
                    // 如当发送消息给一个不存在的Exchange。这种情况Broker会关闭Channel；
                    System.out.println("消息没有到达交换机");
                }
            }
        });

        //消息到达了交换机,但没有到达队列，则回调return
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("来自rabbitmq的失败返回:" + new String(message.getBody()) + ",replyCode:" + replyCode + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
            }
        });
        rabbitTemplate.convertAndSend("rabbitmq-exchange-fanout", "", "你好，test");
       // System.out.println("发送成功");
    }

}