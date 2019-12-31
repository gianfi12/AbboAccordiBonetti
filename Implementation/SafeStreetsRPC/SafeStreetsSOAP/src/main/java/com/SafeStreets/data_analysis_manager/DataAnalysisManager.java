package com.SafeStreets.data_analysis_manager;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.dataManagerAdapterPack.ReportsDataInterface;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.*;
import com.SafeStreets.modelEntities.CoordinateEntity;
import com.SafeStreets.modelEntities.PlaceEntity;
import com.SafeStreets.modelEntities.VehicleEntity;
import org.intellij.lang.annotations.Language;

import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Stateless
public class DataAnalysisManager implements DataAnalysisInterface {
    private static final LocalDate FIRST_DATE=LocalDate.of(2000, 12, 1);
    private static final int DAYS_SAMPLE=7;

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

        Timestamp fromTS= DataManagerAdapter.toTimestampFromLocalDate(from);
        Timestamp toTS= DataManagerAdapter.toTimestampFromLocalDate(to);

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

        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getInstance();

        /*@Language("SQL") String streetsQuery= "SELECT place, count(*) " +
                "FROM UserReportEntity " +
                "WHERE reportTimeStamp>='"+from+"' AND reportTimeStamp<='"+to+"' AND place.city='"+city+"' "+
                "GROUP BY place.address "+
                "ORDER BY count(*) DESC";*/

        //TODO
        @Language("SQL") String streetsQuery= "SELECT place, count(*) " +
                "FROM UserReportEntity " +
                "ORDER BY count(*) DESC";

        QueryFilter queryFilter=new QueryFilter(streetsQuery);

        List<Object[]> resultList = reportsDataInterface.getAggregatedResult(queryFilter);

        List<Statistic> statisticList=new ArrayList<>();

        for(Object[] result : resultList) {
            Statistic statistic=new Statistic(StatisticType.STREETS_STAT);

            Place place=((PlaceEntity) result[0]).toPlace();
            statistic.setStreet(place);

            @Language("SQL") String coordinatesForStreetQuery= "SELECT place.coordinate " +
                    "FROM UserReportEntity " +
                    "WHERE reportTimeStamp>='"+from+"' AND reportTimeStamp<='"+to+"' AND place.city='"+city+"' " +
                    "AND place.address='"+place.getAddress()+"' ";

            queryFilter=new QueryFilter(coordinatesForStreetQuery);
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
        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getInstance();

        LocalDate dateSample=to;

        List<Statistic> statisticList=new ArrayList<>();

        while(dateSample.isAfter(from)) {

            @Language("SQL") String reportsCountQuery= "SELECT count(*) " +
                    "FROM UserReportEntity " +
                    "WHERE reportTimeStamp<='"+DataManagerAdapter.toTimestampFromLocalDate(dateSample)+"' AND place.city='"+city+"'";

            QueryFilter queryFilter=new QueryFilter(reportsCountQuery);

            List<Object[]> reportsCountResultList = reportsDataInterface.getAggregatedResult(queryFilter);
            Object[] reportsResult = reportsCountResultList.get(0);

            Statistic statistic=new Statistic(StatisticType.EFFECTIVENESS_STAT);
            statistic.setNumberOfReports((Integer) reportsResult[0]);
            statistic.setDate(dateSample);

            String usersCountQuery= "SELECT count(*) " +
                    "FROM UserEntity " +
                    "WHERE dateOfRegistration<='"+DataManagerAdapter.toTimestampFromLocalDate(dateSample)+"' AND placeOfResidenceEntity.city='"+city+"'";

            queryFilter=new QueryFilter(usersCountQuery);

            List<Object[]> usersCountResultList = reportsDataInterface.getAggregatedResult(queryFilter);
            Object[] usersResult = usersCountResultList.get(0);

            statistic.setNumberOfUsers((Integer) usersResult[0]);
            statistic.setReportsNoDivUsersNo(((double) statistic.getNumberOfReports())/((double)  statistic.getNumberOfUsers()));
            statisticList.add(statistic);


            dateSample=dateSample.minusDays(DAYS_SAMPLE);
        }


        return statisticList;
    }

    private List<Statistic> getVehiclesStatistics(String city, Timestamp from,
                                                  Timestamp to) {
        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getInstance();

        @Language("SQL") String vehicleQuery= "SELECT VehicleEntity, count(*) " +
                "FROM UserReportEntity, VehicleEntity " +
                "WHERE vehicleEntity=VehicleEntity AND reportTimeStamp>='"+from+"' AND reportTimeStamp<='"+to+"' AND place.city='"+city+"' "+
                "GROUP BY VehicleEntity "+
                "ORDER BY count(*) DESC";

        QueryFilter queryFilter=new QueryFilter(vehicleQuery);

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
        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getInstance();

        @Language("SQL") String violationQuery= "SELECT violationType, count(*) " +
                "FROM UserReportEntity " +
                "WHERE reportTimeStamp>='"+from+"' AND reportTimeStamp<='"+to+"' AND place.city='"+city+"' "+
                "GROUP BY violationType "+
                "ORDER BY count(*) DESC";

        QueryFilter queryFilter=new QueryFilter(violationQuery);

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
        ReportsDataInterface reportsDataInterface = ReportsDataInterface.getInstance();
        return reportsDataInterface.getUserReports(filter);
    }
}
