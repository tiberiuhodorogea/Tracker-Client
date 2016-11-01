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
}
