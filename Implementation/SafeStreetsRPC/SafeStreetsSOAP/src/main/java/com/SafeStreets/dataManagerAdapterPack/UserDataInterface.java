package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.exceptions.*;
import com.SafeStreets.model.User;

/**
 * Interface of the DataManager component. It allows to manage the users in the database.
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

    /**
     * It saves the given user in the database with the given password.
     * @param info user to save
     * @param password password of the given user
     * @throws ImageStoreException if there was a problem during the store of the images of the user
     * @throws UserAlreadyPresentException if there exists already a user with the same username of the given one
     */
    void addUser(User info, String password) throws ImageStoreException, UserAlreadyPresentException;

    /**
     * It returns the user with the given username, but only if the given password is correct.
     * @param username username of the user to return
     * @param password of the user
     * @return user with the given username, but only if the given password is correct.
     * @throws WrongPasswordException if the given password is wrong
     * @throws UserNotPresentException if the given username is not present in the database
     * @throws ImageReadException if there was a problem during the read of the images of the user
     */
    User getUser(String username, String password) throws WrongPasswordException, UserNotPresentException, ImageReadException;
}
