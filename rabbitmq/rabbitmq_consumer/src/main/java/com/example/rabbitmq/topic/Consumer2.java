package com.example.rabbitmq.topic;

import com.example.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author: fs
 * @date: 2023/1/7 14:03
 * @Description: everything is ok
 */
public class Consumer2 {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();

        //创建Consumer对象
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //获取消费者的唯一标识
                System.out.println("consumerTag = " + consumerTag);
                //获取交换机的名字
                System.out.println("envelope.getExchange() = " + envelope.getExchange());
                //获取路由key
                System.out.println("envelope.getRoutingKey() = " + envelope.getRoutingKey());
                //获取当前是第几条消息
                System.out.println("envelope.getDeliveryTag() = " + envelope.getDeliveryTag());
                //获取消息内容
                System.out.println("new String(body) = " + new String(body));
            }
        };

        //消费消息
        channel.basicConsume("topic_queue2",true,defaultConsumer);

    }
}
