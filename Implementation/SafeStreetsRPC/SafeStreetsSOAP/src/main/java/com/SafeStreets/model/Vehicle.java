package com.SafeStreets.model;

import com.SafeStreets.modelEntities.VehicleEntity;


/**
 * It represent a vehicle
 */
public class Vehicle {
    /**
     * License plate of the vehicle
     */
    private String licensePlate;

    /**
     * It creates a Vehicle with the given license plate
     * @param licensePlate new license plate
     */
    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    /**
     * It returns the license plate
     * @return license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * It returns the VehicleEntity converted from this element
     * @return VehicleEntity converted from this element
     */
    public VehicleEntity toVehicleEntity() {
        VehicleEntity vehicleEntity=new VehicleEntity();
        vehicleEntity.setLicensePlate(licensePlate);
        return vehicleEntity;
    }
}
