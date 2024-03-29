package com.fuller.component.xrpc.marshaller;

import io.grpc.MethodDescriptor;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Allen Huang on 2022/2/15
 */
@SuppressWarnings("rawtypes")
public class DefaultMarshallerRegister implements MarshallerRegister {

    private final Map<Type, MethodDescriptor.Marshaller> marshallerMap = new HashMap<>();

    private final MarshallerFactory defaultFactory;

    public DefaultMarshallerRegister(MarshallerFactory defaultFactory) {
        this.defaultFactory = defaultFactory;
    }

    @Override
    public boolean existsMarshaller(Type type) {
        return marshallerMap.containsKey(type);
    }

    @Override
    public void registerMarshaller(Type type, MethodDescriptor.Marshaller marshaller) {
        synchronized (marshallerMap) {
            marshallerMap.put(type, marshaller);
        }
    }

    @Override
    public MethodDescriptor.Marshaller getMarshaller(Type type) {
        MethodDescriptor.Marshaller marshaller = marshallerMap.get(type);
        if (marshaller == null) {
            synchronized (marshallerMap) {
                marshaller = marshallerMap.get(type);
                if (marshaller == null) {
                    marshaller = defaultFactory.getMarshaller(type);
                    marshallerMap.put(type, marshaller);
                }
            }
        }
        return marshaller;
    }

}
