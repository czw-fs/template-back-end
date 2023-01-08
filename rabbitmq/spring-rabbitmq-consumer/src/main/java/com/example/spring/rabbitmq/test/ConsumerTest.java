package com.example.spring.rabbitmq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: fs
 * @date: 2023/1/7 17:26
 * @Description: everything is ok
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ConsumerTest {

    //测试简单模式
    @Test
    public void tset1(){
        while (true){

        }
    }

}
