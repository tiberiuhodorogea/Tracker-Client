package com.example.tiber.googleperformancehost.SharedClasses.Objects;

/**
 * Created by tiber on 10/27/2016.
 */

//IMPORTANT - IMPLEMENT PRIORIY ON NOT DELETION IN SMALLDATASENDER
public class SmsData extends Sendable {

    public SmsData(int date, String clientName, int clientId) {
        super(date, clientName, clientId);
    }
}
