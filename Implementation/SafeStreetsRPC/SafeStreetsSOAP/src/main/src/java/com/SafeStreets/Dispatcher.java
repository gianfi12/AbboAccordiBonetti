package com.SafeStreets;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.*;

@Stateless
@WebService
public class Dispatcher {

    public Dispatcher() {
    }

    @WebMethod
    public Boolean UserRegistration(String name) {
        RegistrationManager registrationManager= new RegistrationManager();
        DataManagerAdapter dataManagerAdapter = new DataManagerAdapter();
        return dataManagerAdapter.getPath();

    }
}