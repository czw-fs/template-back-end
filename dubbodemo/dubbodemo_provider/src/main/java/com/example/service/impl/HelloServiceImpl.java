package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.service.HelloService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: fs
 * @date: 2022/12/28 21:29
 * @Description: everything is ok
 */
@Service(interfaceClass = HelloService.class) //使用dubbo中的注解
@Transactional
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return name;
    }
}
