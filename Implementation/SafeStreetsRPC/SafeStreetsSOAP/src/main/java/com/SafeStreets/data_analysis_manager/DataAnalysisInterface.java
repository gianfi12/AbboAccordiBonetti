package com.SafeStreets.data_analysis_manager;

import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.QueryFilter;
import com.SafeStreets.model.Statistic;
import com.SafeStreets.model.StatisticType;
import com.SafeStreets.model.UserReport;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface of the DataAnalysisManager component. It allows to get various statistics about the violations,
 * the streets, the vehicles and the effectiveness of the system. Then it allows to get the reports
 * done by the users.
 * @author Massimiliano Bonetti
 */
public interface DataAnalysisInterface {

    /**
     * It returns a new instance of a class that implements this interface.
     * @return a new instance of a class that implements this interface
     */
    static DataAnalysisInterface getInstance() {
        return new DataAnalysisManager();
    }

    /**
     * It returns the statistics about the reported violations,
     * the streets, the vehicles or the effectiveness of the system.
     * The statistics are calculated based on the reports of the given city and with a reportOffsetDateTime
     * between the date "from" and the date "to".
     * If the date "from" is null then it is not considered as a filter.
     * If the date "to" is null then it is not considered as a filter.
     * @param statisticType type of statistic to calculate
     * @param city only the reports of this city are considered
     * @param from only the report with a reportOffsetDateTime from this date are considered
     * @param to only the report with a reportOffsetDateTime until this date are considered
     * @return the statistics about the reported violations,
     * the streets, the vehicles or the effectiveness of the system
     */
    List<Statistic> getStatistics(StatisticType statisticType, String city, LocalDate from,
                                  LocalDate to);

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
}
