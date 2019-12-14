package com.SafeStreets;

import java.util.Date;
import java.util.List;

public interface DispatcherInterface {
    Boolean userRegistration(User info,String string);
    Boolean municipalityRegistration(String code,String usernanme,String Password, DataIntegrationInfo dataIntegrationInfo);
    AccessType login(String username, String password);
    Boolean newReport(String username,String password,UserReport userReport);
    List<StatisticType> getAvailableStatistics(String username,String password);
    List<Statistic> requestDataAnalysis(String username,String password,StatisticType statisticsType,String location);
    List<UserReport> accessReports(String username,String password,StatisticType type,String location);
    List<Suggestion> getSuggestions(String username, String password, Date from, Date until);
}
