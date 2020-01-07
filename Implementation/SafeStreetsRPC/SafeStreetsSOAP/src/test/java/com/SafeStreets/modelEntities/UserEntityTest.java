package com.SafeStreets.modelEntities;

import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserEntityTest {

    @Test
    public void toUser() throws ImageReadException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        UserEntity userEntity=em.find(UserEntity.class, "smith40");
        transaction.commit();

        User user=userEntity.toUser();

        assertEquals("smith40",user.getUsername());
        assertEquals("smith40@m.com",user.getEmail());
        assertEquals("Tim",user.getFirstName());
        assertEquals("Smith",user.getLastName());
        assertEquals("Milano",user.getPlaceOfBirth().getCity());
        assertNull(user.getPlaceOfBirth().getAddress());
        assertNull(user.getPlaceOfBirth().getHouseCode());
        assertTrue(45.4769300000==user.getPlaceOfBirth().getCoordinate().getLatitude());
        assertTrue(9.2322900000==user.getPlaceOfBirth().getCoordinate().getLongitude());
        assertTrue(122.0000000000==user.getPlaceOfBirth().getCoordinate().getAltitude());

        assertEquals("Milano",user.getPlaceOfResidence().getCity());
        assertEquals("Piazza della Scala",user.getPlaceOfResidence().getAddress());
        assertEquals("2",user.getPlaceOfResidence().getHouseCode());
        assertTrue(45.4667800000==user.getPlaceOfResidence().getCoordinate().getLatitude());
        assertTrue(9.1904000000==user.getPlaceOfResidence().getCoordinate().getLongitude());
        assertTrue(122.0000000000==user.getPlaceOfResidence().getCoordinate().getAltitude());

        assertNotNull(user.getPicture());
        assertNotNull(user.getImageIdCard());
        assertEquals("SMTTMI90M01F205P",user.getFiscalCode());
        assertEquals(LocalDate.of(1990,8,1),user.getDateOfBirth());
    }

    @Test
    public void toUserFromUserEntityWithoutPicture() throws ImageReadException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        UserEntity userEntity=em.find(UserEntity.class, "smith40");
        transaction.commit();

        userEntity.setPicture(null);

        User user=userEntity.toUser();

        assertEquals("smith40",user.getUsername());
        assertEquals("smith40@m.com",user.getEmail());
        assertEquals("Tim",user.getFirstName());
        assertEquals("Smith",user.getLastName());
        assertEquals("Milano",user.getPlaceOfBirth().getCity());
        assertNull(user.getPlaceOfBirth().getAddress());
        assertNull(user.getPlaceOfBirth().getHouseCode());
        assertTrue(45.4769300000==user.getPlaceOfBirth().getCoordinate().getLatitude());
        assertTrue(9.2322900000==user.getPlaceOfBirth().getCoordinate().getLongitude());
        assertTrue(122.0000000000==user.getPlaceOfBirth().getCoordinate().getAltitude());

        assertEquals("Milano",user.getPlaceOfResidence().getCity());
        assertEquals("Piazza della Scala",user.getPlaceOfResidence().getAddress());
        assertEquals("2",user.getPlaceOfResidence().getHouseCode());
        assertTrue(45.4667800000==user.getPlaceOfResidence().getCoordinate().getLatitude());
        assertTrue(9.1904000000==user.getPlaceOfResidence().getCoordinate().getLongitude());
        assertTrue(122.0000000000==user.getPlaceOfResidence().getCoordinate().getAltitude());

        assertNull(user.getPicture());
        assertNotNull(user.getImageIdCard());
        assertEquals("SMTTMI90M01F205P",user.getFiscalCode());
        assertEquals(LocalDate.of(1990,8,1),user.getDateOfBirth());
    }
}