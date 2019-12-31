package com.SafeStreets.identityverifier;


import com.SafeStreets.model.User;

/**
 * This interface defines the methods that the Identity verifier needs to expose to the system
 */
public interface IdentityVerifierInterface {
    /**
     * This method is used to get an instance of the Identity Verifier
     * @return Is an instance of the Identity Verifier
     */
    static IdentityVerifierInterface getInstance() {
        return new IdentityVerifierAdapter();
    }

    /**
     * This method is used to verify the identity of the user, based on the credentials the user has provided to the system it checks if the person really exists
     * @param info Is an instance of User with all the useful information
     * @return Is a boolean that indicates if the user has been correctly identified
     */
    boolean verify(User info);
}
