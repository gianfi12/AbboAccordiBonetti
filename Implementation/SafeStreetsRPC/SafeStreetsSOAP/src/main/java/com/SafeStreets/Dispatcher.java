package com.SafeStreets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@WebService
public class Dispatcher implements DispatcherInterface{
    private final static Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

    public Dispatcher() {
    }

    @WebMethod
    @Override
    public Boolean userRegistration(String info, String password) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
        EntityManager em = emf.createEntityManager();

        Vehicle v=em.find(Vehicle.class, "AP234IJ");
        RegistrationManager registrationManager= new RegistrationManager();
        Type type = new TypeToken<User>(){}.getType();
        User user = new Gson().fromJson(info,type);
        try {
            registrationManager.startUserRegistration(user.getUsername());
        }catch (IllegalStateException e){
            LOGGER.log(Level.INFO,"Error User registration!");
        }
        return true;
    }

    @WebMethod
    @Override
    public Boolean municipalityRegistration(String code, String username, String password, String dataIntegrationInfo) {
        return null;
    }

    @WebMethod
    @Override
    public String login(String username, String password) {
        return null;
    }

    @WebMethod
    @Override
    public Boolean newReport(String username, String password, String userReport) {
        return null;
    }

    @WebMethod
    @Override
    public List<String> getAvailableStatistics(String username, String password) {
        return null;
    }

    @WebMethod
    @Override
    public List<String> requestDataAnalysis(String username, String password, String statisticsType, String location) {
        return null;
    }

    @WebMethod
    @Override
    public List<String> accessReports(String username, String password, String type, String location) {
        return null;
    }

    @WebMethod
    @Override
    public List<String> getSuggestions(String username, String password, Date from, Date until) {
        return null;
    }
}