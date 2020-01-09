package com.SafeStreets.authorizationPack;

import com.SafeStreets.authorizationmanager.AuthorizationManagerInterface;
import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.model.AccessType;
import com.SafeStreets.model.Place;
import org.junit.Before;
import org.junit.Test;

/**
 * This class test the method exposed by an instance of the Authorization Manager Interface
 */
public class AuthorizationManagerTest {
    AuthorizationManagerInterface authorizationManager;

    @Before
    public void setUp() {
        authorizationManager = AuthorizationManagerInterface.getInstance();
    }

    /**
     * This test checks that a not registered user cannot be authenticate by the system
     */
    @Test
    public void testNotRegistered() {
        AccessType accessType = authorizationManager.getAccessType("not_a_user","not_a_password");
        assert(accessType==AccessType.NOT_REGISTERED);
    }

    /**
     * This test checks that a registered user can be correctly authenticate by the system
     */
    @Test
    public void testUser() {
        AccessType accessType = authorizationManager.getAccessType("jak4","jak");
        assert(accessType==AccessType.USER);
    }

    /**
     * This test checks that a registered user with a wrong password cannot be correctly authenticate by the system
     */
    @Test
    public void testUserWrong() {
        AccessType accessType = authorizationManager.getAccessType("jak4","not_a_pass");
        assert(accessType==AccessType.NOT_REGISTERED);
    }

    /**
     * This test checks that a registered municipality can be correctly authenticate by the system
     */
    @Test
    public void testMunicipality() {
        AccessType accessType = authorizationManager.getAccessType("Milano","Milan");
        assert(accessType==AccessType.MUNICIPALITY);
    }

    /**
     * This test checks that we cannot get the competence area of a not registered municipality
     * @throws MunicipalityNotPresentException Is thrown if the municipality is not present
     */
    @Test(expected = MunicipalityNotPresentException.class)
    public void testGetPlace() throws MunicipalityNotPresentException{
        Place place = authorizationManager.getMunicipality("Berlin");
    }



}
