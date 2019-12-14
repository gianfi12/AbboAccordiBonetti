package com.SafeStreets;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.*;
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
    public Boolean userRegistration(User info, String password) {
        RegistrationManager registrationManager= new RegistrationManager();
        try {
            registrationManager.startUserRegistration(info.getUsername());
        }catch (IllegalStateException e){
            LOGGER.log(Level.INFO,"Error User registration!");
        }
        return true;
    }

    @WebMethod
    @Override
    public Boolean municipalityRegistration(String code, String usernanme, String Password, DataIntegrationInfo dataIntegrationInfo) {
        return null;
    }

    @WebMethod
    @Override
    public AccessType login(String username, String password) {
        return null;
    }

    @WebMethod
    @Override
    public Boolean newReport(String username, String password, UserReport userReport) {
        return null;
    }

    @WebMethod
    @Override
    public List<StatisticType> getAvailableStatistics(String username, String password) {
        return null;
    }

    @WebMethod
    @Override
    public List<Statistic> requestDataAnalysis(String username, String password, StatisticType statisticsType, String location) {
        return null;
    }

    @WebMethod
    @Override
    public List<UserReport> accessReports(String username, String password, StatisticType type, String location) {
        return null;
    }

    @WebMethod
    @Override
    public List<Suggestion> getSuggestions(String username, String password, Date from, Date until) {
        return null;
    }
}