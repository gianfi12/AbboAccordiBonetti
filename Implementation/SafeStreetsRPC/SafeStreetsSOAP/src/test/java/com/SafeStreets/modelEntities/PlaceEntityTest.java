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
     * It calls the method on a PlaceEntity to convert and verifies whether the resulted
     * Place has the same values of PlaceEntity, included the attribute coordinate.
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

    /**
     * It tests the method toPlace with a PlaceEntity without address and without house code.
     * It calls the method on a PlaceEntity to convert and verifies whether the resulted
     * Place has the same values of PlaceEntity, included the attribute coordinate.
     */
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