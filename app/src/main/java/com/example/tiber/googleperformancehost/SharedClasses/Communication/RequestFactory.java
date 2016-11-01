package com.example.tiber.googleperformancehost.SharedClasses.Communication;

import com.example.tiber.googleperformancehost.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.google.gson.Gson;

/**
 * Created by tiber on 4/15/2016.
 */
public class RequestFactory extends MessageFactory {

    public Request create(RequestedAction requestedAction,Object data) throws KeyNotMappedException {
        return new Request(requestedAction,data);
    }

    public Request create(String jsonRequest){
        return new Gson().fromJson(jsonRequest, Request.class);
    }
}
