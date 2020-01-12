package com.SafeStreets.modelEntities;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.UserReport;
import com.SafeStreets.model.ViolationType;
import org.junit.Test;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * It tests the methods of UserReportEntity
 */
public class UserReportEntityTest {

    /**
     * It tests the method toUserReportWithImages.
     * It calls the method on a UserReportEntity to convert and verifies whether the resulted
     * UserReport has the same values of UserReportEntity, included the Place and Coordinate attributes.
     */
    @Test
    public void toUserReportWithImages() throws ImageReadException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        UserReportEntity userReportEntity=em.find(UserReportEntity.class, 1);
        transaction.commit();

        String otherPicturesQueryString="FROM OtherPictureEntity WHERE userReportEntity="+userReportEntity.getId();

        transaction.begin();
        TypedQuery<OtherPictureEntity> otherPicturesQuery = em.createQuery(otherPicturesQueryString, OtherPictureEntity.class);
        transaction.commit();

        List<OtherPictureEntity> otherPictureEntities=otherPicturesQuery.getResultList();

        UserReport userReport=userReportEntity.toUserReportWithImages(otherPictureEntities);



        assertTrue(userReport.getReportOffsetDateTime().isEqual(DataManagerAdapter
                .toOffsetDateTimeFromTimestamp(Timestamp
                        .valueOf(LocalDateTime.of(2019, 11, 12, 15, 50, 0)))));
        assertNull(userReport.getOdtOfWatchedViolation());
        assertEquals(ViolationType.PARKING_ON_SIDEWALK,userReport.getViolationType());
        assertNull(userReport.getDescription());

        assertEquals("Milano", userReport.getPlace().getCity());
        assertEquals("Via Camillo Golgi", userReport.getPlace().getAddress());
        assertEquals("10", userReport.getPlace().getHouseCode());
        assertTrue(45.4769300000==userReport.getPlace().getCoordinate().getLatitude());
        assertTrue(9.2322900000==userReport.getPlace().getCoordinate().getLongitude());
        assertTrue(122.0==userReport.getPlace().getCoordinate().getAltitude());




        assertNotNull("FF456ZZ",userReport.getVehicle().getLicensePlate());
        assertNotNull("jak4", userReport.getAuthorUser().getUsername());

        assertEquals(2, userReport.getOtherPictures().size());


    }
}