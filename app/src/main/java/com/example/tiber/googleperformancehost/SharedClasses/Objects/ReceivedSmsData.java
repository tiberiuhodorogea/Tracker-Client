package com.example.tiber.googleperformancehost.SharedClasses.Objects;

/**
 * Created by tiber on 10/27/2016.
 */

//IMPORTANT - IMPLEMENT PRIORIY ON NOT DELETION IN SMALLDATASENDER
public class ReceivedSmsData extends Sendable {

    String number;
    String name;
    String message;

    public ReceivedSmsData(int date, String clientName, int clientId) {
        super(date, clientName,clientId);
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
