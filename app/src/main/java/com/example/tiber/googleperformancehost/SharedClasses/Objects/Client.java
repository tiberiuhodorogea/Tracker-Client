package com.example.tiber.trackersupervisor.SharedClasses.Objects;

/**
 * Created by tiber on 11/4/2016.
 */

public class Client  {
    int id;
    String name;
    int  supervisorId;

    public Client(int id, String name, int supervisorId) {
        this.id = id;
        this.name = name;
        this.supervisorId = supervisorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }
}
