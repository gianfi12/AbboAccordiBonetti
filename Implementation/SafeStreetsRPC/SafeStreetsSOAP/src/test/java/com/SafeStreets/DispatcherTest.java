package com.SafeStreets;

import com.SafeStreets.dataManagerAdapterPack.UserDataInterface;
import com.SafeStreets.model.AccessType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;


/**
 * This class contains the test of the Dispatcher class, the rest of the test are presents on the client test
 */
public class DispatcherTest {
    /**
     * It contains an instance of the user data interface that is used in order to retrieve the information about the user
     */
    UserDataInterface userDataInterface;
    Dispatcher dispatcher;
    final static Gson gson = new Gson();


    @Before
    public void setUp() {
        userDataInterface = UserDataInterface.getUserDataInstance();
    }

    /**
     * This method test the login method
     */
    @Test
    public void testLogin()  {
        dispatcher =  new Dispatcher();
        String response = dispatcher.login("jak4", "jak");
        Type type = new TypeToken<AccessType>(){}.getType();
        AccessType accessType = gson.fromJson(response,type);
        assert (accessType==AccessType.USER);

        response = dispatcher.login("notAUser", "jak");
        type = new TypeToken<AccessType>(){}.getType();
        accessType = gson.fromJson(response,type);
        assert (accessType==AccessType.NOT_REGISTERED);
    }



}
