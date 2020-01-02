package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.exceptions.ImageStoreException;
import com.SafeStreets.model.QueryFilter;
import com.SafeStreets.model.Report;
import com.SafeStreets.model.UserReport;

import java.util.List;

/**
 *
 * @author Massimiliano Bonetti
 */
public interface ReportsDataInterface {
    /**
     * Returns a new instance of a class that implements this interface.
     *
     * @return a new instance of a class that implements this interface
     */
    static ReportsDataInterface getReportsDataInstance() {
        return new DataManagerAdapter();
    }

    void addUserReport(UserReport userReport) throws ImageStoreException;
    List<Report> getReports(QueryFilter filter) throws ImageReadException;
    List<UserReport> getUserReports(QueryFilter filter) throws ImageReadException;
    List<Object[]> getAggregatedResult(QueryFilter filter);
}
