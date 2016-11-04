package com.example.tiber.trackersupervisor.SharedClasses.Objects;

/**
 * Created by tiber on 11/4/2016.
 */

public class Supervisor {
    int id;
    String name;

    public Supervisor(int id, String name) {
        this.id = id;
        this.name = name;
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
}
