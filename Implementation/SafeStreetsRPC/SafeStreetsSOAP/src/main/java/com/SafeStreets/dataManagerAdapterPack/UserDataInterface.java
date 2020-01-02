package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.exceptions.*;
import com.SafeStreets.model.User;

/**
 *
 * @author Massimiliano Bonetti
 */
public interface UserDataInterface extends ClientDataInterface {

    /**
     * Returns a new instance of a class that implements this interface.
     *
     * @return a new instance of a class that implements this interface
     */
    static UserDataInterface getUserDataInstance() {
        return new DataManagerAdapter();
    }

    void addUser(User info, String password) throws ImageStoreException, UserAlreadyPresentException;

    User getUser(String username, String password) throws WrongPasswordException, UserNotPresentException, ImageReadException;
}
