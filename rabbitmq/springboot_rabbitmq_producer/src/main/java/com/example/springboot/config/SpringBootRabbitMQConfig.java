package com.example.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: fs
 * @date: 2023/1/8 15:00
 * @Description: everything is ok
 */

@Configuration
public class SpringBootRabbitMQConfig {

    private static final String QUEUE_NAME = "springboot_queue";
    private static final String EXCHANGE_NAME = "springboot_exchange";

    //创建交换机
    @Bean
    public Exchange getExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    //创建队列
    @Bean
    public Queue getQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    //队列绑定交换机
    @Bean
    public Binding bindingQueueToExchange(Queue queue ,Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("springboot.#").noargs();
    }

}
