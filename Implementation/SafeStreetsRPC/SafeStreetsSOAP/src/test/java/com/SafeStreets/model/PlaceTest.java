package com.SafeStreets.model;

import com.SafeStreets.modelEntities.PlaceEntity;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * It tests the methods of Place
 */
public class PlaceTest {

    /**
     * It tests the method toPlaceEntity
     * It calls the method on a Place to convert and verifies whether the resulted
     * PlaceEntity has the same values of Place
     */
    @Test
    public void toPlaceEntity() {
        Coordinate coordinate=new Coordinate(3.4, 4.5, 5.6);
        Place place=new Place("Milano", "Piazza della Scala", "4", coordinate);
        PlaceEntity placeEntity=place.toPlaceEntity();

        assertEquals("Milano",placeEntity.getCity());
        assertEquals("Piazza della Scala",placeEntity.getAddress());
        assertEquals("4",placeEntity.getHouseCode());
        assertEquals(new BigDecimal("3.4"), placeEntity.getCoordinateEntity().getLatitude());
        assertEquals(new BigDecimal("4.5"), placeEntity.getCoordinateEntity().getLongitude());
        assertEquals(new BigDecimal("5.6"), placeEntity.getCoordinateEntity().getAltitude());
    }

    /**
     * It tests the method toPlaceEntity
     * It calls the method on a Place with address null to convert and verifies whether the resulted
     * PlaceEntity has the same values of Place
     */
    @Test
    public void toPlaceEntityWithAddressNull() {
        Place place=new Place("Milano", null, null, null);
        PlaceEntity placeEntity=place.toPlaceEntity();

        assertEquals("Milano",placeEntity.getCity());
        assertNull(placeEntity.getAddress());
        assertNull(placeEntity.getHouseCode());
        assertNull(placeEntity.getCoordinateEntity());
    }

    /**
     * It tests the method isEqual.
     * It calls the method on two equal Place and it verifies whether they are equal.
     */
    @Test
    public void isEqualTrue() {
        Coordinate coordinate=new Coordinate(3.4, 4.5, 5.6);
        Place place=new Place("Milano", "Piazza della Scala", "4", coordinate);

        Coordinate coordinate2=new Coordinate(3.4, 4.5, 5.6);
        Place place2=new Place("Milano", "Piazza della Scala", "4", coordinate2);

        assertTrue(place.isEqual(place2));
    }

    /**
     * It tests the method isEqual.
     * It calls the method on two equal Place with address null and it verifies whether they are equal.
     */
    @Test
    public void isEqualTrueWithAddressNull() {
        Place place=new Place("Milano", null, null, null);

        Place place2=new Place("Milano", null, null, null);

        assertTrue(place.isEqual(place2));
    }

    /**
     * It tests the method isEqual.
     * It calls the method on two different Place and it verifies whether they are not equal.
     */
    @Test
    public void isEqualFalse() {
        Coordinate coordinate=new Coordinate(3.4, 4.5, 5.7);
        Place place=new Place("Milano", "Piazza della Scala", "4", coordinate);

        Coordinate coordinate2=new Coordinate(3.4, 4.5, 5.6);
        Place place2=new Place("Milano", "Piazza della Scala", "4", coordinate2);

        assertFalse(place.isEqual(place2));
    }

    /**
     * It tests the method isEqual.
     * It calls the method on two different Place with address null and it verifies whether they are not equal.
     */
    @Test
    public void isEqualFalseWithAddressNull() {
        Place place=new Place("Milano", null, null, null);

        Place place2=new Place("Milano2", null, null, null);

        assertFalse(place.isEqual(place2));
    }
}