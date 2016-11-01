package com.example.tiber.googleperformancehost.Classes;

import com.example.tiber.googleperformancehost.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.googleperformancehost.SharedClasses.Communication.RequestedAction;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.LocationData;

import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.Observable;

import static com.example.tiber.googleperformancehost.SharedClasses.Communication.RequestedAction.*;

/**
 * Created by tiber on 10/26/2016.
 */

public  class RequestDataTypeRequestedActionMapper {

    private static Hashtable<Type,RequestedAction> actionDataTypeMapper =
            new Hashtable<Type,RequestedAction>();
    static{
        //maps RequestedAction ( enum value ) to the request's data type
        // I know that this limits one type to always be on one requestedAction but it will do for now...
        //CAREFUL NOT TO MAP SAME REQUESTED ACTION MULTIPLE TIMES
        actionDataTypeMapper.put(LocationData.class,GIVE_LOCATION);
    }
    public static RequestedAction getDataTypeForRequestedAction(Type key) throws KeyNotMappedException {
        RequestedAction requestedAction = actionDataTypeMapper.get(key);
        if(null == requestedAction)
            throw new KeyNotMappedException("RequestedAction key : " + key.toString() + " not mapped to Type");

        return requestedAction;
    }
}
