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

import javax.ejb.Stateless;
import java.io.IOException;


/**
 * An implementation of {@link MapsServiceInterface} that uses Google Maps.
 *
 * @author Abbo Giulio A.
 * @see MapsServiceInterface
 */
@Stateless
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
     * This is not implemented due to issues with Google Maps API.
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaceInLocation(Place place, String location) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a Place built from the data in the GeocodingResult.
     *
     * @param result the result from the maps api
     * @return a place containing the data from the result
     */
    Place placeFrom(GeocodingResult result) {
        return new Place(
                getField(AddressComponentType.LOCALITY, result.addressComponents),
                getField(AddressComponentType.ROUTE, result.addressComponents),
                getField(AddressComponentType.STREET_NUMBER, result.addressComponents),
                coordinateFrom(result.geometry.location)
        );
    }

    /**
     * Returns a Coordinate from a given LatLng.
     *
     * @param latLng the data to put in the Coordinate
     * @return a Coordinate from a given LatLng
     */
    Coordinate coordinateFrom(LatLng latLng) {
        return new Coordinate(latLng.lat, latLng.lng, 0.0);
    }

    /**
     * Returns a LatLng from a given Coordinate.
     *
     * @param coordinate the data to put in the LatLng
     * @return a LatLng from a given Coordinate
     */
    LatLng latLngFrom(Coordinate coordinate) {
        return new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
    }

    /**
     * Given a list of {@linkplain AddressComponent}, returns the field
     * corresponding to a given {@linkplain AddressComponentType}.
     *
     * @param type       the type to look for
     * @param components the list of AddressComponents
     * @return the field corresponding to a given type, empty string if none found
     */
    String getField(AddressComponentType type, AddressComponent[] components) {
        for (AddressComponent c : components) {
            for (AddressComponentType t : c.types) {
                if (t == type) return c.longName;
            }
        }
        return "";
    }
}
