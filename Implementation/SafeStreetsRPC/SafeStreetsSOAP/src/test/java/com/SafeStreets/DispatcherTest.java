package com.SafeStreets;

import com.SafeStreets.dataManagerAdapterPack.UserDataInterface;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;
import com.SafeStreets.model.AccessType;
import com.SafeStreets.model.Place;
import com.SafeStreets.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;

/**
 * This class contains the test of the Disptacher class
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

    /**
     * This method tests that a user can be correctly registered inside the system
     * @throws IOException Is thrown if an IO error occurs
     */
    @Test
    public void testUserRegistration() throws IOException {
        dispatcher =  new Dispatcher();
        URL url = Thread.currentThread().getContextClassLoader().getResource("image/document.jpg");
        File file = new File(url.getPath());
        url = Thread.currentThread().getContextClassLoader().getResource("image/user.jpg");
        File file2 = new File(url.getPath());
        User user = new User("jak4","jak","Giacomo", "Four", new Place("Milano","Via Lomellina","10",null),new Place("Milan","Piazza Leonardo Da Vinci","32",null), ImageIO.read(file2),ImageIO.read(file),"CSAD234JWEDSAK",LocalDate.of(2000,12,31));
        String info = user.toJSON();
        Boolean response = dispatcher.userRegistration(info,"pass");
        assert !response;
    }

}
