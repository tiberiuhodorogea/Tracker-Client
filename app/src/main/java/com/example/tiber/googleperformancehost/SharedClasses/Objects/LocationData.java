package com.example.tiber.googleperformancehost.SharedClasses.Objects;

import com.example.tiber.googleperformancehost.SharedClasses.Utils.DateUtil;


/**
 * Created by tiber on 4/9/2016.
 */
public class LocationData extends Sendable {
    private double latitude;
    private double longitude;

    public LocationData(double latitude,double longitude, String clientName, int clientId){
        super(DateUtil.nowIntFormat(),clientName,clientId);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationData(int date, double latitude,double longitude, String clientName, int clientId){
        super(date,clientName,clientId);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationData(double latitude,double longitude, String clientName){
        super(DateUtil.nowIntFormat(),clientName,0);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationData(){
        super(DateUtil.nowIntFormat(),null,0);
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    @Override
    public String toString() {
        String ret = "Latitude = " + latitude +"\n";
        ret += "Longitude = "+ longitude +"\n";
        ret += "Date = " + DateUtil.fromIntFormat(date) +"\n";
        ret += "Name = " + this.clientName;
        return ret;
    }

}