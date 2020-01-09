package com.SafeStreets.elaborationManagerTest;

import com.SafeStreets.authorizationmanager.AuthorizationManagerInterface;
import com.SafeStreets.dataManagerAdapterPack.UserDataInterface;
import com.SafeStreets.elaborationmanager.ElaborationManagerInterface;
import com.SafeStreets.exceptions.ElaborationException;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;
import com.SafeStreets.mapsserviceadapter.FieldsException;
import com.SafeStreets.mapsserviceadapter.GeocodeException;
import com.SafeStreets.mapsserviceadapter.MapsServiceInterface;
import com.SafeStreets.model.*;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.ArrayList;

/**
 * This class test the method exposed by an instance of the Elaboration manager interface
 */
public class ElaborationManagerTest {
    ElaborationManagerInterface elaborationManager;
    MapsServiceInterface mapsService;
    UserDataInterface userData;

    @Before
    public void setUp() {
        elaborationManager = ElaborationManagerInterface.getInstance();
        mapsService = MapsServiceInterface.getInstance();
        userData = UserDataInterface.getUserDataInstance();
    }

    /**
     * This test checks that the report is correctly saved by the system
     */
    @Test
    public void testElaborate() throws WrongPasswordException, UserNotPresentException, ImageReadException, IOException, FieldsException, GeocodeException, ElaborationException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("image/document.jpg");
        File file = new File(url.getPath());
        elaborationManager.elaborate(new UserReport(OffsetDateTime.now(), OffsetDateTime.now(),mapsService.geocoding(new Coordinate(45.4645,9.1909,null)), ViolationType.INCIDENT_VEHICLES_VIOLATION,"",new Vehicle("ASDGH456"),userData.getUser("jak4","jak"), ImageIO.read(file),new ArrayList<>()));
    }
}
