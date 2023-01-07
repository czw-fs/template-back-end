package com.example.rabbitmq.work;

import com.example.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: fs
 * @date: 2023/1/7 13:46
 * @Description: everything is ok
 */
public class Producer {

    public static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        //一次发10条消息
        for (int i = 0; i < 10; i++) {
            //设置要发送的消息
            String messge = "消息" + (i+1);
            channel.basicPublish("",QUEUE_NAME,null,messge.getBytes());
        }

        ConnectionUtil.closeResource(channel,connection);
    }
}
