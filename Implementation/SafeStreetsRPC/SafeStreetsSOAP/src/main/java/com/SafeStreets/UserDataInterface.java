package com.SafeStreets;

import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;

public interface UserDataInterface {
    void addUser(User info, String password);

    User getUser(String username, String password) throws WrongPasswordException, UserNotPresentException;
}
