package com.example.spring.rabbitmq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: fs
 * @date: 2023/1/7 17:26
 * @Description: everything is ok
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ProducerTest {

    //注入RabbitTemplate对象
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //测试简单模式
    @Test
    public void tset1(){
        rabbitTemplate.convertAndSend("spring_simple_queue","测试简单模式");
    }

    //测试广播模式
    @Test
    public void test2(){
        rabbitTemplate.convertAndSend("spring_fanout_exchange","","测试广播模式");
    }

    //测试通配符模式
    @Test
    public void test3(){
        rabbitTemplate.convertAndSend("spring_topic_exchange","fs.aa","测试通配符模式队列1");
        rabbitTemplate.convertAndSend("spring_topic_exchange","aa.bb.fs","测试通配符模式队列2");
    }

    //测试确认模式和路由模式
    @Test
    public void tset4(){

        //定义确认的回调函数,不论成功失败都会调用改回调函数
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack){
                    //投递成功
                    System.out.println("投递成功!");
                }else {
                    System.out.println("投递失败! 失败的原因是:" + cause);
                    //需要做一些处理重新投递消息
                }
            }
        });

        //通过定向交换机测试确认模式
        //rabbitTemplate.convertAndSend("spring_direct_exchange","confirm","通过定向交换机测试确认模式");
        //rabbitTemplate.convertAndSend("spring_direct_exchange111","confirm","通过定向交换机测试确认模式");

        //测试消息的手动确认
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("spring_direct_exchange","confirm","通过定向交换机测试确认模式" + (i + 1));
        }
    }

    //测试回退模式
    @Test
    public void test5(){

        //设置 强制退回,必须设置,否则即使失败也不会调用回调函数
        rabbitTemplate.setMandatory(true);

        //设置回退模式的回调函数,只有失败的时候才会调用该回调函数
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                //获取消息
                System.out.println("new String(message.getBody()) = " + new String(message.getBody()));
                //错误消息
                System.out.println("replyText = " + replyText);
                //错误码
                System.out.println("replyCode = " + replyCode);
                //交换机的名字
                System.out.println("exchange = " + exchange);
                //路由key
                System.out.println("routingKey = " + routingKey);
            }
        });
        //通过定向交换机测试回退模式
        //成功
        rabbitTemplate.convertAndSend("spring_direct_exchange","confirm","通过定向交换机测试回退模式");
        //失败
        //rabbitTemplate.convertAndSend("spring_direct_exchange","confirm2123123","通过定向交换机测试回退模式");
    }

    /*
        测试消息的过期时间
        如果队列和消息都设置了过期时间，以时间短的为准
     */
    @Test
    public void test6(){

        //创建消息的后置处理器
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor(){
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置消息的有效时间为5秒
                message.getMessageProperties().setExpiration("5000");
                return message;
            }
        };

        rabbitTemplate.convertAndSend("test_ttl_exchange","test.aa.bb","测试消息的过期时间",messagePostProcessor);
    }

    /**
     * 发送测试死信消息：
     *  1. 过期时间
     *  2. 长度限制
     *  3. 消息拒收
     */
    @Test
    public void testDlx1(){
        //1. 测试过期时间，死信消息
        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","我是一条消息,我会死吗？");
    }
    /**
     * 发送测试死信消息：
     *  1. 过期时间
     *  2. 长度限制
     *  3. 消息拒收
     */
    @Test
    public void testDlx2(){
        //1. 测试过期时间,死信消息
        //rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","我是一条消息,我会死吗？");

        //2. 测试长度限制后,消息死信
        for (int i = 0; i < 11; i++) {
            rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","我是一条消息,我会死吗？");
        }
    }

    /**
     * 发送测试死信消息
     *  1. 过期时间
     *  2. 长度限制
     *  3. 消息拒收
     */
    @Test
    public void testDlx(){
        //3. 测试消息拒收
        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","此消息会被拒收并不放回原队列");
    }

}
