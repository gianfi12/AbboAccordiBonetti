package com.SafeStreets.data_analysis_manager;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.*;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * It tests the methods of the DataAnalysisManager
 */
public class DataAnalysisManagerTest {
    /**
     * It tests the method getStatistics when statisticType is STREETS_STAT.
     * It verifies whether the statistics are of type STREETS_STAT and if they have been calculated correctly by
     * checking the values of the statistics with the expected results.
     */
    @Test
    public void getStreetsViolationsStatisticsTest() {
        DataAnalysisInterface dataAnalysisInterface=new DataAnalysisManager();

        List<Statistic> statisticList= dataAnalysisInterface.getStatistics(StatisticType.STREETS_STAT, "Milano",
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

    /**
     * It tests the method getStatistics when statisticType is EFFECTIVENESS_STAT.
     * It verifies whether the statistics are of type EFFECTIVENESS_STAT and if they have been calculated correctly by
     * checking the values of the statistics with the expected results.
     */
    @Test
    public void getEffectivenessesStatisticsTest() {
        DataAnalysisInterface dataAnalysisInterface=new DataAnalysisManager();

        List<Statistic> statisticList= dataAnalysisInterface.getStatistics(StatisticType.EFFECTIVENESS_STAT, "Milano",
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



    /**
     * It tests the method getStatistics when statisticType is VEHICLES_STAT.
     * It verifies whether the statistics are of type VEHICLES_STAT and if they have been calculated correctly by
     * checking the values of the statistics with the expected results.
     */
    @Test
    public void getVehiclesStatisticsTest() {
        DataAnalysisInterface dataAnalysisInterface=new DataAnalysisManager();

        List<Statistic> statisticList= dataAnalysisInterface.getStatistics(StatisticType.VEHICLES_STAT, "Milano",
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

    /**
     * It tests the method getStatistics when statisticType is VIOLATIONS_STAT.
     * It verifies whether the statistics are of type VIOLATIONS_STAT and if they have been calculated correctly by
     * checking the values of the statistics with the expected results.
     */
    @Test
    public void getViolationsStatisticsTest() {
        DataAnalysisInterface dataAnalysisInterface=new DataAnalysisManager();

        List<Statistic> statisticList= dataAnalysisInterface.getStatistics(StatisticType.VIOLATIONS_STAT, "Venezia",
                LocalDate.of(2019, 9, 15), LocalDate.of(2019, 12, 31));

        for (Statistic statistic : statisticList) {
            assertEquals(StatisticType.VIOLATIONS_STAT, statistic.getStatisticType());
            System.out.println(statistic.getViolationType());
        }

        assertEquals(ViolationType.valueOf("PARKING_ON_RESERVED_STALL"), statisticList.get(0).getViolationType());


    }

    /**
     * It tests the method getStatistics when statisticType is VIOLATIONS_STAT.
     * It verifies whether the VIOLATIONS_STAT are of type STREETS_STAT and if they have been calculated correctly by
     * checking the values of the statistics with the expected results.
     */
    @Test
    public void getStatisticsWithoutFilterOnDatesTest() {
        DataAnalysisInterface dataAnalysisInterface=new DataAnalysisManager();

        List<Statistic> statisticList= dataAnalysisInterface.getStatistics(StatisticType.VIOLATIONS_STAT, "Milano",
                null, null);

        for (Statistic statistic : statisticList) {
            assertEquals(StatisticType.VIOLATIONS_STAT, statistic.getStatisticType());
            System.out.println(statistic.getViolationType());
        }

        assertEquals(ViolationType.valueOf("PARKING_ON_RESERVED_STALL"), statisticList.get(0).getViolationType());
        assertEquals(ViolationType.valueOf("PARKING_ON_SIDEWALK"), statisticList.get(1).getViolationType());


    }


    /**
     * It tests the method getUserReports.
     * It verifies whether the reports have the most important attributes set and whether they respect the given filter.
     */
    @Test
    public void getUserReportsTest() throws ImageReadException {
        DataAnalysisInterface dataAnalysisInterface=DataAnalysisInterface.getInstance();

        QueryFilter queryFilter=new QueryFilter(LocalDate.of(2019, 10, 12),
                LocalDate.of(2019, 11, 12),
                new Place("Milano", "", "", null));
        List<UserReport> userReportList = dataAnalysisInterface.getUserReports(queryFilter);

        assertFalse(userReportList.isEmpty());

        assertEquals(2, userReportList.size());

        for(UserReport userReport : userReportList) {
            assertEquals(userReport.getPlace().getCity(), queryFilter.getPlace().getCity());
            assertTrue(userReport.getReportOffsetDateTime().isAfter(DataManagerAdapter.toOffsetDateTimeFromLocalDate(queryFilter.getFrom(), true)));
            assertTrue(userReport.getReportOffsetDateTime().isBefore(DataManagerAdapter.toOffsetDateTimeFromLocalDate(queryFilter.getUntil(), false)));
            assertNull(userReport.getOdtOfWatchedViolation());
            assertNotNull(userReport.getViolationType());
            assertNull(userReport.getDescription());
            assertNotNull(userReport.getVehicle().getLicensePlate());
            assertNotNull(userReport.getAuthorUser().getUsername());
        }

    }
}