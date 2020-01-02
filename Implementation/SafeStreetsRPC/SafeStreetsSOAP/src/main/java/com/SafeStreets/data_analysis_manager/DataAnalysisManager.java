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
 *
 * @author Massimiliano Bonetti
 */
@Stateless
public class DataAnalysisManager implements DataAnalysisInterface {
    private static final LocalDate FIRST_DATE=LocalDate.of(2000, 12, 1);
    private static final int DAYS_SAMPLE=21;

    /**
     * The constructor is hidden outside the package.
     * Use {@link DataAnalysisInterface#getInstance()}.
     */
    DataAnalysisManager() {
    }

    @Override
    public List<Statistic> getStatistics(StatisticType statisticType, String city, LocalDate from,
                                         LocalDate to) {

        if(from==null)
            from=FIRST_DATE;

        if(to==null)
            to=LocalDate.now(DataManagerAdapter.getZONEID());

        Timestamp fromTS= DataManagerAdapter.toTimestampFromLocalDate(from, true);
        Timestamp toTS= DataManagerAdapter.toTimestampFromLocalDate(to, false);

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

    @Override
    public List<UserReport> getUserReports(QueryFilter filter) throws ImageReadException {
        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getReportsDataInstance();
        return reportsDataInterface.getUserReports(filter);
    }
}
