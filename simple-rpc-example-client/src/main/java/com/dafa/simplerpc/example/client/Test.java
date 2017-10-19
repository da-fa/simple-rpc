package com.dafa.simplerpc.example.client;

import com.dafa.simplerpc.client.RpcProxy;
import com.dafa.simplerpc.example.iface.HelloService;
import com.dafa.simplerpc.example.iface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mtime on 2017/10/12 0012.
 */
@Service(value = "test")
public class Test {

    private Logger logger = LoggerFactory.getLogger(Test.class);

    @Autowired
    private RpcProxy rpcProxy;

    public void helloTest() {
        logger.info("===>hello-test");
        try {
            HelloService helloService = rpcProxy.create(HelloService.class);
            String str = helloService.hello("word");
            logger.info("helloService result ===>:" + str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            UserService userService = rpcProxy.create(UserService.class);
            System.out.println("====>111111111111");
            String name = userService.getUserName(1);
            System.out.println("userService result ===>" + name);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
