package com.SafeStreets.model;

import com.SafeStreets.modelEntities.VehicleEntity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehicle {

    @Id
    private String licensePlate;

    public Vehicle() {

    }

    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleEntity toVehicleEntity() {
        VehicleEntity vehicleEntity=new VehicleEntity();
        vehicleEntity.setLicensePlate(licensePlate);
        return vehicleEntity;
    }
}
