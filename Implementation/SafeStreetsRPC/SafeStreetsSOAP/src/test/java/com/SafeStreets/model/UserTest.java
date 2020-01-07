package com.SafeStreets.model;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.modelEntities.UserEntity;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserTest {

    private static final String PICTURESDATA_TEST_PATH ="image/";

    @Test
    public void toUserEntity() throws ImageReadException {
        String password="userP";

        String picturePath=PICTURESDATA_TEST_PATH+"pictureBobTest.png";
        String idPicturePath=PICTURESDATA_TEST_PATH+"idBobTest.png";

        BufferedImage pictureImage = readImageFromResourcesImage(picturePath);
        BufferedImage idCardImage = readImageFromResourcesImage(idPicturePath);

        Coordinate birthCoordinate=new Coordinate(4.0,5.0,6.0);
        Coordinate residenceCoordinate=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth = new Place("Bologna", "Via Avesella", "12", birthCoordinate);

        Place placeOfResidence = new Place("Ravenna", "Via Alfredo Baccarini", "3", residenceCoordinate);

        String username="bob45";
        User user = new User(username, "bob@m.com", "Bob", "R", placeOfBirth, placeOfResidence, pictureImage, idCardImage, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));

        UserEntity userEntity=user.toUserEntity(password, picturePath, idPicturePath);

        assertEquals(idPicturePath,userEntity.getIdCard());
        assertEquals(username,userEntity.getUsername());
        assertEquals(DataManagerAdapter.generatePasswordHash(password, userEntity.getSalt()),userEntity.getPassword());
        assertEquals(picturePath,userEntity.getPicture());
        assertEquals("bob@m.com",userEntity.getEmail());
        assertEquals("Bob",userEntity.getFirstname());
        assertEquals("BBORSS80A01L378T",userEntity.getFiscalCode());
        assertEquals(LocalDate.of(1980, 1, 1), DataManagerAdapter.toLocalDateFromDate(userEntity.getDateOfBirth()));
        assertEquals(LocalDate.now(DataManagerAdapter.getZONEID()),DataManagerAdapter.toLocalDateFromDate(userEntity.getDateOfRegistration()));
        assertTrue(userEntity.getPlaceOfBirthEntity().toPlace().isEqual(placeOfBirth));
        assertTrue(userEntity.getPlaceOfResidenceEntity().toPlace().isEqual(placeOfResidence));

    }

    @Test
    public void toUserEntityWithoutPicture() throws ImageReadException {
        String password="userP";

        String picturePath=null;
        String idPicturePath=PICTURESDATA_TEST_PATH+"idBobTest.png";

        BufferedImage pictureImage = null;
        BufferedImage idCardImage = readImageFromResourcesImage(idPicturePath);

        Coordinate birthCoordinate=new Coordinate(4.0,5.0,6.0);
        Coordinate residenceCoordinate=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth = new Place("Bologna", "Via Avesella", "12", birthCoordinate);

        Place placeOfResidence = new Place("Ravenna", "Via Alfredo Baccarini", "3", residenceCoordinate);

        String username="bob45";
        User user = new User(username, "bob@m.com", "Bob", "R", placeOfBirth, placeOfResidence, pictureImage, idCardImage, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));

        UserEntity userEntity=user.toUserEntity(password, picturePath, idPicturePath);

        assertEquals(idPicturePath,userEntity.getIdCard());
        assertEquals(username,userEntity.getUsername());
        assertEquals(DataManagerAdapter.generatePasswordHash(password, userEntity.getSalt()),userEntity.getPassword());
        assertEquals(picturePath,userEntity.getPicture());
        assertEquals("bob@m.com",userEntity.getEmail());
        assertEquals("Bob",userEntity.getFirstname());
        assertEquals("BBORSS80A01L378T",userEntity.getFiscalCode());
        assertEquals(LocalDate.of(1980, 1, 1), DataManagerAdapter.toLocalDateFromDate(userEntity.getDateOfBirth()));
        assertEquals(LocalDate.now(DataManagerAdapter.getZONEID()),DataManagerAdapter.toLocalDateFromDate(userEntity.getDateOfRegistration()));
        assertTrue(userEntity.getPlaceOfBirthEntity().toPlace().isEqual(placeOfBirth));
        assertTrue(userEntity.getPlaceOfResidenceEntity().toPlace().isEqual(placeOfResidence));

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
    public void isEqualTrue() throws ImageReadException {
        String picturePath=PICTURESDATA_TEST_PATH+"pictureBobTest.png";
        String idPicturePath=PICTURESDATA_TEST_PATH+"idBobTest.png";

        BufferedImage pictureImage = readImageFromResourcesImage(picturePath);
        BufferedImage idCardImage = readImageFromResourcesImage(idPicturePath);

        Coordinate birthCoordinate=new Coordinate(4.0,5.0,6.0);
        Coordinate residenceCoordinate=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth = new Place("Bologna", "Via Avesella", "12", birthCoordinate);

        Place placeOfResidence = new Place("Ravenna", "Via Alfredo Baccarini", "3", residenceCoordinate);

        String username="bob45";
        User user = new User(username, "bob@m.com", "Bob", "R", placeOfBirth, placeOfResidence, pictureImage, idCardImage, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));


        String picturePath2=PICTURESDATA_TEST_PATH+"pictureBobTest.png";
        String idPicturePath2=PICTURESDATA_TEST_PATH+"idBobTest.png";

        BufferedImage pictureImage2 = readImageFromResourcesImage(picturePath2);
        BufferedImage idCardImage2 = readImageFromResourcesImage(idPicturePath2);

        Coordinate birthCoordinate2=new Coordinate(4.0,5.0,6.0);
        Coordinate residenceCoordinate2=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth2 = new Place("Bologna", "Via Avesella", "12", birthCoordinate2);

        Place placeOfResidence2 = new Place("Ravenna", "Via Alfredo Baccarini", "3", residenceCoordinate2);

        String username2="bob45";
        User user2 = new User(username2, "bob@m.com", "Bob", "R", placeOfBirth2, placeOfResidence2, pictureImage2, idCardImage2, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));

        assertTrue(user.isEqual(user2));
    }

    @Test
    public void isEqualFalse() throws ImageReadException {
        String picturePath=PICTURESDATA_TEST_PATH+"pictureBobTest.png";
        String idPicturePath=PICTURESDATA_TEST_PATH+"idBobTest.png";

        BufferedImage pictureImage = readImageFromResourcesImage(picturePath);
        BufferedImage idCardImage = readImageFromResourcesImage(idPicturePath);

        Coordinate birthCoordinate=new Coordinate(4.0,5.0,6.1);
        Coordinate residenceCoordinate=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth = new Place("Bologna", "Via Avesella", "12", birthCoordinate);

        Place placeOfResidence = new Place("Ravenna", "Via Alfredo Baccarini", "3", residenceCoordinate);

        String username="bob45";
        User user = new User(username, "bob@m.com", "Bob", "R", placeOfBirth, placeOfResidence, pictureImage, idCardImage, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));


        String picturePath2=PICTURESDATA_TEST_PATH+"pictureBobTest.png";
        String idPicturePath2=PICTURESDATA_TEST_PATH+"idBobTest.png";

        BufferedImage pictureImage2 = readImageFromResourcesImage(picturePath2);
        BufferedImage idCardImage2 = readImageFromResourcesImage(idPicturePath2);

        Coordinate birthCoordinate2=new Coordinate(4.0,5.0,6.0);
        Coordinate residenceCoordinate2=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth2 = new Place("Bologna", "Via Avesella", "12", birthCoordinate2);

        Place placeOfResidence2 = new Place("Ravenna", "Via Alfredo Baccarini", "3", residenceCoordinate2);

        String username2="bob45";
        User user2 = new User(username2, "bob@m.com", "Bob", "R", placeOfBirth2, placeOfResidence2, pictureImage2, idCardImage2, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));

        assertFalse(user.isEqual(user2));
    }

    @Test
    public void isEqualTrueWithoutPictures() throws ImageReadException {
        String idPicturePath=PICTURESDATA_TEST_PATH+"idBobTest.png";

        BufferedImage pictureImage = null;
        BufferedImage idCardImage = readImageFromResourcesImage(idPicturePath);

        Coordinate birthCoordinate=new Coordinate(4.0,5.0,6.0);
        Coordinate residenceCoordinate=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth = new Place("Bologna", "Via Avesella", "12", birthCoordinate);

        Place placeOfResidence = new Place("Ravenna", "Via Alfredo Baccarini", "3", residenceCoordinate);

        String username="bob45";
        User user = new User(username, "bob@m.com", "Bob", "R", placeOfBirth, placeOfResidence, pictureImage, idCardImage, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));

        String idPicturePath2=PICTURESDATA_TEST_PATH+"idBobTest.png";

        BufferedImage pictureImage2 = null;
        BufferedImage idCardImage2 = readImageFromResourcesImage(idPicturePath2);

        Coordinate birthCoordinate2=new Coordinate(4.0,5.0,6.0);
        Coordinate residenceCoordinate2=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth2 = new Place("Bologna", "Via Avesella", "12", birthCoordinate2);

        Place placeOfResidence2 = new Place("Ravenna", "Via Alfredo Baccarini", "3", residenceCoordinate2);

        String username2="bob45";
        User user2 = new User(username2, "bob@m.com", "Bob", "R", placeOfBirth2, placeOfResidence2, pictureImage2, idCardImage2, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));

        assertTrue(user.isEqual(user2));
    }

    @Test
    public void isEqualTrueOneUserWithoutPicture() throws ImageReadException {
        String idPicturePath=PICTURESDATA_TEST_PATH+"idBobTest.png";

        BufferedImage pictureImage = null;
        BufferedImage idCardImage = readImageFromResourcesImage(idPicturePath);

        Coordinate birthCoordinate=new Coordinate(4.0,5.0,6.0);
        Coordinate residenceCoordinate=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth = new Place("Bologna", "Via Avesella", "12", birthCoordinate);

        Place placeOfResidence = new Place("Ravenna", "Via Alfredo Baccarini", "3", residenceCoordinate);

        String username="bob45";
        User user = new User(username, "bob@m.com", "Bob", "R", placeOfBirth, placeOfResidence, pictureImage, idCardImage, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));

        String picturePath2=PICTURESDATA_TEST_PATH+"pictureBobTest.png";
        String idPicturePath2=PICTURESDATA_TEST_PATH+"idBobTest.png";

        BufferedImage pictureImage2 = readImageFromResourcesImage(picturePath2);
        BufferedImage idCardImage2 = readImageFromResourcesImage(idPicturePath2);

        Coordinate birthCoordinate2=new Coordinate(4.0,5.0,6.0);
        Coordinate residenceCoordinate2=new Coordinate(7.0,8.0,9.0);
        Place placeOfBirth2 = new Place("Bologna", "Via Avesella", "12", birthCoordinate2);

        Place placeOfResidence2 = new Place("Ravenna", "Via Alfredo Baccarini", "3", residenceCoordinate2);

        String username2="bob45";
        User user2 = new User(username2, "bob@m.com", "Bob", "R", placeOfBirth2, placeOfResidence2, pictureImage2, idCardImage2, "BBORSS80A01L378T", LocalDate.of(1980, 1, 1));

        assertTrue(user.isEqual(user2));
    }

    @Test
    public void toJSON() {
    }

    @Test
    public void fromJSON() {
    }


}