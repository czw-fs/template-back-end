package com.example.rabbitmq.fanout;

import com.example.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author: fs
 * @date: 2023/1/7 14:38
 * @Description: everything is ok
 */
//发布订阅模式(广播模式)的生产者
public class Producer {

    public static final String QUEUE_NAME1 = "fanout_queue1";
    public static final String QUEUE_NAME2 = "fanout_queue2";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();

        //声明交换机
        /*
       exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
       参数：
        1. exchange：指定交换机的名字
        2. type：指定交换机的类型
            DIRECT("direct"),：定向
            FANOUT("fanout"),：扇形（广播）,发送消息到每一个与之绑定队列。
            TOPIC("topic"),通配符的方式
            HEADERS("headers");参数匹配
        3. durable：指定交换机是否是持久的
        4. autoDelete：指定交换机是否是自动删除的
        5. internal：指定交换机是否是内部使用的。 一般false
        6. arguments：其他参数
        */

        channel.exchangeDeclare("test_fanout", BuiltinExchangeType.FANOUT,
                 true,false,false,null);

        //声明队列
        channel.queueDeclare(QUEUE_NAME1,true,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,true,false,false,null);

        //将队列与交换机进行绑定(两个队列绑定一个交换机)
        channel.queueBind(QUEUE_NAME1,"test_fanout","");
        channel.queueBind(QUEUE_NAME2,"test_fanout","");

        //设置发送的消息
        String message = "rabbit gd";

        //发送笑消息
        channel.basicPublish("test_fanout","",null,message.getBytes());

        
    }
}
