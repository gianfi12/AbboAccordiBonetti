package com.SafeStreets;

import java.util.Date;
import java.util.List;

/**
 * This classe define the method that a Dispatcher needs to implement
 */
public interface DispatcherInterface {

    /**
     * This is the method call by a client that wants to register a user in the system
     * @param info This is a Json string that contains the serialization of the User class
     * @param password This is the password selected by the user
     * @return Is a boolean that indicates if the registration has been performed correctly
     */
    Boolean userRegistration(String info,String password);
    /**
     * This is the method called by the client to register a municipality in the system
     * @param code This is the contract code owned by the municipality
     * @param username This is the username choose by the municipality for the registration
     * @param password Is the password that the municipality will use for the authentication
     * @param dataIntegrationInfo It is a Json string with the Data Integration Info that contains the information useful to retrieve the violations from the municipality repository
     * @return Is a boolean that indicates if the registration has succeed
     */
    Boolean municipalityRegistration(String code,String username,String password, String dataIntegrationInfo);
    /**
     * This is the method used to authenticate a user inside the system
     * @param username Is the username inserted by the user
     * @param password Is the password inserted by the user
     * @return Is a Json String that represents the Access Type of the user inside the system
     */
    String login(String username, String password);
    /**
     * This method is called by the client when the user prompt a new report that need to be registered
     * @param username Is the username of the user that send the report
     * @param password Is the password of the user
     * @param userReport Is the report send by the user
     * @return A boolean that indicates if the report has been correctly registered
     */
    Boolean newReport(String username,String password,String userReport);

    /**
     * getAvailableStatistics is called by the client in order to retreive the possible statistics that the use can perform
     * @param username Is the username of the user that wants to perform some statistics
     * @param password Is the password of the user
     * @return Is a JSON string of a string that contains the available Statistics Type to the user
     */
    List<String> getAvailableStatistics(String username,String password);

    /**
     * Is the method that perform the analysis request made by the user
     * @param username Is the username of the use requesting the statistics
     * @param password Is the user's password
     * @param statisticsType Is the type of the statistics requested by the user
     * @param location Is the geographical location on which the analysis must be performed
     * @return Is a JSON string with the result of the analysis
     */
    List<String> requestDataAnalysis(String username,String password,String statisticsType,String location);

    /**
     * The accessReports method is called when a Municipality wants to get the reports made by the user about their zone
     * @param username Is the username of the client
     * @param password Is the password of the client
     * @param from This date indicates that the municipality is interested in the reports from this date on
     * @param until This date indicates that the municipality is interested in the reports up to this date
     * @return Is a list that contains the request reports as a JSON string
     */
    List<String> accessReports(String username,String password,Date from,Date until);

    /**
     * This is the method is used by the municipality to retrieve the suggestions elaborated by the system
     * @param username Is the username of the municipality
     * @param password Is the passowrd of the municipality
     * @return Is a JSON list with the suggestions elaborated by the system
     */
    List<String> getSuggestions(String username, String password);
}
