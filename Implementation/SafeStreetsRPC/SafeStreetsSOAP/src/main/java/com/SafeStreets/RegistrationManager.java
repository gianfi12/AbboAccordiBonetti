package com.SafeStreets;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.dataManagerAdapterPack.UserDataInterface;
import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.exceptions.RegistrationException;
import com.SafeStreets.exceptions.UserAlreadyPresentException;
import com.SafeStreets.model.User;

import javax.ejb.Stateless;

/**
 * This class represents the Registration Manager that is responsible of perform the registration of the user and the municipality inside the system
 */
@Stateless
public class RegistrationManager implements RegistrationManagerInterface{

    /**
     * This method is called when a user what to perform a registration inside the system, it checks if the user can be registered inside the system
     * @param info Is a User object that contains all the information about the user
     * @throws UserAlreadyPresentException Is thrown if the user cannot be registered inside the system
     */
    @Override
    public void startUserRegistration(User info) throws UserAlreadyPresentException{
        UserDataInterface dataManagerAdapter = new DataManagerAdapter();
        IdentityVerifierInterface identityVerifier = new IdentityVerifierAdapter();
        if(dataManagerAdapter.exists(info.getUsername()) && !identityVerifier.verify(info))
                throw new UserAlreadyPresentException();
    }

    /**
     * This method is called when the users' registration can be performed, and it memorize the user in the system
     * @param info Is the instance of the user that has to be registered
     * @throws RegistrationException Is thrown if an error occurs during the registration
     */
    @Override
    public void finishUserRegistration(User info) throws RegistrationException {

    }

    /**
     * The abortUserRegistration is used to abort the user registration if some error occours
     * @param username Is the username of the user of which the registration has to be cancelled
     */
    @Override
    public void abortUserRegistration(String username) {

    }

    /**
     * This method is used to memorize inside the system the data integration info about the municipality if the municipality offers this possibility
     * @param username Is the name that the municipality has choose
     * @param dataIntegrationInfo Is the instance of the data integration info that will be saved inside the system
     */
    @Override
    public void addDataIntegration(String username, DataIntegrationInfo dataIntegrationInfo) {

    }

    /**
     * This method is called by the registration manager when a municipality has to be registered
     * @param code Is the contract code given by the municipality
     * @param username Is the username that will be used by the municipality
     * @param password This is the password provided by the municipality
     * @throws MunicipalityNotPresentException Is thrown if the some error occurs during the registration
     */
    @Override
    public void municipalityRegistration(String code, String username, String password) throws MunicipalityNotPresentException{
        DataManagerAdapter dataManagerAdapter = new DataManagerAdapter();
        boolean response = dataManagerAdapter.checkContractCode(code);
        if(!response)
            throw new MunicipalityNotPresentException();
        
    }
}
