package com.SafeStreets.data_analysis_manager;

import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.QueryFilter;
import com.SafeStreets.model.Statistic;
import com.SafeStreets.model.StatisticType;
import com.SafeStreets.model.UserReport;

import java.time.LocalDate;
import java.util.List;

public interface DataAnalysisInterface {

    /**
     * Returns a new instance of a class that implements this interface.
     *
     * @return a new instance of a class that implements this interface
     */
    static DataAnalysisInterface getInstance() {
        return new DataAnalysisManager();
    }

    List<Statistic> getStatistics(StatisticType statisticType, String city, LocalDate from,
                                  LocalDate to);
    List<UserReport> getUserReports(QueryFilter filter) throws ImageReadException;
}
