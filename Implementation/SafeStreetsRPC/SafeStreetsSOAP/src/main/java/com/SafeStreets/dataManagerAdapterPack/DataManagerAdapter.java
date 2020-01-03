package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.data_analysis_manager.DataAnalysisInterface;
import com.SafeStreets.exceptions.*;
import com.SafeStreets.mapsserviceadapter.MapsServiceInterface;
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
import java.sql.Array;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * An implementation of {@link UserDataInterface}, {@link MunicipalityDataInterface} and {@link ReportsDataInterface}.
 * It manages all the requests to the data needed for the application, in particular it communicates with the DBMS.
 * @author Massimiliano Bonetti
 * @see UserDataInterface
 * @see MunicipalityDataInterface
 * @see ReportsDataInterface
 */
@Stateless
public class DataManagerAdapter implements UserDataInterface, MunicipalityDataInterface, ReportsDataInterface {

    private static final String PERSISTENCE_UNIT_NAME ="manager1";

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static EntityManager em = emf.createEntityManager();

    private static final String PICTURESDATA_PATH ="../picturesData/";
    private static final int SALT_LENGTH =16;

    private static final String PICTURE_FOR_USER ="Picture";
    private static final String IDCARD_FOR_USER ="IdCard";
    private static final String MAIN_PICTURE_FOR_REPORT ="MainPicture";
    private static final String OTHER_PICTURE_FOR_REPORT ="OtherPicture";

    private static final String EMPTY_PICTURE_FILENAME ="empty";
    private static final String PICTURE_FORMAT ="png";
    private static final String DOT =".";

    private static final ZoneId ZONEID=TimeZone.getTimeZone("Europe/Rome").toZoneId();

    private static EntityTransaction transaction=em.getTransaction();

    /**
     * The constructor is hidden outside the package.
     * Use {@link ClientDataInterface#getClientDataInstance()} ()} or
     * use {@link MunicipalityDataInterface#getMunicipalityDataInstance()} ()} or
     * use {@link ReportsDataInterface#getReportsDataInstance()} ()} or
     * use {@link UserDataInterface#getUserDataInstance()} ()}.
     */
    DataManagerAdapter() {
    }

    @Override
    public void addUser(User info, String password) throws ImageStoreException, UserAlreadyPresentException {
        if(exists(info.getUsername()))
            throw new UserAlreadyPresentException();

        String picturePath=saveImage(info.getPicture(), info.getUsername()+ PICTURE_FOR_USER);

        String imageIdCardPath=saveImage(info.getImageIdCard(), info.getUsername()+ IDCARD_FOR_USER);

        transaction.begin();
        em.persist(info.toUserEntity(password, picturePath, imageIdCardPath));
        transaction.commit();
    }

