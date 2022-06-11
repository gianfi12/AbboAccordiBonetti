package com.SafeStreets.mapsserviceadapter;

/**
 * A {@link MapsServiceException} thrown when the underlying maps service
 * can not accomplish the given task.
 *
 * @author giubots
 */
public class GeocodeException extends MapsServiceException {
    /**
     * Constructs a geocode exceptions with the given message.
     *
     * @param message a description of the exception
     */
    GeocodeException(String message) {
        super(message);
    }

    /**
     * Constructs a geocode exception with the given message and cause.
     *
     * @param message a description of the exception
     * @param cause   the event that caused this exception
     */
    GeocodeException(String message, Throwable cause) {
        super(message, cause);
    }
}