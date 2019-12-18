package com.SafeStreets.model;

import com.SafeStreets.modelEntities.CoordinateEntity;

import java.math.BigDecimal;

public class Coordinate {
    private Double latitude;
    private Double longitude;
    private Double altitude;

    public Coordinate(Double latitude, Double longitude, Double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public CoordinateEntity toCoordinateEntity() {
        CoordinateEntity coordinateEntity=new CoordinateEntity();
        coordinateEntity.setLatitude(new BigDecimal(latitude));
        coordinateEntity.setLongitude(new BigDecimal(longitude));
        coordinateEntity.setAltitude(new BigDecimal(altitude));

        return coordinateEntity;
    }
}
