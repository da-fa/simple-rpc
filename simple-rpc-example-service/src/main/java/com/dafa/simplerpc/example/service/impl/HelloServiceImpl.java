package com.dafa.simplerpc.example.service.impl;

import com.dafa.simplerpc.annatation.RpcService;
import com.dafa.simplerpc.example.iface.HelloService;
import org.springframework.stereotype.Service;

/**
 * Created by Mtime on 2017/10/12 0012.
 */
@RpcService(HelloService.class)
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        System.out.println("hello===>" + name);
        return "Hello!" + name;
    }
}
