package com.SafeStreets.authorizationmanager;

import com.SafeStreets.model.AccessType;
import com.SafeStreets.model.Place;

/**
 * This interface represents the method exposed by the manager of the authorization, that verifies that access right of each request
 */
public interface AuthorizationManagerInterface {
    /**
     * This method returns the implementation of the Authorization Manager
     * @return Is the authorization Manager
     */
    static AuthorizationManagerInterface getInstance() {
        return new AuthorizationManager();
    }
    /**
     * This method return the access type relative to the given user
     * @param username Is the username of the user that the manager has to retrieve the access type
     * @param password Is the password of the user
     * @return Is the access type of the user
     */
    AccessType getAccessType(String username, String password);

    /**
     * This method return the jurisdiction area of the municipality
     * @param username Is the username of the municipality
     * @return Is the place of the municipality
     */
    Place getMunicipality(String username);

}
