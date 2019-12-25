package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.exceptions.*;
import com.SafeStreets.model.User;

public interface UserDataInterface extends ClientDataInterface {
    void addUser(User info, String password) throws ImageStoreException, UserAlreadyPresentException;

    User getUser(String username, String password) throws WrongPasswordException, UserNotPresentException, ImageReadException;
}