    private String saveImage(BufferedImage image, String filename) throws ImageStoreException {
        if(image==null) {
            return PICTURESDATA_PATH + EMPTY_PICTURE_FILENAME+DOT+PICTURE_FORMAT;
        }

        String imagePath= PICTURESDATA_PATH+filename+DOT+PICTURE_FORMAT;
        try
        {
            File file = new File(PICTURESDATA_PATH);
            file.mkdir();

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

        BufferedImage bufferedImage;
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

        transaction.begin();
        UserEntity userEntity=em.find(UserEntity.class, username);
        transaction.commit();


        if(userEntity==null)
            throw new UserNotPresentException();

        if(!verifyPassword(userEntity.getPassword(), userEntity.getSalt(), password))
            throw new WrongPasswordException();

        return userEntity.toUser();
    }

    @Override
    public void addMunicipality(String contractCode, String username, String password) throws MunicipalityAlreadyPresentException, PlaceForMunicipalityNotPresentException {
        if(exists(username))
            throw new MunicipalityAlreadyPresentException();

        transaction.begin();
        MunicipalityEntity municipalityEntity=em.find(MunicipalityEntity.class, contractCode);
        transaction.commit();

        municipalityEntity.setName(username);
        municipalityEntity.setPassSalt(generateSalt());
        municipalityEntity.setPassword(generatePasswordHash(password, municipalityEntity.getPassSalt()));

        transaction=em.getTransaction();
        transaction.begin();
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
        transaction.begin();
        MunicipalityEntity municipalityEntity= em.find(MunicipalityEntity.class, code);
        transaction.commit();

        return municipalityEntity!=null;
    }



    private MunicipalityEntity getMunicipalityByUsername(String name) throws MunicipalityNotPresentException {
        String queryString = "FROM MunicipalityEntity WHERE name='"+name+"'";

        transaction.begin();
        TypedQuery<MunicipalityEntity> query = em.createQuery(queryString, MunicipalityEntity.class);
        transaction.commit();

        List<MunicipalityEntity> municipalityEntityList= query.getResultList();

        if(municipalityEntityList==null || municipalityEntityList.isEmpty()
                || municipalityEntityList.get(0)==null)
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
        transaction.begin();
        UserEntity userEntity=em.find(UserEntity.class, username);
        transaction.commit();

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
        transaction.begin();
        boolean isPresent=em.find(UserEntity.class, username)!=null;
        transaction.commit();

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

        UserReportEntity userReportEntity=userReport.toUserReportEntity(mainPicturePath);

        transaction.begin();
        em.persist(userReportEntity);
        transaction.commit();

        List<BufferedImage> otherPicturesImages =userReport.getOtherPictures();
        if(otherPicturesImages!=null) {
            String otherPicturePath;
            for(int i=0; i<otherPicturesImages.size(); i++) {
                otherPicturePath=saveImage(otherPicturesImages.get(i), userReport.getAuthorUser().getUsername()+OTHER_PICTURE_FOR_REPORT+i);
                OtherPictureEntity otherPictureEntity=new OtherPictureEntity();
                otherPictureEntity.setPicture(otherPicturePath);
                otherPictureEntity.setUserReportEntity(userReportEntity);

                transaction=em.getTransaction();
                transaction.begin();
                em.persist(otherPictureEntity);
                transaction.commit();
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
        String queryString="";
        if(filter.getPlace().getAddress()==null || filter.getPlace().getAddress().equals(""))
            queryString = "FROM UserReportEntity WHERE reportTimeStamp " +
                    "BETWEEN '"+toTimestampFromLocalDate(filter.getFrom(), true)+
                    "'"+" AND '"+toTimestampFromLocalDate(filter.getUntil(), false) +
                    "' AND place.city='"+filter.getPlace().getCity()+"'";
        else if(filter.getPlace().getHouseCode()==null || filter.getPlace().getHouseCode().equals("")) {
            queryString = "FROM UserReportEntity WHERE reportTimeStamp " +
                    "BETWEEN '"+toTimestampFromLocalDate(filter.getFrom(), true)+
                    "'"+" AND '"+toTimestampFromLocalDate(filter.getUntil(), false) +
                    "' AND place.city='"+filter.getPlace().getCity()+"'" +
                    " AND place.address='"+filter.getPlace().getAddress()+"'";
        } else {
            queryString = "FROM UserReportEntity WHERE reportTimeStamp " +
                    "BETWEEN '"+toTimestampFromLocalDate(filter.getFrom(), true)+
                    "'"+" AND '"+toTimestampFromLocalDate(filter.getUntil(), false) +
                    "' AND place.city='"+filter.getPlace().getCity()+"'" +
                    " AND place.address='"+filter.getPlace().getAddress()+"'" +
                    " AND place.houseCode='"+filter.getPlace().getHouseCode()+"'";
        }

        transaction.begin();
        TypedQuery<UserReportEntity> query = em.createQuery(queryString, UserReportEntity.class);
        transaction.commit();

        List<UserReportEntity> userReportEntityList= query.getResultList();

        List<UserReport> userReportList=new ArrayList<>();

        for (UserReportEntity userReportEntity : userReportEntityList) {
            userReportList.add(userReportEntity.toUserReportWithoutImages());
        }

        return userReportList;
    }

    @Override
    public List<Object[]> getAggregatedResult(QueryFilter filter) {
        if(filter==null || !filter.isApplyQuery() || filter.getQuery()==null || filter.getQuery().equals(""))
            return new ArrayList<>();

        transaction.begin();
        List<Object[]> result=new ArrayList<>();
        if(filter.isOneResult()) {
            List<Object> intermediateResult=em.createQuery(filter.getQuery()).getResultList();
            for(Object object : intermediateResult) {
                Object[] objects=new Object[1];
                objects[0]=object;
                result.add(objects);
            }
        } else {
            result=em.createQuery(filter.getQuery()).getResultList();
        }
        transaction.commit();

        return result;
    }

    public static OffsetDateTime toOffsetDateTimeFromTimestamp(Timestamp timestamp) {
        OffsetDateTime odt = OffsetDateTime.ofInstant(timestamp.toInstant(), ZONEID);
        return odt;
    }

    public static Timestamp toTimestampFromOffsetDateTime(OffsetDateTime odt) {
        Timestamp timestamp = Timestamp.valueOf(odt.atZoneSameInstant(ZONEID).toLocalDateTime());
        return timestamp;
    }

    public static Date toDateFromLocalDate(LocalDate localDate) {
        Date date= Date.valueOf(localDate);
        return date;
    }

    public static LocalDate toLocalDateFromDate(Date date) {
        LocalDate localDate=date.toLocalDate();
        return localDate;
    }

    public static OffsetDateTime toOffsetDateTimeFromLocalDate(LocalDate localDate, boolean isZeroHourAndNot24) {
        return toOffsetDateTimeFromTimestamp(toTimestampFromLocalDate(localDate, isZeroHourAndNot24));
    }

    public static ZoneId getZONEID() {
        return ZONEID;
    }

    public static Timestamp toTimestampFromLocalDate(LocalDate localDate, boolean isZeroHourAndNot24) {
        Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(timestamp.getTime());
        if(isZeroHourAndNot24) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
        } else {
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 50);
            c.set(Calendar.MILLISECOND, 0);
        }
        timestamp.setTime(c.getTimeInMillis());
        return timestamp;
    }
}
