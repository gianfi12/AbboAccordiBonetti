package com.SafeStreets;

import javax.ejb.Stateless;

@Stateless
public class RegistrationManager {

    public RegistrationManager() {
    }

    public void startUserRegistration(String username) throws IllegalStateException{
        DataManagerAdapter dataManagerAdapter = new DataManagerAdapter();
        if(false)
            throw new IllegalStateException();
    }

    public void finishUserRegistration(User info,String password) throws IllegalStateException{

    }

    public void abortUserRegistrtion(String username) throws IllegalStateException{

    }
}
