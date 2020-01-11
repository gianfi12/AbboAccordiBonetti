package com.SafeStreets;

import com.SafeStreets.authorizationmanager.AuthorizationManagerInterface;
import com.SafeStreets.dataManagerAdapterPack.UserDataInterface;
import com.SafeStreets.data_analysis_manager.DataAnalysisInterface;
import com.SafeStreets.elaborationmanager.ElaborationManagerInterface;
import com.SafeStreets.exceptions.*;
import com.SafeStreets.mapsserviceadapter.FieldsException;
import com.SafeStreets.mapsserviceadapter.GeocodeException;
import com.SafeStreets.mapsserviceadapter.MapsServiceInterface;
import com.SafeStreets.model.*;
import com.SafeStreets.registrationmanager.RegistrationManagerInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.*;
import java.lang.reflect.Type;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
     * It allows to get various statistics about the violations,
     * the streets, the vehicles and the effectiveness of the system. Then it allows to get the reports
     * done by the users.
     */
    private DataAnalysisInterface dataAnalysisInterface;

    /**
     * This is the method call by a client that wants to register a user in the system
     * @param info This is a Json string that contains the serialization of the User class
     * @param password This is the password selected by the user
     * @return Is a boolean that indicates if the registration has been performed correctly
     */
    @WebMethod
    @Override
    public Boolean userRegistration(String info, String password) {
        User user = User.fromJSON(info);
        RegistrationManagerInterface registrationManager= RegistrationManagerInterface.getInstance();
        try {
            registrationManager.startUserRegistration(user);
            registrationManager.finishUserRegistration(user,removeFinal(password));
            return true;
        }catch (UserAlreadyPresentException | ImageStoreException e){
            LOGGER.log(Level.INFO,"Error User registration!");
            registrationManager.abortUserRegistration(user.getUsername());
            return false;
        }
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
        RegistrationManagerInterface registrationManager = RegistrationManagerInterface.getInstance();
        try{
            registrationManager.municipalityRegistration(removeFinal(code),removeFinal(username),removeFinal(password));
            Type type = new TypeToken<DataIntegrationInfo>(){}.getType();
            DataIntegrationInfo dataIntegrationInfo1 = gson.fromJson(removeFinal(dataIntegrationInfo),type);
            //TODO register the data integration info
            return true;
        }catch (MunicipalityNotPresentException | PlaceForMunicipalityNotPresentException | MunicipalityAlreadyPresentException e){
            LOGGER.log(Level.INFO,"Wrong contract code!");
            return false;
        }
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
        AuthorizationManagerInterface authorizationManager = AuthorizationManagerInterface.getInstance();
        AccessType accessType = authorizationManager.getAccessType(removeFinal(username),removeFinal(password));
        Type type = new TypeToken<AccessType>(){}.getType();
        return gson.toJson(accessType,type);
    }

    /**
     * This method is called by the client when the user prompt a new report that need to be registered
     * @param username Is the username of the user that send the report
     * @param password Is the password of the user
     * @param userReport Is the report send by the user
     * @return A boolean that indicates if the report has been correctly registered
     */
    @WebMethod
    @Override
    public Boolean newReport(String username, String password, String userReport) {
        AuthorizationManagerInterface authorizationManager = AuthorizationManagerInterface.getInstance();
        username = removeFinal(username);
        password = removeFinal(password);
        AccessType accessType = authorizationManager.getAccessType(username,password);
        if(accessType==AccessType.USER || accessType==AccessType.MUNICIPALITY){
            UserDataInterface userData = UserDataInterface.getUserDataInstance();
            try {
                User user = userData.getUser(username,password);
                UserReport userReport1 = UserReport.fromJSON(userReport, user);
                if( (accessType==AccessType.USER && userReport1.getViolationType().canBeReportedFromUser()) || (accessType==AccessType.MUNICIPALITY && userReport1.getViolationType().canBeReportedFromMunicipality())) {
                    ElaborationManagerInterface elaborationManager = ElaborationManagerInterface.getInstance();
                    elaborationManager.elaborate(userReport1);
                    return true;
                }
            }catch (ElaborationException e){
                LOGGER.log(Level.SEVERE,"Error during the elaboration!");
            }catch (WrongPasswordException | UserNotPresentException | ImageReadException e){
                LOGGER.log(Level.SEVERE,"Error during user elaboration!");
            }
        }
        return false;
    }

    /**
     * getAvailableStatistics is called by the client in order to retreive the possible statistics that the use can perform
     * @param username Is the username of the user that wants to perform some statistics
     * @param password Is the password of the user
     * @return Is a JSON string of a string that contains the available Statistics Type to the user
     */
    @WebMethod
    @Override
    public List<String> getAvailableStatistics(String username, String password) {
        AuthorizationManagerInterface authorizationManager = AuthorizationManagerInterface.getInstance();
        AccessType accessType = authorizationManager.getAccessType(removeFinal(username),removeFinal(password));
        List<String> statistics = new ArrayList<>();
        switch (accessType){
            case NOT_REGISTERED:
                break;
            case USER:
                for (StatisticType statistic : StatisticType.values()){
                    if(statistic.canBeForUser())
                        statistics.add(statistic.toString());
                }
                break;
            case MUNICIPALITY:
                for (StatisticType statistic : StatisticType.values()){
                    if(statistic.canBeForMunicipality())
                        statistics.add(statistic.toString());
                }
                break;
        }
        return statistics;
    }

    /**
     * Is the method that perform the analysis request made by the user
     * @param username Is the username of the use requesting the statistics
     * @param password Is the user's password
     * @param statisticsType Is the type of the statistics requested by the user
     * @param location Is the geographical location on which the analysis must be performed
     * @return Is a JSON string with the result of the analysis
     */
    @WebMethod
    @Override
    public List<String> requestDataAnalysis(String username, String password, String statisticsType, String location) {
        AuthorizationManagerInterface authorizationManager = AuthorizationManagerInterface.getInstance();
        AccessType accessType = authorizationManager.getAccessType(removeFinal(username),removeFinal(password));
        List<String> response = new ArrayList<>();
        statisticsType = removeFinal(statisticsType);
        StatisticType statistic = StatisticType.valueOf(statisticsType);

        if( (accessType==AccessType.USER && statistic.canBeForUser()) || (accessType==AccessType.MUNICIPALITY && statistic.canBeForMunicipality()) ){
            if(dataAnalysisInterface==null) {
                dataAnalysisInterface=DataAnalysisInterface.getInstance();
            }
            MapsServiceInterface mapsService = MapsServiceInterface.getInstance();

            JSONObject obj = new JSONObject(removeFinal(location));
            Double longitude = obj.getDouble("longitude");
            Double latitude = obj.getDouble("latitude");
            try {
                Place place = mapsService.geocoding(new Coordinate(latitude, longitude, 0.0));
                for( Statistic element : dataAnalysisInterface.getStatistics(statistic,place.getCity(),null,null)){
                    response.add(element.toJSON());
                }
            }catch(FieldsException | GeocodeException e){
                LOGGER.log(Level.SEVERE,"Error during the geocoding of the position!");

            }
        }
        return response;
    }

    /**
     * The accessReports method is called when a Municipality wants to get the reports made by the user about their zone
     * @param username Is the username of the client
     * @param password Is the password of the client
     * @param from This date indicates that the municipality is interested in the reports from this date on
     * @param until This date indicates that the municipality is interested in the reports up to this date
     * @return Is a list that contains the request reports as a JSON string
     */
    public List<String> accessReports(String username,String password,String from,String until){
        AuthorizationManagerInterface authorizationManager = AuthorizationManagerInterface.getInstance();
        AccessType accessType = authorizationManager.getAccessType(removeFinal(username),removeFinal(password));
        List<String> returnList = new ArrayList<>();
        if(accessType==AccessType.MUNICIPALITY) {
            if(dataAnalysisInterface==null) {
                dataAnalysisInterface=DataAnalysisInterface.getInstance();
            }

            try {
                LocalDate untilDate=null;
                LocalDate fromDate=null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                from=from.substring(0, from.length() - 1).split(" ")[0];
                if(!from.equals("null")){
                    fromDate = LocalDate.parse(from, formatter);
                }
                until= until.substring(0, until.length() - 1).split(" ")[0];
                if(!until.equals("null")){
                    untilDate = LocalDate.parse(until, formatter);
                }

                List<UserReport> userReports= dataAnalysisInterface.getUserReports(new QueryFilter(fromDate, untilDate , authorizationManager.getMunicipality(removeFinal(username))));
                for(UserReport userReport : userReports){
                    if(userReport.getPlace().getCoordinate()!=null) {
                        returnList.add(userReport.toJson());
                    }
                }
            }catch(ImageReadException | MunicipalityNotPresentException e){
                LOGGER.log(Level.SEVERE,"Error image loading during report access!");
            }
        }
        return returnList;
    }

    /**
     * It sets the dataAnalysisInterface with the new one
     * @param dataAnalysisInterface new dataAnalysisInterface
     */
    public void setDataAnalysisInterface(DataAnalysisInterface dataAnalysisInterface) {
        this.dataAnalysisInterface=dataAnalysisInterface;
    }

    /**
     * This is the method is used by the municipality to retrieve the suggestions elaborated by the system
     * @param username Is the username of the municipality
     * @param password Is the passowrd of the municipality
     * @return Is a JSON list with the suggestions elaborated by the system
     */
    public List<String> getSuggestions(String username, String password){
        return null;
    }

    /**
     * This method is called in order to remove the final ' character from the received string
     * @param string Is the string that contains the terminal 'character
     * @return Is the returned string without the final '
     */
    public static String removeFinal(String string){
        if(string.lastIndexOf("'")==(string.length()-1)) {
            return string.substring(0, string.length() - 1);
        }
        return string;
    }
}