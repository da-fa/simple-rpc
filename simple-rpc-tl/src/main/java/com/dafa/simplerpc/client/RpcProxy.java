package com.dafa.simplerpc.client;


import com.dafa.simplerpc.rpc.RpcRequest;
import com.dafa.simplerpc.rpc.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * Created by Mtime on 2017/10/11 0011.
 */
public class RpcProxy {

    private Logger logger = LoggerFactory.getLogger(RpcProxy.class);

    private String serverAddress;
    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> interfaceClass) {
        T t = null;
        logger.info("RPC Proxy 开始创建代理对象");
        try {
            t = (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new LocalProxyHandler() );
            logger.info("RPC Proxy创建代理对象结束");
        } catch (IllegalArgumentException e) {
            logger.error("RPC Proxy exception:",e);
        }
        return t;
    }

    private class LocalProxyHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcRequest request = new RpcRequest();
            request.setRequestId(UUID.randomUUID().toString());
            request.setClassName(method.getDeclaringClass().getName());
            request.setMehtodName(method.getName());
            request.setParameterTypes(method.getParameterTypes());
            request.setParameters(args);

            if (null != serviceDiscovery) {
                serverAddress = serviceDiscovery.discover();//发现服务
            }

            String[] array = serverAddress.split(":");
            String host = array[0];
            int port = Integer.parseInt(array[1]);
            RpcClient rpcClient = new RpcClient(host, port);
            RpcResponse response = rpcClient.send(request);
            if (response.getError() != null) {
                throw response.getError();
            } else {
                return response.getResult();
            }
        }
    }

}
