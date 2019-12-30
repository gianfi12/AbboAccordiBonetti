package com.SafeStreets.mapsserviceadapter;

import com.SafeStreets.model.Coordinate;
import com.SafeStreets.model.Place;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.io.IOException;


/**
 * An implementation of {@link MapsServiceInterface} that uses Google Maps.
 *
 * @author Abbo Giulio A.
 * @see MapsServiceInterface
 */
class MapsServiceAdapter implements MapsServiceInterface {
    /**
     * The context of the maps service: must be a singleton.
     */
    private static final GeoApiContext context = new GeoApiContext
            .Builder()
            .apiKey("AIzaSyD6-YCMxksVl0uOV52UD6NJr-RuaUpPqbo")
            .build();

    /**
     * The constructor is hidden outside the package.
     * Use {@link MapsServiceInterface#getInstance()}.
     */
    MapsServiceAdapter() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Place reverseGeocoding(Place place) throws GeocodeException, FieldsException {
        Coordinate coordinate = place.getCoordinate();
        if (coordinate == null) throw new FieldsException("Coordinate is null");
        return geocoding(coordinate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Place geocoding(String location) throws GeocodeException {
        try {
            return placeFrom(GeocodingApi.geocode(context, location).await()[0]);
        } catch (ApiException | InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            throw new GeocodeException("Trying to geocode location " + location, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Place geocoding(Coordinate coordinate) throws GeocodeException, FieldsException {
        if (coordinate.getLatitude() == null || coordinate.getLongitude() == null)
            throw new FieldsException("Not valid coordinate fields");
        try {
            return placeFrom(GeocodingApi
                    .reverseGeocode(context, latLngFrom(coordinate)).await()[0]);
        } catch (ApiException | InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            throw new GeocodeException("Trying to geocode coordinate " + coordinate, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaceInLocation(Place place, String location) throws GeocodeException, FieldsException {
        throw new UnsupportedOperationException();
/*        // Initial checks
        if (place.getCoordinate() == null) throw new FieldsException("Coordinate is null");
        if (place.getCoordinate().getLatitude() == null || place.getCoordinate().getLongitude() == null)
            throw new FieldsException("Not valid coordinate fields");

        // The name of the location recognized by google maps
        String locationName;
        // The name corresponding to the AddressComponentType of "location" in place
        String placeLocation;
        try {
            // Get locationName
            GeocodingResult locResult = GeocodingApi.geocode(context, location).await()[0];
            AddressComponentType locationType =
                    AddressComponentType.valueOf(locResult.types[0].name());
            locationName = getField(locationType, locResult.addressComponents);

            // Get place location
            GeocodingResult[] placeRes = GeocodingApi
                    .reverseGeocode(context, latLngFrom(place.getCoordinate()))
                    .await();
            placeLocation = getField(locationType,
                    placeRes[0].addressComponents);

            // Check if AddressComponentType of location is valid for place
            if (placeLocation == null)
                throw  new GeocodeException("Unable to " + "elaborate " + locationType);
        } catch (ApiException | InterruptedException | IOException | IllegalArgumentException e) {
            Thread.currentThread().interrupt();
            throw new GeocodeException("Trying to geocode " + location + " " + place, e);
        }

        // Check if the place is in the location
        return placeLocation.equals(locationName);*/
    }

    Place placeFrom(GeocodingResult result) {
        return new Place(
                getField(AddressComponentType.LOCALITY, result.addressComponents),
                getField(AddressComponentType.ROUTE, result.addressComponents),
                getField(AddressComponentType.STREET_NUMBER, result.addressComponents),
                coordinateFrom(result.geometry.location)
        );
    }

    Coordinate coordinateFrom(LatLng latLng) {
        return new Coordinate(latLng.lat, latLng.lng, 0.0);
    }

    LatLng latLngFrom(Coordinate coordinate) {
        return new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
    }

    String getField(AddressComponentType type, AddressComponent[] components) {
        for (AddressComponent c : components) {
            for (AddressComponentType t : c.types) {
                if (t == type) return c.longName;
            }
        }
        return "";
    }
}
