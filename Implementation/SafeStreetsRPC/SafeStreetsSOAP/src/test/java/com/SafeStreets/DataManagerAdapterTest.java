package com.SafeStreets;

import com.SafeStreets.dataManagerAdapterPack.UserDataInterface;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;
import com.SafeStreets.model.User;
import org.junit.Before;
import org.junit.Test;

public class DataManagerAdapterTest {
    UserDataInterface userDataInterface;

    @Before
    public void setUp() throws Exception {
        userDataInterface = UserDataInterface.getUserDataInstance();
    }

    @Test
    public void testLogin() throws WrongPasswordException, UserNotPresentException, ImageReadException {
        User user =userDataInterface.getUser("jak4", "jak");
        assert (user.getUsername()=="jak4");
    }

}
