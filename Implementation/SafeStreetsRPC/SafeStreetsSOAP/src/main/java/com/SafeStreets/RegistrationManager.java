package com.SafeStreets;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.dataManagerAdapterPack.UserDataInterface;
import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.model.User;

import javax.ejb.Stateless;

@Stateless
public class RegistrationManager {

    public RegistrationManager() {
    }

    public void startUserRegistration(String username) throws IllegalStateException{
        UserDataInterface dataManagerAdapter = new DataManagerAdapter();
        if(false)
            throw new IllegalStateException();
    }

    public void finishUserRegistration(User info, String password) throws IllegalStateException{

    }

    public void abortUserRegistrtion(String username) throws IllegalStateException{

    }

    public void municipalityRegistration(String code,String username,String password) throws MunicipalityNotPresentException{
        DataManagerAdapter dataManagerAdapter = new DataManagerAdapter();
        boolean response = dataManagerAdapter.checkContractCode(code);
        if(!response)
            throw new MunicipalityNotPresentException();
        
    }
}
