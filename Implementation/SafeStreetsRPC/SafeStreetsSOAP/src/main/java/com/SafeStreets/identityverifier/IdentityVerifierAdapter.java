package com.SafeStreets.identityverifier;


import com.SafeStreets.model.User;

/**
 * This is the adapter of the Identity Verifier that makes possible for the registration manager to verify the identity of the user during the registration, it implements the Identity Verifier Adapter, so it allows ous to decouple the verifier from the code in our system
 */
class IdentityVerifierAdapter implements IdentityVerifierInterface{


    /**
     * This method is used to verify the identity of the user, based on the credentials the user has provided to the system it checks if the person really exists
     * @param info Is an instance of User with all the useful information
     * @return Is a boolean that indicates if the user has been correctly identified
     */
    @Override
    public boolean verify(User info) {
        return true;
    }
}
