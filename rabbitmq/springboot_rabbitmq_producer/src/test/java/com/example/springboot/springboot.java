package com.example.springboot;


import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: fs
 * @date: 2023/1/8 18:39
 * @Description: everything is ok
 */
@SpringBootTest
public class springboot {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test1(){
        rabbitTemplate.convertAndSend("springboot_exchange","springboot.aaa","springboot测试");
    }
}
