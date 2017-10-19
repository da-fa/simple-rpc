package com.dafa.simplerpc.rpc;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Mtime on 2017/10/11 0011.
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(RpcHandler.class);

    private final Map<String, Object> handlerMap;

    public RpcHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        logger.info("===>接收到的请求:{}", JSON.toJSON(request));
        RpcResponse response = new RpcResponse();
        response.setRequestId(response.getRequestId());
        try {
            Object result = this.handle(request);
            response.setResult(result);
        } catch (Throwable throwable) {
            logger.error("调用异常", throwable);
            response.setError(throwable);
        }
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        logger.info("===>返回结果{}", JSON.toJSON(response));
    }

    private Object handle(RpcRequest request) throws Throwable {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);
        Class<?> serviceClass = serviceBean.getClass();
        String mehtodName = request.getMehtodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(mehtodName, parameterTypes);

        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("server caught exception:", cause);
        ctx.close();
    }
}
