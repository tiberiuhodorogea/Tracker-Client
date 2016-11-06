package com.example.tiber.googleperformancehost.SharedClasses.Objects;

/**
 * Created by tiber on 11/6/2016.
 */

public class SentSmsData extends Sendable {

    String number;
    String name;
    String message;

    public SentSmsData(int date, String clientName, int clientId) {
        super(date, clientName, clientId);
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
