package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.*;
import com.SafeStreets.exceptions.*;
import com.SafeStreets.model.*;
import com.SafeStreets.modelEntities.MunicipalityEntity;
import com.SafeStreets.modelEntities.UserEntity;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class DataManagerAdapter implements UserDataInterface, MunicipalityDataInterface, ReportsDataInterface {
    private static final Logger LOGGER = Logger.getLogger(DataManagerAdapter.class.getName());

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
    private EntityManager em = emf.createEntityManager();

    private static final String PICTURESDATAPATH="/Users/max/Desktop/forSE2proj/picturesData/";
    private static final int SALTLENGTH=16;

    @Override
    public void addUser(User info, String password) throws ImageStoreException, UserAlreadyPresentException {
        if(exists(info.getUsername()))
            throw new UserAlreadyPresentException();

        String picturePath=saveImage(info.getPicture(), info.getUsername()+"Picture");

        String imageIdCardPath=saveImage(info.getImageIdCard(), info.getUsername()+"IdCard");

        em.persist(info.toUserEntity(password, picturePath, imageIdCardPath));
    }

    private String saveImage(BufferedImage image, String id) throws ImageStoreException {
        if(image==null) {
            return PICTURESDATAPATH + "empty.png";
        }

        String imagePath=PICTURESDATAPATH+id;
        try
        {
            File outputFile = new File(imagePath);
            ImageIO.write(image, "png", outputFile);
        }
        catch (Exception e)
        {
            throw new ImageStoreException();
        }

        return imagePath;
    }

    @Override
    public User getUser(String username, String password) throws WrongPasswordException, UserNotPresentException  {
        UserEntity userEntity=em.find(UserEntity.class, username);

        if(userEntity==null)
            throw new UserNotPresentException();

        if(!verifyPassword(userEntity.getPassword(), userEntity.getSalt(), password))
            throw new WrongPasswordException();

        return userEntity.toUser();
    }

    @Override
    public void addMunicipality(Place place, String username, String password) throws MunicipalityAlreadyPresentException {
        if(exists(username))
            throw new MunicipalityAlreadyPresentException();

        MunicipalityEntity municipalityEntity=new MunicipalityEntity();
        municipalityEntity.setName(username);
        municipalityEntity.setPassSalt(generateSalt());
        municipalityEntity.setPassword(generatePasswordHash(password, municipalityEntity.getPassSalt()));

        em.persist(municipalityEntity);
    }

    @Override
    public Place getMunicipalityArea(String username) throws MunicipalityNotPresentException{
        MunicipalityEntity municipalityEntity=getMunicipalityByUsername(username);

        return municipalityEntity.getPlaceEntity().toPlace();
    }


    @Override
    public boolean checkContractCode(String code) throws MunicipalityNotPresentException {
        MunicipalityEntity municipalityEntity= em.find(MunicipalityEntity.class, code);

        if(municipalityEntity==null)
            throw new MunicipalityNotPresentException();

        return code.equals(municipalityEntity.getContractCode());
    }



    private MunicipalityEntity getMunicipalityByUsername(String name) throws MunicipalityNotPresentException{
        String queryString = "SELECT * FROM Municipality WHERE name=\""+name+"\"";

        TypedQuery<MunicipalityEntity> query = em.createQuery(queryString, MunicipalityEntity.class);

        List<MunicipalityEntity> municipalityEntityList= query.getResultList();

        if(municipalityEntityList==null || municipalityEntityList.get(0)==null)
            throw new MunicipalityNotPresentException();

        return municipalityEntityList.get(0);
    }


    @Override
    public Municipality getMunicipality(String username, String password) throws MunicipalityNotPresentException,WrongPasswordException {
        MunicipalityEntity municipalityEntity = getMunicipalityByUsername(username);

        if(municipalityEntity==null)
            throw new MunicipalityNotPresentException();

        if(!verifyPassword(municipalityEntity.getPassword(), municipalityEntity.getPassSalt(), password))
            throw new WrongPasswordException();

        return municipalityEntity.toMunicipality();
    }

    @Override
    public boolean checkPassword(String username, String password) {
        UserEntity userEntity=em.find(UserEntity.class, username);

        if(userEntity==null) {
            MunicipalityEntity municipalityEntity;
            try {
                municipalityEntity = getMunicipalityByUsername(username);
            } catch (MunicipalityNotPresentException e) {
                return false;
            }

            return verifyPassword(municipalityEntity.getPassword(), municipalityEntity.getPassSalt(), password);
        }

        return verifyPassword(userEntity.getPassword(), userEntity.getSalt(), password);
    }

    @Override
    public boolean exists(String username) {
        boolean isPresent=em.find(UserEntity.class, username)!=null;
        try {
            isPresent=isPresent||getMunicipalityByUsername(username)!=null;
        } catch (MunicipalityNotPresentException ignored) {
            //empty
        }

        return isPresent;
    }

    private boolean verifyPassword(String hash, String salt, String password) {
        String stringToHash=password+salt;
        String hashToVerify = doHash(stringToHash);

        return hash.equals(hashToVerify);
    }

    public static String generatePasswordHash(String password, String salt) {
        return doHash(password+salt);
    }

    public static String generateSalt() {
        return getRandomString(SALTLENGTH);
    }

    private static String doHash(String stringToHash) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(stringToHash.getBytes());
        return Hex.toHexString(digest);
    }

    private static String getRandomString(int length) {
        String result="";
        for(int i=0; i<length;i++)
            result=result+getRandomChar();
        return result;
    }

    private static String  getRandomChar() {
        double d=Math.random()*(126-32)+32;
        int n=(int)d;
        char c=(char)n;
        return Character.toString(c);
    }

    @Override
    public void addUserReport(UserReport userReport) {

    }

    @Override
    public List<Report> getReports(QueryFilter filter) {
        return null;
    }

    @Override
    public List<UserReport> getUserReports(QueryFilter filter) {
        return null;
    }

    @Override
    public String getAggregatedResult(QueryFilter filter) {
        return null;
    }
}
