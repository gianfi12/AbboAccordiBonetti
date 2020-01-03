package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;
import com.SafeStreets.model.User;
import com.SafeStreets.exceptions.*;
import com.SafeStreets.model.*;
import com.SafeStreets.modelEntities.*;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.intellij.lang.annotations.Language;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.xml.crypto.Data;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.math.BigDecimal;
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

    private static final String PICTURESDATA_PATH ="../picturesData/";
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

        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        UserEntity userEntity=em.find(UserEntity.class, username);
        transaction.commit();

        assertEquals(DataManagerAdapter.generatePasswordHash(password, userEntity.getSalt()), userEntity.getPassword());
        assertEquals(PICTURESDATA_PATH+username+"Picture"+".png", userEntity.getPicture());
        assertEquals(PICTURESDATA_PATH+username+"IdCard"+".png", userEntity.getIdCard());

        User savedUser = userEntity.toUser();
        assertTrue(user.isEqual(savedUser));

        transaction=em.getTransaction();
        transaction.begin();
        em.remove(userEntity);
        transaction.commit();
    }

    @Test
    public void addUserTest2() throws UserAlreadyPresentException, ImageStoreException, ImageReadException {

        String password="not_a_password";
        BufferedImage pictureImage = readImageFromResourcesImage(PICTURESDATA_TEST_PATH+"pictureBobTest.png");

        BufferedImage idCardImage = readImageFromResourcesImage(PICTURESDATA_TEST_PATH+"idBobTest.png");

        Coordinate birthCoordinate=new Coordinate(45.4642035,9.189982,0.0);
        Coordinate residenceCoordinate=new Coordinate(45.462035,9.189982,0.0);
        Place placeOfBirth = new Place("Milan", "", "", birthCoordinate);

        Place placeOfResidence = new Place("Milan", "", "", residenceCoordinate);

        String username="user";
        User user = new User(username, "user@mail.com", "Real", "User", placeOfBirth, placeOfResidence, pictureImage, idCardImage, "SDCHSDC127NASD", LocalDate.of(1995,11,23));

        dataManagerAdapter.addUser(user, password);

        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        UserEntity userEntity=em.find(UserEntity.class, username);
        transaction.commit();

        assertEquals(DataManagerAdapter.generatePasswordHash(password, userEntity.getSalt()), userEntity.getPassword());
        assertEquals(PICTURESDATA_PATH+username+"Picture"+".png", userEntity.getPicture());
        assertEquals(PICTURESDATA_PATH+username+"IdCard"+".png", userEntity.getIdCard());

        User savedUser = userEntity.toUser();
        assertTrue(user.isEqual(savedUser));

        transaction=em.getTransaction();
        transaction.begin();
        em.remove(userEntity);
        transaction.commit();
    }


    @Test
    public void getUser() throws UserNotPresentException, ImageReadException, WrongPasswordException {
        User user =dataManagerAdapter.getUser("jak4", "jak");
        assertEquals("jak4", user.getUsername());
        assertEquals("jak@gmail.com", user.getEmail());
        assertEquals("Jak", user.getFirstName());
        assertEquals("Red", user.getLastName());
        assertEquals("Milan", user.getPlaceOfBirth().getCity());
        assertNull(user.getPlaceOfBirth().getAddress());
        assertNull( user.getPlaceOfBirth().getHouseCode());
        assertTrue(45.4769300000==user.getPlaceOfBirth().getCoordinate().getLatitude());
        assertTrue(9.2322900000==user.getPlaceOfBirth().getCoordinate().getLongitude());
        assertTrue(122.0000000000==user.getPlaceOfBirth().getCoordinate().getAltitude());
        assertEquals("Milan", user.getPlaceOfResidence().getCity());
        assertEquals("Piazza della Scala",user.getPlaceOfResidence().getAddress());
        assertEquals("2",user.getPlaceOfResidence().getHouseCode());
        assertTrue(45.4667800000==user.getPlaceOfResidence().getCoordinate().getLatitude());
        assertTrue(9.1904000000==user.getPlaceOfResidence().getCoordinate().getLongitude());
        assertTrue(122.0000000000==user.getPlaceOfResidence().getCoordinate().getAltitude());
        assertEquals("RDEJKA80A01F205W", user.getFiscalCode());
        assertEquals(LocalDate.of(1983,1,1), user.getDateOfBirth());
    }

    @Test
    public void addMunicipality() throws MunicipalityAlreadyPresentException, PlaceForMunicipalityNotPresentException, MunicipalityNotPresentException, WrongPasswordException {
        dataManagerAdapter.addMunicipality("14", "FlorenceMunicipality", "FlorenceP");

        Municipality municipality=dataManagerAdapter.getMunicipality("FlorenceMunicipality", "FlorenceP");

        assertEquals("FlorenceMunicipality", municipality.getName());
        assertEquals("Florence", municipality.getPlace().getCity());
        assertNull(municipality.getPlace().getAddress());
        assertNull(municipality.getPlace().getHouseCode());
        assertNull(municipality.getPlace().getCoordinate());

        String unregisterMunicipality="UPDATE MunicipalityEntity " +
                "SET name=NULL " +
                "WHERE contractCode='14'";

        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        em.createQuery(unregisterMunicipality).executeUpdate();
        transaction.commit();

        unregisterMunicipality="UPDATE MunicipalityEntity " +
                "SET password=NULL " +
                "WHERE contractCode='14'";

        transaction=em.getTransaction();
        transaction.begin();
        em.createQuery(unregisterMunicipality).executeUpdate();
        transaction.commit();

        unregisterMunicipality="UPDATE MunicipalityEntity " +
                "SET passSalt=NULL " +
                "WHERE contractCode='14'";

        transaction=em.getTransaction();
        transaction.begin();
        em.createQuery(unregisterMunicipality).executeUpdate();
        transaction.commit();
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
            assertTrue(userReport.getReportOffsetDateTime().isAfter(DataManagerAdapter.toOffsetDateTimeFromLocalDate(queryFilter.getFrom(), true)));
            assertTrue(userReport.getReportOffsetDateTime().isBefore(DataManagerAdapter.toOffsetDateTimeFromLocalDate(queryFilter.getUntil(), false)));
        }

    }

    @Test
    public void getUserReportsTestWithCityAddressAndHouseCode() throws ImageReadException {
        QueryFilter queryFilter=new QueryFilter(LocalDate.of(2018, 12, 1),
                LocalDate.of(2019, 12, 16),
                new Place("Venice", "Via Amerigo Vespucci", "4", null));
        List<UserReport> userReportList = dataManagerAdapter.getUserReports(queryFilter);

        assertFalse(userReportList.isEmpty());

        for(UserReport userReport : userReportList) {
            assertEquals(userReport.getPlace().getCity(), queryFilter.getPlace().getCity());
            assertEquals(userReport.getPlace().getAddress(), queryFilter.getPlace().getAddress());
            assertEquals(userReport.getPlace().getHouseCode(), queryFilter.getPlace().getHouseCode());
            assertTrue(userReport.getReportOffsetDateTime().isAfter(DataManagerAdapter.toOffsetDateTimeFromLocalDate(queryFilter.getFrom(), true)));
            assertTrue(userReport.getReportOffsetDateTime().isBefore(DataManagerAdapter.toOffsetDateTimeFromLocalDate(queryFilter.getUntil(), false)));
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

        printHash("jak"+"\":xa?(Cc7o]t5f6$");
    }


    private static void printHash(String stringToHash) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(stringToHash.getBytes());
        String hash = Hex.toHexString(digest);
        System.out.println("hash SHA3-512 of "+stringToHash+" is " + hash);
    }

    @Test
    public void testCoordinatePlace() {
        CoordinateEntity coordinateEntity=new CoordinateEntity();
        coordinateEntity.setAltitude(BigDecimal.valueOf(1));
        coordinateEntity.setLatitude(BigDecimal.valueOf(1));
        coordinateEntity.setLongitude(BigDecimal.valueOf(1));

        PlaceEntity placeEntity=new PlaceEntity();
        placeEntity.setCity("Milan");
        placeEntity.setCoordinateEntity(coordinateEntity);

        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        em.persist(coordinateEntity);
        transaction.commit();

        transaction=em.getTransaction();
        transaction.begin();
        em.persist(placeEntity);
        transaction.commit();

    }
}
