package com.example.spring.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.io.IOException;

/**
 * @author: fs
 * @date: 2023/1/8 9:36
 * @Description: everything is ok
 */
public class AckListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //获取消息
        System.out.println("new String(message.getBody()) = " + new String(message.getBody()));
        //获取消息标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {


            //处理业务逻辑
            //业务逻辑出现异常
            //int a = 10/0;
        /*
            如果没有收到异常,收到确认消息

            basicAck()方法中参数说明:
            第一个参数是消息的标识
            第二个参数是设置是否批量确认，如果为true，第一条消息确认后后面的自动确人

         */

            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            e.printStackTrace();
            /*
                出现异常,不确认消息
                basicNack()方法参数说明
                前两个参数与basicAck()方法中参数一致，
                第三个参数用来设置当消息不确认时是否返回原消息队列
             */
            channel.basicNack(deliveryTag,true,true);
        }
    }
}
