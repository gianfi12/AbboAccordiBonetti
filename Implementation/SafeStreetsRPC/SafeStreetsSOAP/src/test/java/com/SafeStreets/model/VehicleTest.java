package com.SafeStreets.model;

import com.SafeStreets.modelEntities.VehicleEntity;
import org.junit.Test;

import static org.junit.Assert.*;

public class VehicleTest {

    @Test
    public void toVehicleEntity() {
        Vehicle vehicle=new Vehicle("ad546gh");
        VehicleEntity vehicleEntity=vehicle.toVehicleEntity();
        assertEquals("ad546gh", vehicleEntity.getLicensePlate());
    }
}