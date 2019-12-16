package com.SafeStreets;

import java.util.Date;
import java.util.List;

public interface DispatcherInterface {
    Boolean userRegistration(String info,String password);
    Boolean municipalityRegistration(String code,String username,String password, String dataIntegrationInfo);
    String login(String username, String password);
    Boolean newReport(String username,String password,String userReport);
    List<String> getAvailableStatistics(String username,String password);
    List<String> requestDataAnalysis(String username,String password,String statisticsType,String location);
    List<String> accessReports(String username,String password,String type,String location);
    List<String> getSuggestions(String username, String password, Date from, Date until);
}
