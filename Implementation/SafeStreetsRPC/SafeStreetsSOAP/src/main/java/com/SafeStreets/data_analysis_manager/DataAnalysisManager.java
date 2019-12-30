package com.SafeStreets.data_analysis_manager;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.dataManagerAdapterPack.ReportsDataInterface;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.*;
import com.SafeStreets.modelEntities.PlaceEntity;
import com.SafeStreets.modelEntities.UserReportEntity;
import com.SafeStreets.modelEntities.VehicleEntity;

import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Stateless
public class DataAnalysisManager implements DataAnalysisInterface {
    private static final LocalDate FIRST_DATE=LocalDate.of(2000, 12, 1);
    private static final int DAYS_SAMPLE=7;

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

        ReportsDataInterface reportsDataInterface = new DataManagerAdapter();

        String streetsQuery= "SELECT place, count(*) " +
                "FROM UserReportEntity " +
                "WHERE reportTimeStamp>='"+from+"' and reportTimeStamp<='"+to+"' and place.city='"+city+"' "+
                "GROUP BY place.street "+
                "ORDER BY count(*) desc";

        QueryFilter queryFilter=new QueryFilter(streetsQuery);

        List<Object[]> resultList = reportsDataInterface.getAggregatedResult(queryFilter);

        List<Statistic> statisticList=new ArrayList<>();

        for(Object[] result : resultList) {
            Statistic statistic=new Statistic(StatisticType.STREETS_STAT);

            statistic.setStreet(((PlaceEntity) result[0]).toPlace());
            statistic.setNumberOfViolationsInStreet(Integer.parseInt(result[1].toString()));

            statisticList.add(statistic);
        }

        return statisticList;

    }

    private List<Statistic> getEffectivenessesStatistics(String city, LocalDate from,
                                                         LocalDate to) {
        ReportsDataInterface reportsDataInterface = new DataManagerAdapter();

        LocalDate dateSample=to;

        List<Statistic> statisticList=new ArrayList<>();

        while(dateSample.isAfter(from)) {
            String reportsCountQuery= "SELECT count(*) " +
                    "FROM UserReportEntity " +
                    "WHERE reportTimeStamp<='"+DataManagerAdapter.toTimestampFromLocalDate(dateSample)+"' and place.city='"+city+"'";

            QueryFilter queryFilter=new QueryFilter(reportsCountQuery);

            List<Object[]> reportsCountResultList = reportsDataInterface.getAggregatedResult(queryFilter);
            Object[] reportsResult = reportsCountResultList.get(0);

            Statistic statistic=new Statistic(StatisticType.EFFECTIVENESS_STAT);
            statistic.setNumberOfReports((Integer) reportsResult[0]);
            statistic.setDate(dateSample);

            String usersCountQuery= "SELECT count(*) " +
                    "FROM UserEntity " +
                    "WHERE dateOfRegistration<='"+DataManagerAdapter.toTimestampFromLocalDate(dateSample)+"' and placeOfResidenceEntity.city='"+city+"'";

            queryFilter=new QueryFilter(usersCountQuery);

            List<Object[]> usersCountResultList = reportsDataInterface.getAggregatedResult(queryFilter);
            Object[] usersResult = usersCountResultList.get(0);

            statistic.setNumberOfUsers((Integer) usersResult[0]);
            statisticList.add(statistic);


            dateSample=dateSample.minusDays(DAYS_SAMPLE);
        }


        return statisticList;
    }

    private List<Statistic> getVehiclesStatistics(String city, Timestamp from,
                                                  Timestamp to) {
        ReportsDataInterface reportsDataInterface = new DataManagerAdapter();

        String vehicleQuery= "SELECT VehicleEntity, count(*) " +
                "FROM UserReportEntity, VehicleEntity " +
                "WHERE vehicleEntity=VehicleEntity and reportTimeStamp>='"+from+"' and reportTimeStamp<='"+to+"' and place.city='"+city+"' "+
                "GROUP BY VehicleEntity "+
                "ORDER BY count(*) desc";

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
        ReportsDataInterface reportsDataInterface = new DataManagerAdapter();

        String violationQuery= "SELECT violationType, count(*) " +
                "FROM UserReportEntity " +
                "WHERE reportTimeStamp>='"+from+"' and reportTimeStamp<='"+to+"' and place.city='"+city+"' "+
                "GROUP BY violationType "+
                "ORDER BY count(*) desc";

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
        ReportsDataInterface reportsDataInterface = new DataManagerAdapter();
        return reportsDataInterface.getUserReports(filter);
    }
}
