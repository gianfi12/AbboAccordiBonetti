package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.model.User;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;

public interface UserDataInterface extends ClientDataInterface {
    void addUser(User info, String password);

    User getUser(String username, String password) throws WrongPasswordException, UserNotPresentException;
}
