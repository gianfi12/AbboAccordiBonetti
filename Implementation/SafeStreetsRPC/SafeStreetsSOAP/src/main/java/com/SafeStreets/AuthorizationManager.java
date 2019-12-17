package com.SafeStreets;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;
import com.SafeStreets.model.AccessType;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorizationManager {
    private final static Logger LOGGER = Logger.getLogger(AuthorizationManager.class.getName());

    public AccessType getAccessType(String username, String password){
        DataManagerAdapter dataManagerAdapter = new DataManagerAdapter();
        try {
            dataManagerAdapter.getUser(username, password);
            return AccessType.USER;
        }catch (WrongPasswordException e){
            LOGGER.log(Level.INFO,"Wrong password!");
        }catch (UserNotPresentException x ){
            LOGGER.log(Level.INFO,"User not present!");
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

}
