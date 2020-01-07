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

public class UserReportTest {

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

    @Test
    public void toReport() {
    }

    @Test
    public void fromJSON() {
    }
}