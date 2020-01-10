package com.SafeStreets.modelEntities;

import com.SafeStreets.model.Vehicle;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * It tests the methods of VehicleEntity
 */
public class VehicleEntityTest {

    /**
     * It tests the method toVehicle.
     * It calls the method on a VehicleEntity to convert and verifies whether the resulted
     * Vehicle has the same values of VehicleEntity.
     */
    @Test
    public void toVehicle() {
        VehicleEntity vehicleEntity=new VehicleEntity();
        vehicleEntity.setLicensePlate("ad546fw");
        Vehicle vehicle=vehicleEntity.toVehicle();
        assertEquals("ad546fw", vehicle.getLicensePlate());

    }
}