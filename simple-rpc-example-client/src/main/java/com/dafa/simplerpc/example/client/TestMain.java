package com.dafa.simplerpc.example.client;

import com.dafa.simplerpc.example.iface.HelloService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Mtime on 2017/10/18 0018.
 */
public class TestMain {


    public static void main(String[] args) {

        Class<?> cls = HelloService.class;

        HelloService o = (HelloService)Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                System.out.println("====>invoke before");
                Object invoke = method.invoke((HelloService) name -> "hello " + name, args);
                System.out.println("====>invoke end");
                return invoke;
            }
        });

        System.out.println("==============>"+o.hello("aaa"));

    }


}
