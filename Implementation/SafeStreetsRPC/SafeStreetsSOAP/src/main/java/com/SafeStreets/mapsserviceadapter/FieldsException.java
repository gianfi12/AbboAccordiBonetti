package com.SafeStreets.mapsserviceadapter;

/**
 * A {@link MapsServiceException} signalling arguments with not valid
 * fields.
 * <p>
 * A method throws this if it was handed one or more arguments with some
 * invalid fields, for example a {@linkplain com.SafeStreets.model.Place}
 * without coordinates when coordinates were needed.
 *
 * @author giubots
 */
public class FieldsException extends MapsServiceException {
    /**
     * Constructs a fields exceptions with the given message.
     *
     * @param message a description of the exception
     */
    FieldsException(String message) {
        super(message);
    }

    /**
     * Constructs a fields exception with the given message and cause.
     *
     * @param message a description of the exception
     * @param cause   the event that caused this exception
     */
    FieldsException(String message, Throwable cause) {
        super(message, cause);
    }
}
