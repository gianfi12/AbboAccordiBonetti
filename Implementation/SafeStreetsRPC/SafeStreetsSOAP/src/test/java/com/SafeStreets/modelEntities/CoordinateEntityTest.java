package com.SafeStreets.modelEntities;

import com.SafeStreets.model.Coordinate;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * It tests the methods of CoordinateEntity
 */
public class CoordinateEntityTest {

    @Test
    public void toCoordinate() {
        CoordinateEntity coordinateEntity=new CoordinateEntity();
        coordinateEntity.setAltitude(new BigDecimal("2.4"));
        coordinateEntity.setLongitude(new BigDecimal("4.6"));
        coordinateEntity.setLatitude(new BigDecimal("7.8"));

        Coordinate coordinate=coordinateEntity.toCoordinate();
        assertTrue(2.4==coordinate.getAltitude());
        assertTrue(4.6==coordinate.getLongitude());
        assertTrue(7.8==coordinate.getLatitude());
    }

    @Test
    public void toCoordinateWithoutAltitude() {
        CoordinateEntity coordinateEntity=new CoordinateEntity();
        coordinateEntity.setAltitude(null);
        coordinateEntity.setLongitude(new BigDecimal("4.6"));
        coordinateEntity.setLatitude(new BigDecimal("7.8"));

        Coordinate coordinate=coordinateEntity.toCoordinate();
        assertNull(coordinate.getAltitude());
        assertTrue(4.6==coordinate.getLongitude());
        assertTrue(7.8==coordinate.getLatitude());
    }
}