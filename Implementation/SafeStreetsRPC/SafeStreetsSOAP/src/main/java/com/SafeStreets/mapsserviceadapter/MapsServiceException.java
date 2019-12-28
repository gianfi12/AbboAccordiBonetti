package com.SafeStreets.mapsserviceadapter;

/**
 * A class that represents a generic exception thrown by this package.
 *
 * @author Abbo Giulio A.
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

    /**
     * A {@link MapsServiceException} signalling arguments with not valid
     * fields.
     * <p>
     * A method throws this if it was handed one or more arguments with some
     * invalid fields, for example a {@linkplain com.SafeStreets.model.Place}
     * without coordinates when coordinates were needed.
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

    /**
     * A {@link MapsServiceException} thrown when the underlying maps service
     * can not accomplish the given task.
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
}
