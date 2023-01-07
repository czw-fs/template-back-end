package com.example.rabbitmq.topic;

import com.example.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author: fs
 * @date: 2023/1/7 16:01
 * @Description: everything is ok
 */
public class Producer {

    private static final String QUEUE_NAME1 = "topic_queue1";
    private static final String QUEUE_NAME2 = "topic_queue2";

    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME1,true,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,true,false,false,null);

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC,true,false,null);

        //队列绑定交换机
        channel.queueBind(QUEUE_NAME1,EXCHANGE_NAME,"#.fs");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"fs.#");
        channel.queueBind(QUEUE_NAME1,EXCHANGE_NAME,"*.fs");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"fs.*");

        //设置发送消息
        String message = "good name";

        //发送消息
//        channel.basicPublish(EXCHANGE_NAME,"aa.bb.fs",null,message.getBytes());
//        channel.basicPublish(EXCHANGE_NAME,"fs.aa.bb",null,message.getBytes());
//        channel.basicPublish(EXCHANGE_NAME,"aa.fs",null,message.getBytes());
//        channel.basicPublish(EXCHANGE_NAME,"fs.aa",null,message.getBytes());
        channel.basicPublish(EXCHANGE_NAME,"fs",null,message.getBytes());
        ConnectionUtil.closeResource(channel,connection);
    }
}
