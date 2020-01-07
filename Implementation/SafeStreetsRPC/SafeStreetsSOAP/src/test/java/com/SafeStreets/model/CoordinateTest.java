package com.SafeStreets.model;

import com.SafeStreets.modelEntities.CoordinateEntity;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void toCoordinateEntity() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, null);
        CoordinateEntity coordinateEntity=coordinate.toCoordinateEntity();
        assertEquals(new BigDecimal("10.2"), coordinateEntity.getLatitude());
        assertEquals(new BigDecimal("12.5"), coordinateEntity.getLongitude());
        assertNull(coordinateEntity.getAltitude());
    }

    @Test
    public void toCoordinateEntityWithAltitude() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, 100.3);
        CoordinateEntity coordinateEntity=coordinate.toCoordinateEntity();
        assertEquals(new BigDecimal("10.2"), coordinateEntity.getLatitude());
        assertEquals(new BigDecimal("12.5"), coordinateEntity.getLongitude());
        assertEquals(new BigDecimal("100.3"), coordinateEntity.getAltitude());
    }

    @Test
    public void isEqualTrueAltitudeNotNull() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, 100.3);
        Coordinate coordinate2=new Coordinate(10.2, 12.5, 100.3);
        assertTrue(coordinate.isEqual(coordinate2));
    }

    @Test
    public void isEqualTrueAltitudeNull() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, null);
        Coordinate coordinate2=new Coordinate(10.2, 12.5, null);
        assertTrue(coordinate.isEqual(coordinate2));
    }

    @Test
    public void isEqualFalseAltitudeNotNull() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, 100.3);
        Coordinate coordinate2=new Coordinate(10.2, 12.5, 100.2);
        assertFalse(coordinate.isEqual(coordinate2));
    }

    @Test
    public void isEqualFalseAltitudeNull() {
        Coordinate coordinate=new Coordinate(10.2, 12.5, null);
        Coordinate coordinate2=new Coordinate(10.2, 12.5, 100.2);
        assertFalse(coordinate.isEqual(coordinate2));
    }
}