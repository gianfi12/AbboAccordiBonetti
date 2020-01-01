package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;
import com.SafeStreets.model.User;
import com.SafeStreets.exceptions.*;
import com.SafeStreets.model.*;
import com.SafeStreets.modelEntities.UserEntity;
import com.SafeStreets.modelEntities.UserReportEntity;
import com.SafeStreets.modelEntities.VehicleEntity;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.xml.crypto.Data;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataManagerAdapterTest {
    private static final String PERSISTENCE_UNIT_NAME ="manager1";
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static DataManagerAdapter dataManagerAdapter;

    private static final String PICTURESDATA_PATH ="./picturesData/";
    private static final String PICTURESDATA_TEST_PATH ="image/";

    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        dataManagerAdapter=new DataManagerAdapter();
    }

    @Test
    public void addUserTest() throws UserAlreadyPresentException, ImageStoreException, ImageReadException {
        String password="userP";
        BufferedImage pictureImage = readImageFromResourcesImage(PICTURESDATA_TEST_PATH+"pictureBobTest.png");

        BufferedImage idCardImage = readImageFromResourcesImage(PICTURESDATA_TEST_PATH+"idBobTest.png");

        Coordinate birthCoordinate=new Coordinate(4.0,5.0,6.0);
        Coordinate residenceCoordinate=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth = new Place("b", "a", "4", birthCoordinate);

        Place placeOfResidence = new Place("r", "a2", "5", residenceCoordinate);

        String username="bob45";
        User user = new User(username, "bob@m.com", "bob", "r", placeOfBirth, placeOfResidence, pictureImage, idCardImage, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));

        dataManagerAdapter.addUser(user, password);

        UserEntity userEntity=em.find(UserEntity.class, username);
        assertEquals(DataManagerAdapter.generatePasswordHash(password, userEntity.getSalt()), userEntity.getPassword());
        assertEquals(userEntity.getPicture(), PICTURESDATA_PATH+username+"Picture"+".png");
        assertEquals(userEntity.getIdCard(), PICTURESDATA_PATH+username+"IdCard"+".png");

        User savedUser = userEntity.toUser();
        assertTrue(user.isEqual(savedUser));

        em.getTransaction().begin();
        em.remove(userEntity);
        em.getTransaction().commit();
    }




    @Test
    public void getUser() {
    }

    @Test
    public void addMunicipality() throws MunicipalityAlreadyPresentException, PlaceForMunicipalityNotPresentException {
        //dataManagerAdapter.addMunicipality(new Place("Rome", "", "", null),
        // "Rome", "RomeP");
    }

    @Test
    public void getMunicipalityArea() {
    }

    @Test
    public void checkContractCode() {
    }

    @Test
    public void getMunicipality() throws MunicipalityNotPresentException, WrongPasswordException {
        Municipality municipality=dataManagerAdapter.getMunicipality("Rome", "RomeP");
        assertEquals(municipality.getName(),"Rome");
        assertEquals(municipality.getPlace().getCity(),"Rome");
        assertNull(municipality.getPlace().getAddress());
        assertNull(municipality.getPlace().getHouseCode());
        assertNull(municipality.getPlace().getCoordinate());
    }

    @Test
    public void checkPassword() {
    }

    @Test
    public void exists() {
    }

    @Test
    public void generatePasswordHash() {
    }

    @Test
    public void generateSalt() {
    }

    @Test
    public void addUserReport() {
    }

    @Test
    public void getReports() {
    }

    @Test
    public void getUserReportsTestWithCity() throws ImageReadException {
        QueryFilter queryFilter=new QueryFilter(LocalDate.of(2019, 10, 1),
                LocalDate.of(2019, 12, 15),
                new Place("Milan", null, null, null));
        List<UserReport> userReportList = dataManagerAdapter.getUserReports(queryFilter);

        assertFalse(userReportList.isEmpty());

        for(UserReport userReport : userReportList) {
            assertEquals(userReport.getPlace().getCity(), queryFilter.getPlace().getCity());
            assertTrue(userReport.getReportOffsetDateTime().isAfter(DataManagerAdapter.toOffsetDateTimeFromLocalDate(queryFilter.getFrom())));
            assertTrue(userReport.getReportOffsetDateTime().isBefore(DataManagerAdapter.toOffsetDateTimeFromLocalDate(queryFilter.getUntil())));
        }

    }

    @Test
    public void getUserReportsTestWithCityAddressAndHouseCode() throws ImageReadException {
        QueryFilter queryFilter=new QueryFilter(LocalDate.of(2018, 10, 1),
                LocalDate.of(2019, 10, 13),
                new Place("Venice", "Via Amerigo Vespucci", "4", null));
        List<UserReport> userReportList = dataManagerAdapter.getUserReports(queryFilter);

        assertFalse(userReportList.isEmpty());

        for(UserReport userReport : userReportList) {
            assertEquals(userReport.getPlace().getCity(), queryFilter.getPlace().getCity());
            assertEquals(userReport.getPlace().getAddress(), queryFilter.getPlace().getAddress());
            assertEquals(userReport.getPlace().getHouseCode(), queryFilter.getPlace().getHouseCode());
            assertTrue(userReport.getReportOffsetDateTime().isAfter(DataManagerAdapter.toOffsetDateTimeFromLocalDate(queryFilter.getFrom())));
            assertTrue(userReport.getReportOffsetDateTime().isBefore(DataManagerAdapter.toOffsetDateTimeFromLocalDate(queryFilter.getUntil())));
        }

    }

    @Test
    public void getAggregatedResult() {
    }

    @Test
    public void getVehicle() {
        VehicleEntity vehicleEntity = em.find(VehicleEntity.class, "AP234IJ");
    }

    @Test
    public void testLogin() throws WrongPasswordException, UserNotPresentException, ImageReadException {
        User user =dataManagerAdapter.getUser("jak4", "jak");
        assert (user.getUsername()=="jak4");
    }

    @Test
    public void violationStatisticTest() {
        String queryString= "SELECT violationType, count(*) " +
                "FROM UserReportEntity " +
                "GROUP BY violationType";

        List<Object[]> resultList = em.createQuery(queryString).getResultList();

        for(Object[] result : resultList) {
            System.out.println(ViolationType.valueOf(result[0].toString()));
            System.out.println(result[1].toString()+" .");
        }
    }

    private BufferedImage readImageFromResourcesImage(String imagePath) throws ImageReadException {

        BufferedImage bufferedImage;
        try
        {
            InputStream imageStream = this.getClass().getClassLoader().getResourceAsStream(imagePath);
            if(imageStream==null)
                throw new ImageReadException();
            bufferedImage = ImageIO.read(imageStream);
        }
        catch (Exception e)
        {
            throw new ImageReadException();
        }

        return bufferedImage;
    }

    @Test
    public void hashTest() {
        printHash("s4"+"M#c\3|Yzoja`sIu(");
    }


    private static void printHash(String stringToHash) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(stringToHash.getBytes());
        String hash = Hex.toHexString(digest);
        System.out.println("hash SHA3-512 of "+stringToHash+" is " + hash);
    }
}
