package com.SafeStreets;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;
import com.SafeStreets.model.User;
import org.junit.Before;
import org.junit.Test;

public class DataManagerAdapterTest {
    DataManagerAdapter dataManagerAdapter;

    @Before
    public void setUp() throws Exception {
        dataManagerAdapter = new DataManagerAdapter();
    }

    @Test
    public void testLogin() throws WrongPasswordException, UserNotPresentException, ImageReadException {
        User user =dataManagerAdapter.getUser("jak4", "jak");
        assert (user.getUsername()=="jak4");
    }

}
