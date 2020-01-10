package com.SafeStreets.data_analysis_manager;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.dataManagerAdapterPack.ReportsDataInterface;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.*;
import com.SafeStreets.modelEntities.CoordinateEntity;
import com.SafeStreets.modelEntities.VehicleEntity;
import org.intellij.lang.annotations.Language;

import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

/**
 * An implementation of {@link DataAnalysisInterface}.
 * It implements the DataAnalysisManager component. It allows to get various statistics about the violations,
 * the streets, the vehicles and the effectiveness of the system. Then it allows to get the reports
 * done by the users.
 *
 * @author Massimiliano Bonetti
 * @see DataAnalysisInterface
 */
@Stateless
public class DataAnalysisManager implements DataAnalysisInterface {
    /**
     * Conventionally, first day of activity of the system. It is considered as start day when
     * the given start day of the methods is null.
     */
    private static final LocalDate FIRST_DATE=LocalDate.of(2019, 10, 1);
    /**
     * Interval of time between one effectiveness statistic and the next one.
     */
    private static final int DAYS_SAMPLE=21;

    /**
     * The constructor is hidden outside the package.
     * Use {@link DataAnalysisInterface#getInstance()}.
     */
    DataAnalysisManager() {
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
    @Override
    public List<Statistic> getStatistics(StatisticType statisticType, String city, LocalDate from,
                                         LocalDate to) {

        if(from==null)
            from=FIRST_DATE;

        if(to==null)
            to=LocalDate.now(DataManagerAdapter.getZONEID());

        Timestamp fromTS= DataManagerAdapter.toTimestampFromLocalDate(from, true);
        Timestamp toTS= DataManagerAdapter.toTimestampFromLocalDate(to, false);

        city=DataManagerAdapter.getStdStringForCity(city);

        switch (statisticType) {
            case STREETS_STAT:
                return getStreetsViolationsStatistics(city, fromTS, toTS);
            case EFFECTIVENESS_STAT:
                return getEffectivenessesStatistics(city, from, to);
            case VEHICLES_STAT:
                return getVehiclesStatistics(city, fromTS, toTS);
            case VIOLATIONS_STAT:
                return getViolationsStatistics(city, fromTS, toTS);
            default:
                return new ArrayList<>();
        }

    }

    /**
     * It returns the statistics about the streets. In particular it returns a list of Statistic, each
     * one contains the street considered, the number of reported violations in that street and a list
     * of coordinates of the reports in that street.
     * The statistics are calculated based on the reports of the given city and with a reportOffsetDateTime
     * between the date "from" and the date "to".
     * If the date "from" is null then it is not considered as a filter.
     * If the date "to" is null then it is not considered as a filter.
     * @param city only the reports of this city are considered
     * @param from only the report with a reportOffsetDateTime from this timestamp are considered
     * @param to only the report with a reportOffsetDateTime until this timestamp are considered
     * @return statistics about the reported violations. In particular it returns a list of Statistic, each
     * one contains the street considered, the number of reported violations in that street and a list
     * of coordinates of the reports in that street.
     */
    private List<Statistic> getStreetsViolationsStatistics(String city, Timestamp from,
                                                           Timestamp to) {

        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getReportsDataInstance();

        @Language("SQL") String streetsQuery= "SELECT uR.place.address, count(*) " +
                "FROM UserReportEntity AS uR " +
                "WHERE uR.reportTimeStamp>='"+from+"' AND uR.reportTimeStamp<='"+to+"' AND uR.place.city='"+city+"' "+
                "GROUP BY uR.place.address "+
                "ORDER BY count(*) DESC";


        QueryFilter queryFilter=new QueryFilter(streetsQuery, false);

        List<Object[]> resultList = reportsDataInterface.getAggregatedResult(queryFilter);

        List<Statistic> statisticList=new ArrayList<>();

        for(Object[] result : resultList) {
            Statistic statistic=new Statistic(StatisticType.STREETS_STAT);

            String address=(String) result[0];
            statistic.setStreet(address);

            @Language("SQL") String coordinatesForStreetQuery= "SELECT uR.place.coordinateEntity " +
                    "FROM UserReportEntity AS uR " +
                    "WHERE uR.reportTimeStamp>='"+from+"' AND uR.reportTimeStamp<='"+to+"' AND uR.place.city='"+city+"' " +
                    "AND uR.place.address='"+address+"' ";

            queryFilter=new QueryFilter(coordinatesForStreetQuery, true);
            List<Object[]> coordinatesResultList=reportsDataInterface.getAggregatedResult(queryFilter);

            List<Coordinate> coordinates=new ArrayList<>();
            for(Object[] coordinate : coordinatesResultList) {
                coordinates.add(((CoordinateEntity) coordinate[0]).toCoordinate());
            }

            statistic.setCoordinateListForStreet(coordinates);
            statistic.setNumberOfViolationsInStreet(Integer.parseInt(result[1].toString()));

            statisticList.add(statistic);
        }

        return statisticList;

    }

    /**
     * It returns the statistics about the effectiveness of the system. In particular it returns a list of Statistic, each statistic
     * contains a date. The list is ordered from the most recent date which is "to" and the next statistic has a date that differs
     * from the previous one by DAYS_SAMPLE days. The last statistic has date "to".
     * Each statistic has also the number of users that were registered in the system at the corresponding date,
     * the number of reports that were present in the system at the corresponding date and the
     * number of users divided by the number of reports at the corresponding date.
     * The statistics are calculated based on the reports of the given city and with a reportOffsetDateTime
     * between the date "from" and the date "to".
     * If the date "from" is null then it is not considered as a filter.
     * If the date "to" is null then it is not considered as a filter.
     * @param city only the reports of this city are considered
     * @param from only the report with a reportOffsetDateTime from this date are considered
     * @param to only the report with a reportOffsetDateTime until this date are considered
     * @return statistics about the effectiveness of the system. In particular it returns a list of Statistic, each statistic
     * contains a date. The list is ordered from the most recent date which is "to" and the next statistic has a date that differs
     * from the previous one by DAYS_SAMPLE days. The last statistic has date "to".
     * Each statistic has also the number of users that were registered in the system at the corresponding date,
     * the number of reports that were present in the system at the corresponding date and the
     * number of users divided by the number of reports at the corresponding date.
     */
    private List<Statistic> getEffectivenessesStatistics(String city, LocalDate from,
                                                         LocalDate to) {


        LocalDate dateSample=to;

        List<Statistic> statisticList=new ArrayList<>();

        while(dateSample.isAfter(from)) {

            statisticList.add(getEffectivenessStatistic(city, dateSample));

            dateSample=dateSample.minusDays(DAYS_SAMPLE);
        }

        if(dateSample.isBefore(from)) {
            statisticList.add(getEffectivenessStatistic(city, from));
        }


        return statisticList;
    }


    /**
     * It returns one statistic of type effectiveness. The statistic contains the number of users that were registered in the system at the given dateSample,
     * the number of reports that were present in the system at the given dateSample and the
     * number of users divided by the number of reports at the given dateSample.
     * @param city only the reports of this city are considered
     * @param dateSample the statistic is calculated on this date
     * @return effectiveness statistic which contains the number of users that were registered in the system at the given dateSample,
     * the number of reports that were present in the system at the given dateSample and the
     * number of users divided by the number of reports at the given dateSample.
     */
    private Statistic getEffectivenessStatistic(String city, LocalDate dateSample) {
        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getReportsDataInstance();

        @Language("SQL") String reportsCountQuery= "SELECT count(*) " +
                "FROM UserReportEntity AS uR " +
                "WHERE uR.reportTimeStamp<='"+DataManagerAdapter.toTimestampFromLocalDate(dateSample, false)+
                "' AND uR.place.city='"+city+"'";

        QueryFilter queryFilter=new QueryFilter(reportsCountQuery, true);

        List<Object[]> reportsCountResultList = reportsDataInterface.getAggregatedResult(queryFilter);
        Object[] reportsResult = reportsCountResultList.get(0);

        Statistic statistic=new Statistic(StatisticType.EFFECTIVENESS_STAT);
        statistic.setNumberOfReports(Integer.parseInt(reportsResult[0].toString()));
        statistic.setDate(dateSample);

        @Language("SQL") String usersCountQuery= "SELECT count(*) " +
                "FROM UserEntity AS u " +
                "WHERE u.dateOfRegistration<='"+DataManagerAdapter.toTimestampFromLocalDate(dateSample, false)+
                "' AND u.placeOfResidenceEntity.city='"+city+"'";

        queryFilter=new QueryFilter(usersCountQuery, true);

        List<Object[]> usersCountResultList = reportsDataInterface.getAggregatedResult(queryFilter);
        Object[] usersResult = usersCountResultList.get(0);

        statistic.setNumberOfUsers(Integer.parseInt(usersResult[0].toString()));
        double reportsNo=(double) statistic.getNumberOfReports();
        double usersNo=(double)  statistic.getNumberOfUsers();
        if(usersNo==0) {
            statistic.setReportsNoDivUsersNo(-1);
        } else {
            statistic.setReportsNoDivUsersNo(reportsNo / usersNo);
        }

        return statistic;
    }

    /**
     * It returns the statistics about the vehicles. In particular it returns a list of Statistic ordered by the vehicle
     * that has committed the highest number of violations. Each statistic
     * contains a vehicle.
     * The statistics are calculated based on the reports of the given city and with a reportOffsetDateTime
     * between the date "from" and the date "to".
     * If the date "from" is null then it is not considered as a filter.
     * If the date "to" is null then it is not considered as a filter.
     * @param city only the reports of this city are considered
     * @param from only the report with a reportOffsetDateTime from this timestamp are considered
     * @param to only the report with a reportOffsetDateTime until this timestamp are considered
     * @return statistics about the vehicles. In particular it returns a list of Statistic ordered by the vehicle
     * that has committed the highest number of violations. Each statistic
     * contains a vehicle.
     */
    private List<Statistic> getVehiclesStatistics(String city, Timestamp from,
                                                  Timestamp to) {
        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getReportsDataInstance();

        @Language("SQL") String vehicleQuery= "SELECT uR.vehicleEntity, count(*) " +
                "FROM UserReportEntity AS uR " +
                "WHERE uR.reportTimeStamp>='"+from+"' AND uR.reportTimeStamp<='"+to+
                "' AND uR.place.city='"+city+"' "+
                "GROUP BY uR.vehicleEntity "+
                "ORDER BY count(*) DESC";

        QueryFilter queryFilter=new QueryFilter(vehicleQuery, false);

        List<Object[]> resultList = reportsDataInterface.getAggregatedResult(queryFilter);

        List<Statistic> statisticList=new ArrayList<>();

        for(Object[] result : resultList) {
            Statistic statistic=new Statistic(StatisticType.VEHICLES_STAT);

            statistic.setVehicle(((VehicleEntity) result[0]).toVehicle());
            statistic.setNumberOfViolationsOfVehicle(Integer.parseInt(result[1].toString()));

            statisticList.add(statistic);
        }

        return statisticList;
    }

    /**
     * It returns the statistics about the reported violations. In particular it returns a list of Statistic ordered
     * by the type of violation that has been reported more. Each statistic contains a type of violation.
     * The statistics are calculated based on the reports of the given city and with a reportOffsetDateTime
     * between the date "from" and the date "to".
     * If the date "from" is null then it is not considered as a filter.
     * If the date "to" is null then it is not considered as a filter.
     * @param city only the reports of this city are considered
     * @param from only the report with a reportOffsetDateTime from this timestamp are considered
     * @param to only the report with a reportOffsetDateTime until this timestamp are considered
     * @return statistics about the reported violations. In particular it returns a list of Statistic ordered
     * by the type of violation that has been reported more. Each statistic contains a type of violation.
     */
    private List<Statistic> getViolationsStatistics(String city, Timestamp from,
                                                    Timestamp to) {
        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getReportsDataInstance();

        @Language("SQL") String violationQuery= "SELECT uR.violationType, count(*) " +
                "FROM UserReportEntity AS uR " +
                "WHERE uR.reportTimeStamp>='"+from+"' AND uR.reportTimeStamp<='"+to+"' AND uR.place.city='"+city+"' "+
                "GROUP BY uR.violationType "+
                "ORDER BY count(*) DESC";

        QueryFilter queryFilter=new QueryFilter(violationQuery, false);

        List<Object[]> resultList = reportsDataInterface.getAggregatedResult(queryFilter);

        List<Statistic> statisticList=new ArrayList<>();

        for(Object[] result : resultList) {
            Statistic statistic=new Statistic(StatisticType.VIOLATIONS_STAT);
            statistic.setViolationType(ViolationType.valueOf(result[0].toString()));
            statisticList.add(statistic);
        }

        return statisticList;
    }

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
    @Override
    public List<UserReport> getUserReports(QueryFilter filter) throws ImageReadException {
        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getReportsDataInstance();
        return reportsDataInterface.getUserReports(filter);
    }
}
