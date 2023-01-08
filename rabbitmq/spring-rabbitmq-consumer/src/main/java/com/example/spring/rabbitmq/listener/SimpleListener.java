package com.example.spring.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author: fs
 * @date: 2023/1/7 18:11
 * @Description: everything is ok
 */
public class SimpleListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        //获取消费者的唯一标识
        System.out.println("message.getMessageProperties().getConsumerTag() = " + message.getMessageProperties().getConsumerTag());
        //获取消息内容
        System.out.println("new String(message.getBody()) = " + new String(message.getBody()));
        //获取消费者消费的第几条消息
        System.out.println("message.getMessageProperties().getDeliveryTag() = " + message.getMessageProperties().getDeliveryTag());
        //获取交换机的名字
        System.out.println("message.getMessageProperties().getReceivedExchange() = " + message.getMessageProperties().getReceivedExchange());
        //获取路由key
        System.out.println("message.getMessageProperties().getReceivedRoutingKey() = " + message.getMessageProperties().getReceivedRoutingKey());
    }
}
