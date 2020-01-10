package com.SafeStreets.model;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.modelEntities.UserEntity;
import com.SafeStreets.modelEntities.UserReportEntity;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * It tests the methods of UserReport
 */
public class UserReportTest {
    /**
     * It tests the method toUserReportEntity
     * It calls the method on a UserReport to convert and verifies whether the resulted
     * UserReportEntity has the same values (of the attributes) of UserReport
     */
    @Test
    public void toUserReportEntity() throws ImageReadException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
        EntityManager em = emf.createEntityManager();

        BufferedImage mainPicture= DataManagerAdapter.readImage("../picturesData/ParkingOnReservedStall.png");

        List<BufferedImage> otherPictures = new ArrayList<>();
        otherPictures.add(DataManagerAdapter.readImage("../picturesData/ParkingOnReservedStall.png"));
        otherPictures.add(DataManagerAdapter.readImage("../picturesData/ParkingOnReservedStall.png"));

        Vehicle vehicle=new Vehicle("fb452rt");

        Coordinate coordinate=new Coordinate(45.471245, 9.211029 , 150.0);
        Timestamp reportTimeStamp=Timestamp.valueOf(LocalDateTime.of(2020, 1, 4, 8, 30, 30));
        Timestamp reportOfWatchedViolationTimeStamp=Timestamp.valueOf(LocalDateTime.of(2020, 1, 4, 8, 50, 30));

        Place place=new Place("Milano", "Via Carlo Pisacane", null, coordinate);

        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        UserEntity userEntity= em.find(UserEntity.class, "jak4");
        transaction.commit();

        UserReport userReport=new UserReport(DataManagerAdapter.toOffsetDateTimeFromTimestamp(reportTimeStamp),
                DataManagerAdapter.toOffsetDateTimeFromTimestamp(reportOfWatchedViolationTimeStamp),
                place, ViolationType.PARKING_ON_RESERVED_STALL, null,
                vehicle,
                userEntity.toUser(), mainPicture, otherPictures);

        UserReportEntity userReportEntity=userReport.toUserReportEntity("../picturesData/ParkingOnReservedStall.png");

        assertEquals("Milano", userReportEntity.getPlace().getCity());
        assertEquals("Via Carlo Pisacane", userReportEntity.getPlace().getAddress());
        assertNull(userReportEntity.getPlace().getHouseCode());
        assertEquals("jak4", userReportEntity.getUserEntity().getUsername());
        assertEquals("fb452rt", userReportEntity.getVehicleEntity().getLicensePlate());
        assertEquals(ViolationType.PARKING_ON_RESERVED_STALL.toString(), userReportEntity.getViolationType());
        assertEquals("../picturesData/ParkingOnReservedStall.png", userReportEntity.getMainPicture());
        assertNull(userReportEntity.getDescription());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2020, 1, 4, 8, 30, 30)), userReportEntity.getReportTimeStamp());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2020, 1, 4, 8, 50, 30)), userReportEntity.getTimeStampOfWatchedViolation());

    }

    /**
     * It tests the method toReport
     * It calls the method on a UserReport to convert and verifies whether the resulted
     * Report has the same values (of the attributes) of UserReport
     */
    @Test
    public void toReport() throws ImageReadException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
        EntityManager em = emf.createEntityManager();

        BufferedImage mainPicture= DataManagerAdapter.readImage("../picturesData/ParkingOnReservedStall.png");

        List<BufferedImage> otherPictures = new ArrayList<>();
        otherPictures.add(DataManagerAdapter.readImage("../picturesData/ParkingOnReservedStall.png"));
        otherPictures.add(DataManagerAdapter.readImage("../picturesData/ParkingOnReservedStall.png"));

        Vehicle vehicle=new Vehicle("fb452rt");

        Coordinate coordinate=new Coordinate(45.471245, 9.211029 , 150.0);
        Timestamp reportTimeStamp=Timestamp.valueOf(LocalDateTime.of(2020, 1, 4, 8, 30, 30));
        Timestamp reportOfWatchedViolationTimeStamp=Timestamp.valueOf(LocalDateTime.of(2020, 1, 4, 8, 50, 30));

        Place place=new Place("Milano", "Via Carlo Pisacane", "3", coordinate);

        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        UserEntity userEntity= em.find(UserEntity.class, "jak4");
        transaction.commit();

        UserReport userReport=new UserReport(DataManagerAdapter.toOffsetDateTimeFromTimestamp(reportTimeStamp),
                DataManagerAdapter.toOffsetDateTimeFromTimestamp(reportOfWatchedViolationTimeStamp),
                place, ViolationType.PARKING_ON_RESERVED_STALL, "Description",
                vehicle,
                userEntity.toUser(), mainPicture, otherPictures);

        Report report=userReport.toReport();


        assertEquals("Milano", report.getPlace().getCity());
        assertEquals("Via Carlo Pisacane", report.getPlace().getAddress());
        assertEquals("3", report.getPlace().getHouseCode());
        assertEquals("fb452rt", report.getVehicle().getLicensePlate());
        assertEquals(ViolationType.PARKING_ON_RESERVED_STALL, report.getViolationType());
        assertEquals("Description", report.getDescription());
        assertEquals(DataManagerAdapter.toOffsetDateTimeFromTimestamp(Timestamp.valueOf(LocalDateTime.of(2020,
                1, 4, 8, 30, 30))), report.getReportOffsetDateTime());
        assertEquals(DataManagerAdapter.toOffsetDateTimeFromTimestamp(Timestamp.valueOf(LocalDateTime.of(2020,
                1, 4, 8, 50, 30))), report.getOdtOfWatchedViolation());


    }

    @Test
    public void fromJSON() {
    }

    @Test
    public void toJson() {
    }

    @Test
    public void encodeToString() {
    }

}