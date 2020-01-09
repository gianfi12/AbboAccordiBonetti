package com.SafeStreets.model;

/**
 * The enumeration describe the different access type that an actor can have in the system after it has performed the authentication
 */
public enum AccessType {
    /**
     * This is the access type get by an actor when it has been recognized as a user
     */
    USER,
    /**
     * This is the access type get by an actor when it has been recognized as a municipality
     */
    MUNICIPALITY,
    /**
     * This is the access type get by an actor when it has not bee already recognized
     */
    NOT_REGISTERED;
}
