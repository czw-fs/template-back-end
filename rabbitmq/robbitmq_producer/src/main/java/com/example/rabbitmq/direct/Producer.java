package com.example.rabbitmq.direct;

import com.example.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: fs
 * @date: 2023/1/7 15:28
 * @Description: everything is ok
 */
public class Producer {

    private static final String QUEUE_NAME1 = "direct_queue1";
    private static final String QUEUE_NAME2 = "direct_queue2";

    private static final String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME1,true,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,true,false,false,null);
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true,false,null);

        //绑定队列和交换机
        channel.queueBind(QUEUE_NAME1,EXCHANGE_NAME,"error");

        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"error");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"info");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"warning");

        //设置发送消息
        String message = "rabbit";
        //发送消息
        //channel.basicPublish(EXCHANGE_NAME,"error",null,message.getBytes());
        channel.basicPublish(EXCHANGE_NAME,"info",null,message.getBytes());

        ConnectionUtil.closeResource(channel,connection);
    }
}
