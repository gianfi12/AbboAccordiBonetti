package com.SafeStreets;

import com.SafeStreets.dataManagerAdapterPack.ReportsDataInterface;
import com.SafeStreets.dataManagerAdapterPack.UserDataInterface;
import com.SafeStreets.data_analysis_manager.DataAnalysisInterface;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.*;
import com.SafeStreets.modelEntities.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * This class contains the test of the Dispatcher class, the rest of the test are presents on the client test
 */
public class DispatcherTest {
    /**
     * It contains an instance of the user data interface that is used in order to retrieve the information about the user
     */
    UserDataInterface userDataInterface;
    Dispatcher dispatcher;
    final static Gson gson = new Gson();


    @Before
    public void setUp() {
        userDataInterface = UserDataInterface.getUserDataInstance();
    }

    /**
     * This method test the login method
     */
    @Test
    public void testLogin()  {
        dispatcher =  new Dispatcher();
        String response = dispatcher.login("jak4", "jak");
        Type type = new TypeToken<AccessType>(){}.getType();
        AccessType accessType = gson.fromJson(response,type);
        assert (accessType==AccessType.USER);

        response = dispatcher.login("notAUser", "jak");
        type = new TypeToken<AccessType>(){}.getType();
        accessType = gson.fromJson(response,type);
        assert (accessType==AccessType.NOT_REGISTERED);
    }

    /**
     * It tests the method requestDataAnalysis.
     * It mocks the interface DataAnalysisInterface in order to have a stub for it. Then it calls the method requestDataAnalysis
     * one the DispatcherInterface and it verifies whether the returned list has one element as expected and if it was invoked.
     */
    @Test
    public void requestDataAnalysis() {
        DataAnalysisInterface dataAnalysisInterface=mock(DataAnalysisInterface.class);

        Statistic statistic=new Statistic(StatisticType.VIOLATIONS_STAT);
        statistic.setViolationType(ViolationType.PARKING_ON_RESERVED_STALL);

        List<Statistic> expectedResult=new ArrayList<>();
        expectedResult.add(statistic);

        when(dataAnalysisInterface.getStatistics(StatisticType.VIOLATIONS_STAT, "Milano", null, null))
                .thenReturn(expectedResult);

        DispatcherInterface dispatcherInterface= DispatcherInterface.getInstance();
        dispatcherInterface.setDataAnalysisInterface(dataAnalysisInterface);

        List<String> statisticStrings= dispatcherInterface.requestDataAnalysis("Milano", "Milan", StatisticType.VIOLATIONS_STAT.toString(), "{longitude: 9.23229, latitude: 45.47693}");

        Assert.assertEquals(expectedResult.size(), statisticStrings.size());

        verify(dataAnalysisInterface).getStatistics(StatisticType.VIOLATIONS_STAT, "Milano", null, null);
    }

    /**
     * It tests the method accessReports.
     * It mocks the interface DataAnalysisInterface in order to have a stub for it. Then it calls the method accessReports
     * one the DispatcherInterface and it verifies whether the returned list has one element as expected and if it was invoked.
     * @throws ImageReadException if the test fails
     */
    @Test
    public void accessReports() throws ImageReadException {
        DataAnalysisInterface dataAnalysisInterface=mock(DataAnalysisInterface.class);

        QueryFilter queryFilter=new QueryFilter(LocalDate.of(2019, 10, 11),
                LocalDate.of(2019, 10, 13),
                new Place("Milano", "", "", null));

        ReportsDataInterface reportsDataInterface= ReportsDataInterface.getReportsDataInstance();

        List<UserReport> expectedUserReports = reportsDataInterface.getUserReports(queryFilter);

        when(dataAnalysisInterface.getUserReports(Mockito.any(QueryFilter.class)))
                .thenReturn(expectedUserReports);

        DispatcherInterface dispatcherInterface= DispatcherInterface.getInstance();
        dispatcherInterface.setDataAnalysisInterface(dataAnalysisInterface);

        List<String> userReportStrings= dispatcherInterface.accessReports("Milano", "Milan", "2019-10-11 ", "2019-10-13 ");

        Assert.assertEquals(expectedUserReports.size(), userReportStrings.size());

        verify(dataAnalysisInterface).getUserReports(Mockito.any(QueryFilter.class));

    }




}
