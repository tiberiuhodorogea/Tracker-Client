package com.example.tiber.googleperformancehost.SharedClasses.Communication;

import java.lang.reflect.Type;

/**
 * Created by tiber on 4/14/2016.
 */
public class TypePairContainer {
    private Type requestDataType;
    private Type responseDataType;

    public TypePairContainer(Type requestDataType, Type responseDataType) {
        this.requestDataType = requestDataType;
        this.responseDataType = responseDataType;
    }

    public Type getRequestDataType() {
        return requestDataType;
    }

    public Type getResponseDataType() {
        return responseDataType;
    }
}
