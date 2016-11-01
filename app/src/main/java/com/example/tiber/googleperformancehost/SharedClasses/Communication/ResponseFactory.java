package com.example.tiber.googleperformancehost.SharedClasses.Communication;

import com.example.tiber.googleperformancehost.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.google.gson.Gson;

/**
 * Created by tiber on 4/15/2016.
 */
public class ResponseFactory extends MessageFactory{

    public Response create(RequestedAction requestedAction, Object data) throws KeyNotMappedException {
        return new Response(requestedAction,data);
    }

    public Response create(String jsonRequest){
        return new Gson().fromJson(jsonRequest, Response.class);
    }

}
