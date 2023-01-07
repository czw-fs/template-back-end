package com.example.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: fs
 * @date: 2023/1/7 11:32
 * @Description: everything is ok
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.6.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        /*
        声明队列
        作为一个消费者来说，可以不声明队列;
        如果声明了队列，消费消息时如果队列不存在可以自动创建一个队列，
        但是如果队列存在，声明队列时指定的参数值与生产者指定的不一致则会抛出异常
         */

        channel.queueDeclare("simple_queue",true,false,true,null);

        //创建consumer对象
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            /*
               回调方法,当收到消息后,会自动执行该方法
               1. consumerTag：标识
               2. envelope：获取一些信息,交换机,路由key...
               3. properties：配置信息
               4. body：数据
            */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //获取消费者的唯一的标识
                System.out.println("consumerTag = " + consumerTag);
                //获取交换机的名字
                System.out.println("envelope.getExchange() = " + envelope.getExchange());
                //获取路由key
                System.out.println("envelope.getRoutingKey() = " + envelope.getRoutingKey());
                //获取当前是第几条消息
                System.out.println("envelope.getDeliveryTag() = " + envelope.getDeliveryTag());
                //获取其他属性
                System.out.println("properties = " + properties);
                //获取消息内容
                System.out.println("new String(body) = " + new String(body));
            }
        };

        /*
        basicConsume(String queue, boolean autoAck, Consumer callback)
        参数：
            1. queue：设置队列名称
            2. autoAck：自动确认,是否自动确认 ,类似咱们发短信,发送成功会收到一个确认消息
            3. callback：回调对象,包含回调函数的Consumer对象
         */
        //消费消息
        // 消费者类似一个监听程序,主要是用来监听消息
        channel.basicConsume("simple_queue",true,defaultConsumer);
    }
}
