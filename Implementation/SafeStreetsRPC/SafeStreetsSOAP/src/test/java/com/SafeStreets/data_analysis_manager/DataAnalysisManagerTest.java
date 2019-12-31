package com.SafeStreets.data_analysis_manager;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.dataManagerAdapterPack.ReportsDataInterface;
import com.SafeStreets.model.*;
import com.SafeStreets.modelEntities.CoordinateEntity;
import com.SafeStreets.modelEntities.PlaceEntity;
import org.intellij.lang.annotations.Language;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataAnalysisManagerTest {

    @Test
    public void getStreetsViolationsStatisticsTest() {
        DataAnalysisInterface dataAnalysisInterface=new DataAnalysisManager();

        List<Statistic> statisticList= dataAnalysisInterface.getStatistics(StatisticType.STREETS_STAT, "Milan",
                LocalDate.of(2019, 9, 15), LocalDate.of(2019, 12, 31));

        for (Statistic statistic : statisticList) {
            assertEquals(StatisticType.STREETS_STAT, statistic.getStatisticType());
            System.out.println("street: "+statistic.getStreet());
            System.out.println("number of violations: "+statistic.getNumberOfViolationsInStreet());
            for(Coordinate coordinate : statistic.getCoordinateListForStreet()) {
                System.out.println("coordinate: "+coordinate.toString());
            }
        }

        assertEquals("Via Camillo Golgi", statisticList.get(0).getStreet());
        assertEquals(2, statisticList.get(0).getNumberOfViolationsInStreet());

        assertEquals("Piazza della Scala", statisticList.get(1).getStreet());
        assertEquals(1, statisticList.get(1).getNumberOfViolationsInStreet());
    }

    @Test
    public void getEffectivenessesStatisticsTest() {
        DataAnalysisInterface dataAnalysisInterface=new DataAnalysisManager();

        List<Statistic> statisticList= dataAnalysisInterface.getStatistics(StatisticType.EFFECTIVENESS_STAT, "Milan",
                LocalDate.of(2019, 11, 1), LocalDate.of(2019, 12, 31));

        for (Statistic statistic : statisticList) {
            assertEquals(StatisticType.EFFECTIVENESS_STAT, statistic.getStatisticType());
            System.out.println("stat");
            System.out.println(statistic.getNumberOfReports());
            System.out.println(statistic.getNumberOfUsers());
            System.out.println(statistic.getReportsNoDivUsersNo());
            System.out.println(statistic.getDate());
        }

        assertEquals(3, statisticList.get(0).getNumberOfReports());
        assertEquals(2, statisticList.get(0).getNumberOfUsers());
        assertTrue(1.5==statisticList.get(0).getReportsNoDivUsersNo());
        assertEquals(LocalDate.of(2019, 12, 31), statisticList.get(0).getDate());

        assertEquals(3, statisticList.get(1).getNumberOfReports());
        assertEquals(1, statisticList.get(1).getNumberOfUsers());
        assertTrue(3==statisticList.get(1).getReportsNoDivUsersNo());
        assertEquals(LocalDate.of(2019, 12, 10), statisticList.get(1).getDate());

        assertEquals(2, statisticList.get(2).getNumberOfReports());
        assertEquals(1, statisticList.get(2).getNumberOfUsers());
        assertTrue(2==statisticList.get(2).getReportsNoDivUsersNo());
        assertEquals(LocalDate.of(2019, 11, 19), statisticList.get(2).getDate());

        assertEquals(1, statisticList.get(3).getNumberOfReports());
        assertEquals(0, statisticList.get(3).getNumberOfUsers());
        assertTrue(-1==statisticList.get(3).getReportsNoDivUsersNo());
        assertEquals(LocalDate.of(2019, 11, 1), statisticList.get(3).getDate());







    }



    @Test
    public void getVehiclesStatisticsTest() {
        DataAnalysisInterface dataAnalysisInterface=new DataAnalysisManager();

        List<Statistic> statisticList= dataAnalysisInterface.getStatistics(StatisticType.VEHICLES_STAT, "Milan",
                LocalDate.of(2019, 9, 15), LocalDate.of(2019, 12, 31));

        for (Statistic statistic : statisticList) {
            assertEquals(StatisticType.VEHICLES_STAT, statistic.getStatisticType());
            System.out.println(statistic.getVehicle().getLicensePlate()+"   "+statistic.getNumberOfViolationsOfVehicle());
        }

        assertEquals("FF456ZZ", statisticList.get(0).getVehicle().getLicensePlate());
        assertEquals(2, statisticList.get(0).getNumberOfViolationsOfVehicle());

        assertEquals("DS473OP", statisticList.get(1).getVehicle().getLicensePlate());
        assertEquals(1, statisticList.get(1).getNumberOfViolationsOfVehicle());

    }

    @Test
    public void getViolationsStatisticsTest() {
        DataAnalysisInterface dataAnalysisInterface=new DataAnalysisManager();

        List<Statistic> statisticList= dataAnalysisInterface.getStatistics(StatisticType.VIOLATIONS_STAT, "Venice",
                LocalDate.of(2019, 9, 15), LocalDate.of(2019, 12, 31));

        for (Statistic statistic : statisticList) {
            assertEquals(StatisticType.VIOLATIONS_STAT, statistic.getStatisticType());
            System.out.println(statistic.getViolationType());
        }

        assertEquals(ViolationType.valueOf("PARKING_ON_RESERVED_STALL"), statisticList.get(0).getViolationType());


    }



    @Test
    public void getUserReportsTest() {
    }

    @Test
    public void queryTest() {
        ReportsDataInterface reportsDataInterface= ReportsDataInterface.getInstance();

        Timestamp from= DataManagerAdapter.toTimestampFromLocalDate(LocalDate.of(2019, 10, 1), true);
        Timestamp to= DataManagerAdapter.toTimestampFromLocalDate(LocalDate.of(2019, 12, 31), false);
        String city="Milan";
        Place place=new Place(city, "Via Camillo Golgi", "", null);


        /*String coordinatesForStreetQuery= "SELECT place.coordinate " +
                "FROM UserReportEntity " +
                "WHERE reportTimeStamp>='"+from+"' AND reportTimeStamp<='"+to+"' AND place.city='"+city+"' " +
                "AND place.address='"+place.getAddress()+"' ";*/
        String coordinatesForStreetQuery= "SELECT UR.place.coordinateEntity " +
                "FROM UserReportEntity AS UR " +
                "WHERE UR.reportTimeStamp>='"+from+"' AND UR.reportTimeStamp<='"+to+"' AND UR.place.city='"+city+"' "+
                "AND UR.place.address='"+place.getAddress()+"' ";

        QueryFilter queryFilter=new QueryFilter(coordinatesForStreetQuery, true);


        List<Object[]> coordinatesResultList=reportsDataInterface.getAggregatedResult(queryFilter);

        for(Object[] coordinate : coordinatesResultList) {
            Coordinate coordinate1=((CoordinateEntity) coordinate[0]).toCoordinate();
            System.out.println(coordinate1.toString());
        }
    }
}