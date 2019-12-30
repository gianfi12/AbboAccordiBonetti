package com.SafeStreets.mapsserviceadapter;

import com.SafeStreets.model.Coordinate;
import com.SafeStreets.model.Place;

/**
 * This component gives access to the services provided by the Maps Service.
 * <p>
 * The Map Service allows to understand the geographical coordinates and to
 * manipulate the geographical data. Specifically, this allows to:<ul>
 * <li>translate between addresses as strings and their corresponding
 * coordinates;</li>
 * <li>determine whether a {@linkplain Place} is in a generic location.</li>
 * </ul>
 *
 * @author Abbo Giulio A.
 * @see Place
 * @see Coordinate
 */
public interface MapsServiceInterface {
    /**
     * Returns a new instance of a class that implements this interface.
     *
     * @return a new instance of a class that implements this interface
     */
    static MapsServiceInterface getInstance() {
        return new MapsServiceAdapter();
    }

    /**
     * Translates from coordinates to address string.
     * <p>
     * This will take the coordinates in the place, translate them to an
     * address and return a new object with the complete data.
     *
     * @param place the coordinates to reverse geocode
     * @return a new object with the coordinates and the corresponding address
     * @throws FieldsException  if there are no valid coordinates in the provided place or there are already some translated fields
     * @throws GeocodeException if the reverse geocode is not successful
     */
    Place reverseGeocoding(Place place) throws FieldsException, GeocodeException;

    /**
     * Translates from an address string to coordinates.
     * <p>
     * This will translate the provided address into coordinates and return a
     * place containing both the address and the coordinates.
     *
     * @param location the address to geocode
     * @return a place with the address and the corresponding coordinates
     * @throws GeocodeException if the geocode is not successful
     */
    Place geocoding(String location) throws GeocodeException;

    /**
     * Translates from coordinates to address string.
     * <p>
     * This will translate the provided coordinates to an address and return a
     * place containing both the translation and the coordinates.
     *
     * @param coordinates the coordinates to reverse geocode
     * @return a place with the coordinates and the corresponding address
     * @throws FieldsException  if the provided coordinates has not valid fields
     * @throws GeocodeException if the geocode is not successful
     */
    Place geocoding(Coordinate coordinates) throws FieldsException, GeocodeException;

    /**
     * Returns whether the provided place is in a generic location, based on
     * its coordinates.
     * <p>
     * For example: <pre>{@code
     * Place place = new Place();
     * place.setCoordinate(new Coordinate(0.0, 0.0, 0.0));
     * isPlaceInLocation(place, "Milan");
     * }</pre>
     * will return {@code false}.
     *
     * @param place    the place to check
     * @param location the common name of a location
     * @return whether the place is inside a location with the given name
     * @throws FieldsException  if place fields are not valid
     * @throws GeocodeException if the location was not recognized
     */
    boolean isPlaceInLocation(Place place, String location) throws FieldsException, GeocodeException;
}