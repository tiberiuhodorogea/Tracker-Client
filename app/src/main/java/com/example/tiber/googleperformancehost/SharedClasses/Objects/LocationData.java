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


    public LocationData(){
        super(DateUtil.nowIntFormat(),null,0);
    }
    public double getLatitude() {
        return latitude;
    }

    public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
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
    public String getclientName(){
    	return clientName;
    }
    public void setclientName(String clientName){
    	this.clientName = clientName;
    }
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
}
