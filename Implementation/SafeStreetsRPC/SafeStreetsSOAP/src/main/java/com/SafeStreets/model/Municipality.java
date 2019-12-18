package com.SafeStreets.model;


public class Municipality {
    private String name;
    private Place place;

    public Municipality(String name, Place place) {
        this.name = name;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public Place getPlace() {
        return place;
    }
}
