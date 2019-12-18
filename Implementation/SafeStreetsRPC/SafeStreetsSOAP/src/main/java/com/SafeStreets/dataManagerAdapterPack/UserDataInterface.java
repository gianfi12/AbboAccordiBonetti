package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.exceptions.ImageStoreException;
import com.SafeStreets.exceptions.UserAlreadyPresentException;
import com.SafeStreets.model.User;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;

public interface UserDataInterface extends ClientDataInterface {
    void addUser(User info, String password) throws ImageStoreException, UserAlreadyPresentException;

    User getUser(String username, String password) throws WrongPasswordException, UserNotPresentException;
}
