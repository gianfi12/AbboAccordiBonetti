package com.SafeStreets.model;

import com.SafeStreets.modelEntities.CoordinateEntity;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * It tests the methods of Coordinate
 */
public class CoordinateTest {
    /**
     * It tests the method toCoordinateEntity
     * It calls the method on a Coordinate with altitude null to convert and verifies whether the resulted
     * CoordinateEntity has the same values (of the attributes) of Coordinate
     */
    @Test
    public void toCoordinateEntity() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, null);
        CoordinateEntity coordinateEntity=coordinate.toCoordinateEntity();
        assertEquals(new BigDecimal("10.2"), coordinateEntity.getLatitude());
        assertEquals(new BigDecimal("12.5"), coordinateEntity.getLongitude());
        assertNull(coordinateEntity.getAltitude());
    }
    /**
     * It tests the method toCoordinateEntity
     * It calls the method on a Coordinate with altitude to convert and verifies whether the resulted
     * CoordinateEntity has the same values of Coordinate
     */
    @Test
    public void toCoordinateEntityWithAltitude() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, 100.3);
        CoordinateEntity coordinateEntity=coordinate.toCoordinateEntity();
        assertEquals(new BigDecimal("10.2"), coordinateEntity.getLatitude());
        assertEquals(new BigDecimal("12.5"), coordinateEntity.getLongitude());
        assertEquals(new BigDecimal("100.3"), coordinateEntity.getAltitude());
    }

    /**
     * It tests the method isEqual.
     * It calls the method on two equal Coordinate and it verifies whether they are equal.
     */
    @Test
    public void isEqualTrueAltitudeNotNull() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, 100.3);
        Coordinate coordinate2=new Coordinate(10.2, 12.5, 100.3);
        assertTrue(coordinate.isEqual(coordinate2));
    }

    /**
     * It tests the method isEqual.
     * It calls the method on two equal Coordinate with altitude null and it verifies whether they are equal.
     */
    @Test
    public void isEqualTrueAltitudeNull() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, null);
        Coordinate coordinate2=new Coordinate(10.2, 12.5, null);
        assertTrue(coordinate.isEqual(coordinate2));
    }

    /**
     * It tests the method isEqual.
     * It calls the method on two different Coordinate and it verifies whether they are not equal.
     */
    @Test
    public void isEqualFalseAltitudeNotNull() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, 100.3);
        Coordinate coordinate2=new Coordinate(10.2, 12.5, 100.2);
        assertFalse(coordinate.isEqual(coordinate2));
    }

    /**
     * It tests the method isEqual.
     * It calls the method on two different Coordinate with altitude null and it verifies whether they are notequal.
     */
    @Test
    public void isEqualFalseAltitudeNull() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, null);
        Coordinate coordinate2=new Coordinate(10.2, 12.5, 100.2);
        assertFalse(coordinate.isEqual(coordinate2));
    }
}