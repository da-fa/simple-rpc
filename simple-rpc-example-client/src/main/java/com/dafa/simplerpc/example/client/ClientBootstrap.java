package com.dafa.simplerpc.example.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Mtime on 2017/10/12 0012.
 */
public class ClientBootstrap {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-zk-rpc-client.xml");
        Test test = (Test) context.getBean("test");
        test.helloTest();
        System.out.println("====>end main");
    }

}
