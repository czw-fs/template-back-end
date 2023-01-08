package com.example.springboot.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: fs
 * @date: 2023/1/8 19:21
 * @Description: everything is ok
 */
@Component
public class SpringBootRabbitMQConsumerConfig {

    @RabbitListener(queues = "springboot_queue")
    public void getMessage(Message message){
        System.out.println("new String(message.getBody()) = " + new String(message.getBody()));
    }
}
