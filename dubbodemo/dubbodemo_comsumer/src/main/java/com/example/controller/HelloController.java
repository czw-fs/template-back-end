package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fs
 * @date: 2022/12/29 9:31
 * @Description: everything is ok
 */
@RestController
@RequestMapping("/demo")
public class HelloController {
    @Reference
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello(String name){
        String result = helloService.sayHello(name);
        System.out.println(helloService.getClass().getName());
        return result;
    }
}
