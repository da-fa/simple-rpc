package com.dafa.simplerpc.rpc;

/**
 * Created by Mtime on 2017/10/11 0011.
 */
public class RpcRequest {

    private String requestId;
    private String className;
    private String mehtodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMehtodName() {
        return mehtodName;
    }

    public void setMehtodName(String mehtodName) {
        this.mehtodName = mehtodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
