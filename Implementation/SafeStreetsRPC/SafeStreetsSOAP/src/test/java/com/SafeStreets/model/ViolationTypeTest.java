package com.SafeStreets.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * It tests the methods of ViolationType
 */
public class ViolationTypeTest {

    /**
     * It tests the method getViolationTypeReportableFromUser.
     * It verifes whether the method returns all and only all the parking violation type.
     */
    @Test
    public void getViolationTypeReportableFromUser() {
        List<ViolationType> violationTypeList= ViolationType.getViolationTypeReportableFromUser();
        assertEquals(8, violationTypeList.size());
        assertTrue(violationTypeList.contains(ViolationType.PARKING_ON_BIKE_LANES));
        assertTrue(violationTypeList.contains(ViolationType.PARKING_ON_RESERVED_STALL));
        assertTrue(violationTypeList.contains(ViolationType.DOUBLE_PARKING));
        assertTrue(violationTypeList.contains(ViolationType.PARKING_ON_PEDESTRIAN_CROSSING));
        assertTrue(violationTypeList.contains(ViolationType.PARKING_ON_SIDEWALK));
        assertTrue(violationTypeList.contains(ViolationType.PARKING_ON_TRAFFIC_ISLAND));
        assertTrue(violationTypeList.contains(ViolationType.PARKING_NOT_PAYED));
        assertTrue(violationTypeList.contains(ViolationType.PARKING_ON_RED_ZONE));

        for(ViolationType violationType : violationTypeList) {
            assertTrue(violationType.canBeReportedFromUser());
        }

    }
}