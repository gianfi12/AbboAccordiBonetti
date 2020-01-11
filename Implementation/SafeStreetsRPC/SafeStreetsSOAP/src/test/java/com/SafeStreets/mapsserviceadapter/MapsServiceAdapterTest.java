package com.SafeStreets.mapsserviceadapter;

import com.SafeStreets.model.Coordinate;
import com.SafeStreets.model.Place;
import com.google.maps.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * It tests the methods of MapsServiceAdapter.
 * Note: this does not test the correctness of the results nor the
 * interaction with the maps service.
 */
class MapsServiceAdapterTest {

    /**
     * Given a list of address components, this tests that the method
     * extracts the correct field given a field type.
     */
    @Test
    void getField() {
        AddressComponent[] components = {
                new AddressComponent(),
                new AddressComponent(),
        };
        components[0].types = new AddressComponentType[]{
                AddressComponentType.STREET_NUMBER
        };
        components[1].types = new AddressComponentType[]{
                AddressComponentType.ROUTE,
                AddressComponentType.LOCALITY
        };
        components[1].longName = "test";

        assertEquals(components[1].longName,
                new MapsServiceAdapter().getField(AddressComponentType.LOCALITY, components));
    }

    /**
     * this tests the translation between Coordinate and LatLng.
     */
    @Test
    void latLngFrom() {
        Coordinate coordinate = new Coordinate(1.0, 2.1, 3.3);
        LatLng latLng = new MapsServiceAdapter().latLngFrom(coordinate);

        assertEquals(coordinate.getLatitude(), latLng.lat);
        assertEquals(coordinate.getLongitude(), latLng.lng);
    }

    /**
     * this tests the translation between LatLng and Coordinate.
     */
    @Test
    void coordinateFrom() {
        LatLng latLng = new LatLng(1.0, 3.01);
        Coordinate coordinate = new MapsServiceAdapter().coordinateFrom(latLng);

        assertEquals(latLng.lat, coordinate.getLatitude());
        assertEquals(latLng.lng, coordinate.getLongitude());
    }

    /**
     * Tests the translation from GeocodingResult to Place.
     */
    @Test
    void placeFrom() {
        GeocodingResult result = new GeocodingResult();
        result.addressComponents = new AddressComponent[]{
                new AddressComponent(),
                new AddressComponent(),
                new AddressComponent(),
        };
        result.addressComponents[0].types = new AddressComponentType[]{AddressComponentType.STREET_NUMBER};
        result.addressComponents[0].longName = "number";
        result.addressComponents[1].types = new AddressComponentType[]{AddressComponentType.LOCALITY};
        result.addressComponents[1].longName = "local";
        result.addressComponents[2].types = new AddressComponentType[]{AddressComponentType.ROUTE};
        result.addressComponents[2].longName = "my route";
        result.geometry = new Geometry();
        result.geometry.location = new LatLng(1.22222, 323.12);
        Place place = new MapsServiceAdapter().placeFrom(result);

        assertEquals(result.addressComponents[0].longName, place.getHouseCode());
        assertEquals(result.addressComponents[1].longName, place.getCity());
        assertEquals(result.addressComponents[2].longName, place.getAddress());
        assertEquals(result.geometry.location.lat, place.getCoordinate().getLatitude());
        assertEquals(result.geometry.location.lng, place.getCoordinate().getLongitude());
    }
}