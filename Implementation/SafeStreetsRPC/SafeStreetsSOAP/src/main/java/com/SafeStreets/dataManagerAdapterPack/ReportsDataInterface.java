package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.exceptions.ImageStoreException;
import com.SafeStreets.model.QueryFilter;
import com.SafeStreets.model.Report;
import com.SafeStreets.model.UserReport;

import java.util.List;

/**
 * Interface of the DataManager component. It allows to manage the saved reports.
 * @author Massimiliano Bonetti
 */
public interface ReportsDataInterface {
    /**
     * Returns a new instance of a class that implements this interface.
     * @return a new instance of a class that implements this interface
     */
    static ReportsDataInterface getReportsDataInstance() {
        return new DataManagerAdapter();
    }

    /**
     * It saves the given report done by a user.
     * @param userReport report to save
     * @throws ImageStoreException if there was a problem during the store of the images of the report
     */
    void addUserReport(UserReport userReport) throws ImageStoreException;

    /**
     * It returns the reports by applying the given filter.
     * So by considering the city of the filter, the address of the filter if it is not null and
     * the houseCode of the filter if the address and the houseCode are not null
     * @param filter filter to apply to the userReports. In this way are considered the reports of the city of the filter, the address of the filter if it is not null and
     * the houseCode of the filter if the address and the houseCode are not null
     * @return reports done by the user by applying the given filter
     * @throws ImageReadException if it is not possible to read the mainPicture or the otherPictures of the
     * userReports, for example because a path to one image is wrong or because one image is not present
     */
    List<Report> getReports(QueryFilter filter) throws ImageReadException;
    /**
     * It returns the reports done by the user by applying the given filter.
     * So by considering the city of the filter, the address of the filter if it is not null and
     * the houseCode of the filter if the address and the houseCode are not null
     * @param filter filter to apply to the userReports. In this way are considered the reports of the city of the filter, the address of the filter if it is not null and
     * the houseCode of the filter if the address and the houseCode are not null
     * @return reports done by the user by applying the given filter
     * @throws ImageReadException if it is not possible to read the mainPicture or the otherPictures of the
     * userReports, for example because a path to one image is wrong or because one image is not present
     */
    List<UserReport> getUserReports(QueryFilter filter) throws ImageReadException;

    /**
     * It returns the result of the query contained in the given filter.
     * @param filter filter which contains the query to do to the database
     * @return a list of Object[], each element of the list contains one result of the query
     */
    List<Object[]> getAggregatedResult(QueryFilter filter);
}
