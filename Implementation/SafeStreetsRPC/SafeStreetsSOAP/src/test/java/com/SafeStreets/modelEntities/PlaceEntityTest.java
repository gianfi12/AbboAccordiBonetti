package com.SafeStreets.modelEntities;

import com.SafeStreets.model.Place;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * It tests the methods of PlaceEntity
 */
public class PlaceEntityTest {

    /**
     * It tests the method toPlace.
     * It gives to the method a CoordinateEntity to convert and verifies whether the resulted
     * Coordinate has the same values of CoordinateEntity
     */
    @Test
    public void toPlace() {
        PlaceEntity placeEntity=new PlaceEntity();
        placeEntity.setCity("Milano");
        placeEntity.setAddress("Piazza della Scala");
        placeEntity.setHouseCode("3");

        CoordinateEntity coordinateEntity=new CoordinateEntity();
        coordinateEntity.setAltitude(new BigDecimal("3.5"));
        coordinateEntity.setLongitude(new BigDecimal("4.6"));
        coordinateEntity.setLatitude(new BigDecimal("7.8"));

        placeEntity.setCoordinateEntity(coordinateEntity);
        placeEntity.setId(100);

        Place place=placeEntity.toPlace();

        assertEquals("Milano",place.getCity());
        assertEquals("Piazza della Scala",place.getAddress());
        assertEquals("3",place.getHouseCode());
        assertTrue(3.5==place.getCoordinate().getAltitude());
        assertTrue(4.6==place.getCoordinate().getLongitude());
        assertTrue(7.8==place.getCoordinate().getLatitude());

    }

    @Test
    public void toPlaceWithoutAddress() {
        PlaceEntity placeEntity=new PlaceEntity();
        placeEntity.setCity("Venezia");
        placeEntity.setId(5);

        Place place=placeEntity.toPlace();

        assertEquals("Venezia",place.getCity());
        assertNull(place.getAddress());
        assertNull(place.getHouseCode());
        assertNull(place.getCoordinate());

    }
}