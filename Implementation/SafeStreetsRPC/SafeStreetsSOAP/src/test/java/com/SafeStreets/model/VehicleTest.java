package com.SafeStreets.model;

import com.SafeStreets.modelEntities.VehicleEntity;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * It tests the methods of Vehicle
 */
public class VehicleTest {

    /**
     * It tests the method toVehicleEntity.
     * It calls the method on a Vehicle to convert and verifies whether the resulted
     * VehicleEntity has the same values (of the attributes) of Vehicle
     */
    @Test
    public void toVehicleEntity() {
        Vehicle vehicle=new Vehicle("ad546gh");
        VehicleEntity vehicleEntity=vehicle.toVehicleEntity();
        assertEquals("ad546gh", vehicleEntity.getLicensePlate());
    }
}