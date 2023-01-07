package com.example.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: fs
 * @date: 2023/1/7 10:25
 * @Description: everything is ok
 */
//简单模式的生产者
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置主机
        connectionFactory.setHost("192.168.6.100");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置虚拟主机
        connectionFactory.setVirtualHost("/");
        //设置用户名
        connectionFactory.setUsername("admin");
        //设置密码
        connectionFactory.setPassword("123456");

        //获取连接
        Connection connection = connectionFactory.newConnection();

        //创建信道
        Channel channel = connection.createChannel();

        /*
         * queue      参数1：队列名称
         * durable    参数2：指定该队列是否持久,如果为true,则服务重启之后该队列依然存在
         * exclusive  参数3：是否独占本次连接,如果为true,则只有当前连接才能使用该队列
         * autoDelete 参数4：所有consumer断开连接后自动删除队列
         * arguments  参数5：队列其它参数
         */
        channel.queueDeclare("simple_queue",true,false,true,null);

        //创建要发送的消息
        String message = "你好,小白兔";

        /*
         * 参数1：交换机名称,如果没有指定则使用默认Default Exchage
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：配置信息
         * 参数4：消息内容
         */
        //发布消息
        channel.basicPublish("","simple_queue",null,message.getBytes());

        //关闭资源
        channel.close();
        connection.close();

    }
}
