package com.fuller.component.xrpc.parser;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * @author Allen Huang on 2022/2/23
 */
@Component
public class DefaultMethodParameterParser implements MethodParameterParser {

    @Override
    public Type parse(Method method) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length != 1) {
            throw new IllegalArgumentException("RPC方法的参数列表必须包含一个参数，也只能有一个参数.未能解析的方法: " + method.getDeclaringClass().getName() + "#" + method.getName());
        }
        return parameters[0].getParameterizedType();
    }

    @Override
    public Object toRpcValue(Method method, Object[] args) {
        return args == null ? null : args.length == 0 ? null : args[0];
    }

    @Override
    public boolean isSupport(Method method) {
        return true;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

}
