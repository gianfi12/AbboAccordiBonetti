package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.exceptions.*;
import com.SafeStreets.model.*;
import com.SafeStreets.modelEntities.MunicipalityEntity;
import com.SafeStreets.modelEntities.OtherPictureEntity;
import com.SafeStreets.modelEntities.UserEntity;
import com.SafeStreets.modelEntities.UserReportEntity;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class DataManagerAdapter implements UserDataInterface, MunicipalityDataInterface, ReportsDataInterface {
    private static final Logger LOGGER = Logger.getLogger(DataManagerAdapter.class.getName());

    private static final String PERSISTENCE_UNIT_NAME ="manager1";

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private EntityManager em = emf.createEntityManager();

    private static final String PICTURESDATA_PATH ="/Users/max/Desktop/forSE2proj/picturesData/";
    private static final int SALT_LENGTH =16;

    private static final String PICTURE_FOR_USER ="Picture";
    private static final String IDCARD_FOR_USER ="IdCard";
    private static final String MAIN_PICTURE_FOR_REPORT ="MainPicture";
    private static final String OTHER_PICTURE_FOR_REPORT ="OtherPicture";

    private static final String EMPTY_PICTURE_FILENAME ="empty";
    private static final String PICTURE_FORMAT ="png";
    private static final String DOT =".";


    @Override
    public void addUser(User info, String password) throws ImageStoreException, UserAlreadyPresentException {
        if(exists(info.getUsername()))
            throw new UserAlreadyPresentException();

        String picturePath=saveImage(info.getPicture(), info.getUsername()+ PICTURE_FOR_USER);

        String imageIdCardPath=saveImage(info.getImageIdCard(), info.getUsername()+ IDCARD_FOR_USER);

        em.getTransaction().begin();
        em.persist(info.toUserEntity(password, picturePath, imageIdCardPath));
        em.getTransaction().commit();
    }

    private String saveImage(BufferedImage image, String filename) throws ImageStoreException {
        if(image==null) {
            return PICTURESDATA_PATH + EMPTY_PICTURE_FILENAME+DOT+PICTURE_FORMAT;
        }

        String imagePath= PICTURESDATA_PATH+filename+DOT+PICTURE_FORMAT;
        try
        {
            File outputFile = new File(imagePath);
            ImageIO.write(image, PICTURE_FORMAT, outputFile);
        }
        catch (Exception e)
        {
            throw new ImageStoreException();
        }

        return imagePath;
    }

    public static BufferedImage readImage(String imagePath) throws ImageReadException {

        BufferedImage bufferedImage = null;
        try
        {
            bufferedImage = ImageIO.read(new File(imagePath));
        }
        catch (Exception e)
        {
            throw new ImageReadException();
        }

        return bufferedImage;
    }

    @Override
    public User getUser(String username, String password) throws WrongPasswordException, UserNotPresentException, ImageReadException {
        em.getTransaction().begin();
        UserEntity userEntity=em.find(UserEntity.class, username);
        em.getTransaction().commit();


        if(userEntity==null)
            throw new UserNotPresentException();

        if(!verifyPassword(userEntity.getPassword(), userEntity.getSalt(), password))
            throw new WrongPasswordException();

        return userEntity.toUser();
    }

    @Override
    public void addMunicipality(Place place, String username, String password) throws MunicipalityAlreadyPresentException, PlaceForMunicipalityNotPresentException {
        if(exists(username))
            throw new MunicipalityAlreadyPresentException();

        MunicipalityEntity municipalityEntity=getMunicipalityByPlace(place.getCity());
        municipalityEntity.setName(username);
        municipalityEntity.setPassSalt(generateSalt());
        municipalityEntity.setPassword(generatePasswordHash(password, municipalityEntity.getPassSalt()));

        em.getTransaction().begin();
        em.persist(municipalityEntity);
        em.getTransaction().commit();
    }

    @Override
    public Place getMunicipalityArea(String username) throws MunicipalityNotPresentException{
        MunicipalityEntity municipalityEntity=getMunicipalityByUsername(username);

        return municipalityEntity.getPlaceEntity().toPlace();
    }


    @Override
    public boolean checkContractCode(String code) {
        em.getTransaction().begin();
        MunicipalityEntity municipalityEntity= em.find(MunicipalityEntity.class, code);
        em.getTransaction().commit();

        return municipalityEntity!=null;
    }



    private MunicipalityEntity getMunicipalityByUsername(String name) throws MunicipalityNotPresentException {
        String queryString = "FROM MunicipalityEntity WHERE name='"+name+"'";

        TypedQuery<MunicipalityEntity> query = em.createQuery(queryString, MunicipalityEntity.class);

        List<MunicipalityEntity> municipalityEntityList= query.getResultList();

        if(municipalityEntityList==null || municipalityEntityList.isEmpty()
                || municipalityEntityList.get(0)==null)
            throw new MunicipalityNotPresentException();

        return municipalityEntityList.get(0);
    }

    private MunicipalityEntity getMunicipalityByPlace(String city) throws PlaceForMunicipalityNotPresentException {
        String queryString = "FROM MunicipalityEntity WHERE placeEntity.city='"+city+"'";

        TypedQuery<MunicipalityEntity> query = em.createQuery(queryString, MunicipalityEntity.class);

        List<MunicipalityEntity> municipalityEntityList= query.getResultList();

        if(municipalityEntityList==null || municipalityEntityList.isEmpty()
                || municipalityEntityList.get(0)==null)
            throw new PlaceForMunicipalityNotPresentException();

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
        em.getTransaction().begin();
        UserEntity userEntity=em.find(UserEntity.class, username);
        em.getTransaction().commit();

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
        em.getTransaction().begin();
        boolean isPresent=em.find(UserEntity.class, username)!=null;
        em.getTransaction().commit();

        if(isPresent)
            return true;

        try {
            isPresent=getMunicipalityByUsername(username)!=null;
        } catch (MunicipalityNotPresentException ignored) {
            isPresent=false;
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
        return getRandomString(SALT_LENGTH);
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
    public void addUserReport(UserReport userReport) throws ImageStoreException {

        String mainPicturePath=saveImage(userReport.getMainPicture(), userReport.getAuthorUser().getUsername()+ MAIN_PICTURE_FOR_REPORT);

        em.getTransaction().begin();
        UserReportEntity userReportEntity=userReport.toUserReportEntity(mainPicturePath);
        em.persist(userReportEntity);
        em.getTransaction().commit();

        List<BufferedImage> otherPicturesImages =userReport.getOtherPictures();
        if(otherPicturesImages!=null) {
            String otherPicturePath;
            for(int i=0; i<otherPicturesImages.size(); i++) {
                otherPicturePath=saveImage(otherPicturesImages.get(i), userReport.getAuthorUser().getUsername()+OTHER_PICTURE_FOR_REPORT+i);
                OtherPictureEntity otherPictureEntity=new OtherPictureEntity();
                otherPictureEntity.setPicture(otherPicturePath);
                otherPictureEntity.setUserReportEntity(userReportEntity);

                em.getTransaction().begin();
                em.persist(otherPictureEntity);
                em.getTransaction().commit();
            }
        }

    }

    @Override
    public List<Report> getReports(QueryFilter filter) throws ImageReadException {
        List<UserReport> userReportList=getUserReports(filter);
        List<Report> reportList=new ArrayList<>();

        for(UserReport userReport : userReportList)
            reportList.add(userReport.toReport());

        return reportList;
    }

    @Override
    public List<UserReport> getUserReports(QueryFilter filter) throws ImageReadException {
        String queryString = "FROM UserReportEntity WHERE reportTimeStamp " +
                "BETWEEN '"+filter.getFrom()+"'"+" AND '"+filter.getUntil() +
                "' AND place.city='"+filter.getPlace().getCity()+"'";

        TypedQuery<UserReportEntity> query = em.createQuery(queryString, UserReportEntity.class);

        List<UserReportEntity> userReportEntityList= query.getResultList();

        List<UserReport> userReportList=new ArrayList<>();

        for (UserReportEntity userReportEntity : userReportEntityList) {
            userReportList.add(userReportEntity.toUserReportWithoutImages());
        }

        return userReportList;
    }

    @Override
    public String getAggregatedResult(QueryFilter filter) {
        return null;
    }
}
