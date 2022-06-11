package com.SafeStreets.mapsserviceadapter;

/**
 * A class that represents a generic exception thrown by this package.
 *
 * @author giubots
 */
public abstract class MapsServiceException extends Exception {

    /**
     * Constructs an exceptions with the given message.
     *
     * @param message a description of the exception
     */
    MapsServiceException(String message) {
        super(message);
    }

    /**
     * Constructs an exception with the given message and cause.
     *
     * @param message a description of the exception
     * @param cause   the event that caused this exception
     */
    MapsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
