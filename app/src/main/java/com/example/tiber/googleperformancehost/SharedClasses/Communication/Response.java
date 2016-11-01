package com.example.tiber.googleperformancehost.SharedClasses.Communication;


import java.lang.reflect.Type;

import com.example.tiber.googleperformancehost.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.google.gson.Gson;


/**
 * Created by tiber on 4/14/2016.
 */
public class Response extends AbstractMessage {

    protected Response(RequestedAction requestedAction, Object data) throws KeyNotMappedException {
        this.requestedAction = requestedAction;
        //Type typeOfData = ActionTypesHashMapper.getResponseDataClass(requestedAction);//get REQUEST data class
        this.jsonData = new Gson().toJson(data,data.getClass());
    }

    @Override
    public Object deserializeData() throws KeyNotMappedException {
        Type typeOfData = ActionTypesHashMapper.getResponseDataClass(requestedAction);//get REQUEST data class
        return new Gson().fromJson(this.jsonData,typeOfData);
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this,this.getClass());
    }
}
