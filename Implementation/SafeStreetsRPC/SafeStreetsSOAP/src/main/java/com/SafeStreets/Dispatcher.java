package com.SafeStreets;

import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.model.AccessType;
import com.SafeStreets.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.*;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the instance of the Dispatcher that expose the service that can be reach through the following address
 * http://ip_address:port/SafeStreetsSOAP/DispatcherService
 * This class implements the method defined by the Dispatcher Interface class
 */
@Stateless
@WebService
public class Dispatcher implements DispatcherInterface{
    /**
     * This is used to print log message if an error occurs
     */
    private final static Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());
    /**
     * The Gson object is used to convert object to json and to convert from json string
     */
    private final static Gson gson = new Gson();

    /**
     * This is the method call by a client that wants to register a user in the system
     * @param info This is a Json string that contains the serialization of the User class
     * @param password This is the password selected by the user
     * @return Is a boolean that indicates if the registration has been performed correctly
     */
    @WebMethod
    @Override
    public Boolean userRegistration(String info, String password) {
        RegistrationManager registrationManager= new RegistrationManager();
        Type type = new TypeToken<User>(){}.getType();
        User user = gson.fromJson(info,type);
        try {
            registrationManager.startUserRegistration(user.getUsername());
        }catch (IllegalStateException e){
            LOGGER.log(Level.INFO,"Error User registration!");
        }
        return true;
    }

    /**
     * This is the method called by the client to register a municipality in the system
     * @param code This is the contract code owned by the municipality
     * @param username This is the username choose by the municipality for the registration
     * @param password Is the password that the municipality will use for the authentication
     * @param dataIntegrationInfo It is a Json string with the Data Integration Info that contains the information useful to retrieve the violations from the municipality repository
     * @return Is a boolean that indicates if the registration has succeed
     */
    @WebMethod
    @Override
    public Boolean municipalityRegistration(String code, String username, String password, String dataIntegrationInfo) {
        RegistrationManager registrationManager = new RegistrationManager();
        try{
            registrationManager.municipalityRegistration(code,username,password);
        }catch (MunicipalityNotPresentException e){
            LOGGER.log(Level.INFO,"Wrong contract code!");
        }
        return null;
    }

    /**
     * This is the method used to authenticate a user inside the system
     * @param username Is the username inserted by the user
     * @param password Is the password inserted by the user
     * @return Is a Json String that represents the Access Type of the user inside the system
     */
    @WebMethod
    @Override
    public String login(String username, String password) {
        AuthorizationManager authorizationManager = new AuthorizationManager();
        AccessType accessType = authorizationManager.getAccessType(username,password);
        Type type = new TypeToken<AccessType>(){}.getType();
        return gson.toJson(accessType,type);
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