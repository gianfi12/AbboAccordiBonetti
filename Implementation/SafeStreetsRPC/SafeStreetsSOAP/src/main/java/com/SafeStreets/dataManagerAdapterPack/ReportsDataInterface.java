package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.exceptions.ImageStoreException;
import com.SafeStreets.model.QueryFilter;
import com.SafeStreets.model.Report;
import com.SafeStreets.model.UserReport;

import java.util.List;

public interface ReportsDataInterface {
    void addUserReport(UserReport userReport) throws ImageStoreException;
    List<Report> getReports(QueryFilter filter) throws ImageReadException;
    List<UserReport> getUserReports(QueryFilter filter) throws ImageReadException;
    String getAggregatedResult(QueryFilter filter);
}