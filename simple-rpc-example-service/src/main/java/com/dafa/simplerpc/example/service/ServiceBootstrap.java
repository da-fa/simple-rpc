package com.dafa.simplerpc.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Mtime on 2017/10/12 0012.
 */
@SpringBootApplication
@ImportResource("classpath:spring-zk-rpc-server.xml")
public class ServiceBootstrap {

    public static void main(String[] args) {
//        new ClassPathXmlApplicationContext("spring-zk-rpc-server.xml");
        SpringApplication.run(ServiceBootstrap.class);
    }

}
