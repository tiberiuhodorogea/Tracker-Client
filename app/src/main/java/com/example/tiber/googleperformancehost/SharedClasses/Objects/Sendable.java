package com.example.tiber.googleperformancehost.SharedClasses.Objects;

/**
 * Created by tiber on 10/25/2016.
 */

public abstract class Sendable {
    int date;
    int clientId;
    String clientName;

    public Sendable(int date, String clientName, int clientId) {
        this.date = date;
        this.clientName = clientName;
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getclientName(){
        return clientName;
    }

    public void setclientName(String clientName){
        this.clientName = clientName;
    }

    public int getDate() {
        return date;
    }
    public void setDate(int date) {
        this.date = date;
    }
}