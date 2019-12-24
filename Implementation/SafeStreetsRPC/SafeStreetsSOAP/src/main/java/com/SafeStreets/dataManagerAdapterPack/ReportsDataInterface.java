package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.model.QueryFilter;
import com.SafeStreets.model.Report;
import com.SafeStreets.model.UserReport;

import java.util.List;

public interface ReportsDataInterface {
    void addUserReport(UserReport userReport);
    List<Report> getReports(QueryFilter filter);
    List<UserReport> getUserReports(QueryFilter filter);
    String getAggregatedResult(QueryFilter filter);
}
