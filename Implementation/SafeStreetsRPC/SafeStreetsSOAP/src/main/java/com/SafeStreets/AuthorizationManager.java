package com.SafeStreets;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;
import com.SafeStreets.model.AccessType;
import com.SafeStreets.model.Place;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is an implementation of the authorization manager, that is responsible of verify the access rights of all the requests
 */
public class AuthorizationManager implements AuthorizationManagerInterface{
    /**
     * This is the logger of the class
     */
    private final static Logger LOGGER = Logger.getLogger(AuthorizationManager.class.getName());

    /**
     * This method return the access type relative to the given user
     * @param username Is the username of the user that the manager has to retrieve the access type
     * @param password Is the password of the user
     * @return Is the access type of the user
     */
    @Override
    public AccessType getAccessType(String username, String password){
        DataManagerAdapter dataManagerAdapter = new DataManagerAdapter();
        try {
            dataManagerAdapter.getUser(username, password);
            return AccessType.USER;
        }catch (WrongPasswordException e){
            LOGGER.log(Level.INFO,"Wrong password!");
        }catch (UserNotPresentException x ){
            LOGGER.log(Level.INFO,"User not present!");
        } catch (ImageReadException e) {
            e.printStackTrace();
        }
        try{
            dataManagerAdapter.getMunicipality(username,password);
            return AccessType.MUNICIPALITY;
        }catch (WrongPasswordException e){
            LOGGER.log(Level.INFO,"Wrong password!");
        }catch (MunicipalityNotPresentException x){
            LOGGER.log(Level.INFO,"Municipality not present!");
        }
        return AccessType.NOT_REGISTERED;
    }

    /**
     * This method return the jurisdiction area of the municipality
     * @param username Is the username of the municipality
     * @return Is the place of the municipality
     */
    @Override
    public Place getMunicipality(String username) {
        return null;
    }
}
