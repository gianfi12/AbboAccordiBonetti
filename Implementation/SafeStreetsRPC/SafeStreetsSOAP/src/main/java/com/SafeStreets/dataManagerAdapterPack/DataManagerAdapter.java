package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.*;
import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.exceptions.UserNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;
import com.SafeStreets.model.*;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class DataManagerAdapter implements UserDataInterface, MunicipalityDataInterface {
    private final static Logger LOGGER = Logger.getLogger(DataManagerAdapter.class.getName());


    @Override
    public void addUser(User info, String password) {

        String addPlaceOfBirthQuery="INSERT INTO User values";
        String addUserQuery="INSERT INTO User values";


    }

    @Override
    public User getUser(String username, String password) throws WrongPasswordException, UserNotPresentException  {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
        EntityManager em = emf.createEntityManager();

        Vehicle v=em.find(Vehicle.class, "AP234IJ");

        User u = null;

        return u;
    }

    @Override
    public void addMunicipality(Place place, String username, String password) {

    }

    @Override
    public Place getMunicipalityArea(String username) {
        return null;
    }

    @Override
    public void addDataIntegration(String username, DataIntegrationInfo dataIntegrationInfo) {

    }

    @Override
    public Place checkContractCode(String code) {
        return null;
    }

    @Override
    public DataIntegrationInfo getDataIntegrationInfo(Place place) {
        return null;
    }

    @Override
    public List<DataIntegrationInfo> getAllDataIntegrationInfo() {
        return null;
    }

    @Override
    public Municipality getMunicipality(String username, String password) throws MunicipalityNotPresentException,WrongPasswordException {
        return null;
    }

    @Override
    public boolean checkPassword(String username, String password) {
        return false;
    }

    @Override
    public boolean exists(String username) {
        return false;
    }

    private boolean verifyPassword(String hash, String salt, String password) {
        String stringToHash=password+salt;
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(stringToHash.getBytes());
        String hashToVerify = Hex.toHexString(digest);

        return hash.equals(hashToVerify);
    }
}
