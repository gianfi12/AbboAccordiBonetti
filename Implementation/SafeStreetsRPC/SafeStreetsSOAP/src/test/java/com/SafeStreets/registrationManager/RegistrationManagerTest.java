package com.SafeStreets.registrationManager;

import com.SafeStreets.dataManagerAdapterPack.UserDataInterface;
import com.SafeStreets.exceptions.*;
import com.SafeStreets.mapsserviceadapter.GeocodeException;
import com.SafeStreets.mapsserviceadapter.MapsServiceInterface;
import com.SafeStreets.model.Place;
import com.SafeStreets.model.User;
import com.SafeStreets.registrationmanager.RegistrationManagerInterface;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

/**
 * This class contains the test of the registration manager
 */
public class RegistrationManagerTest {
    RegistrationManagerInterface registrationManager;
    MapsServiceInterface mapsService;

    @Before
    public void setUp() {
        registrationManager = RegistrationManagerInterface.getInstance();
        mapsService = MapsServiceInterface.getInstance();
    }

    /**
     * This method checks that the user can a new user can correctly start a registration
     */
    @Test
    public void testStartRegistrationUser() throws GeocodeException, IOException, UserAlreadyPresentException {
        Place place = mapsService.geocoding("Milan");
        URL url = Thread.currentThread().getContextClassLoader().getResource("image/document.jpg");
        File file = new File(url.getPath());
        url = Thread.currentThread().getContextClassLoader().getResource("image/user.jpg");
        File file2 = new File(url.getPath());
        registrationManager.startUserRegistration(new User("new_user","email@mail.com","New","User",place,place, ImageIO.read(file2),ImageIO.read(file),"AAD534BSD6ASD",LocalDate.of(1990,12,31)));
    }

    /**
     * This method checks that an exception is thrown if a user tries to register himself two times
     * @throws UserAlreadyPresentException Is thrown if the user is already registered
     */
    @Test(expected = UserAlreadyPresentException.class)
    public void testStartRegistrationUserPresent() throws GeocodeException, IOException, UserAlreadyPresentException {
        Place place = mapsService.geocoding("Milan");
        URL url = Thread.currentThread().getContextClassLoader().getResource("image/document.jpg");
        File file = new File(url.getPath());
        url = Thread.currentThread().getContextClassLoader().getResource("image/user.jpg");
        File file2 = new File(url.getPath());
        registrationManager.startUserRegistration(new User("jak4","email@mail.com","New","User",place,place, ImageIO.read(file2),ImageIO.read(file),"AAD534BSD6ASD",LocalDate.of(1990,12,31)));
    }

    /**
     * This method checks that a user can be correctly registered inside the system
     */
    @Test
    public void testFinishUserRegistration() throws GeocodeException, IOException, UserAlreadyPresentException, ImageStoreException {
        Place place = mapsService.geocoding("Milan");
        URL url = Thread.currentThread().getContextClassLoader().getResource("image/document.jpg");
        File file = new File(url.getPath());
        url = Thread.currentThread().getContextClassLoader().getResource("image/user.jpg");
        File file2 = new File(url.getPath());
        registrationManager.finishUserRegistration(new User("newUser","email@mail.com","New","User",place,place, ImageIO.read(file2),ImageIO.read(file),"AAD534BSD6ASD",LocalDate.of(1990,12,31)),"not_a_pass");
    }

    /**
     * This test checks that a Municipality can be correctly registered inside the system
     */
    @Test
    public void testMunicipalityRegistration() throws PlaceForMunicipalityNotPresentException, MunicipalityNotPresentException, MunicipalityAlreadyPresentException {
        registrationManager.municipalityRegistration("14","Verona","password");
    }

    /**
     * This method checks that a municipality that tries to register with an already used contract code cannot register himself inside the system
     * @throws MunicipalityAlreadyPresentException Is thrown if a municipality with the same contract code has been found
     */
    @Test(expected = MunicipalityAlreadyPresentException.class)
    public void testMunicipalityRegistrationUsedCode() throws PlaceForMunicipalityNotPresentException, MunicipalityNotPresentException, MunicipalityAlreadyPresentException {
        registrationManager.municipalityRegistration("5186b6b20e77f0ee6254dfa8d7e06dd33d3138160b77d5338c279d0ff6f48d0c432e1a7dea6aa241c6de109763090be603029b1b33226ea30c4b165945605002","Verona","password");
    }

    /**
     * This method checks that no municipality can be registered inside the system that has provide a wrong contract code
     * @throws MunicipalityNotPresentException Is thrown because the requested contract code has not been found
     */
    @Test(expected = MunicipalityNotPresentException.class)
    public void testMunicipalityRegistrationWrongCode() throws PlaceForMunicipalityNotPresentException, MunicipalityNotPresentException, MunicipalityAlreadyPresentException {
        registrationManager.municipalityRegistration("0","Verona","password");
    }





}
