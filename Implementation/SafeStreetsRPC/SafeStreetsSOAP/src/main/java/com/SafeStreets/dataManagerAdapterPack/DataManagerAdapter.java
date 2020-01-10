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
import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * An implementation of {@link UserDataInterface}, {@link MunicipalityDataInterface} and {@link ReportsDataInterface}.
 * It manages all the requests to the data needed for the application, in particular it communicates with the DBMS.
 * @author Massimiliano Bonetti
 * @see ClientDataInterface
 * @see UserDataInterface
 * @see MunicipalityDataInterface
 * @see ReportsDataInterface
 */
@Stateless
public class DataManagerAdapter implements UserDataInterface, MunicipalityDataInterface, ReportsDataInterface {
    /**
     * Name of the persistence unit used to communicate with the DBMS
     */
    private static final String PERSISTENCE_UNIT_NAME ="manager1";
    /**
     * Factory of the entity manager
     */
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    /**
     * Entity manager used to communicate with the DBMS
     */
    private static EntityManager em = emf.createEntityManager();
    /**
     * Directory which contains the images of the users and of the reports
     */
    private static final String PICTURESDATA_PATH ="../picturesData/";
    /**
     * Length of the salt used for the passwords
     */
    private static final int SALT_LENGTH =16;
    /**
     * Part of the name of the picture of a user
     */
    private static final String PICTURE_FOR_USER ="Picture";
    /**
     * Part of the name of the image of the identity card of a user
     */
    private static final String IDCARD_FOR_USER ="IdCard";
    /**
     * Part of the name of the main picture of a user's report
     */
    private static final String MAIN_PICTURE_FOR_REPORT ="MainPictureFromReportOf";
    /**
     * Part of the name of an optional picture of a user's report
     */
    private static final String OTHER_PICTURE_FOR_REPORT ="OtherPictureFromReportOf";
    /**
     * Name of a blank picture
     */
    private static final String EMPTY_PICTURE_FILENAME ="empty";
    /**
     * Format of the pictures
     */
    private static final String PICTURE_FORMAT ="png";
    /**
     * Separator between the picture's name and its format
     */
    private static final String DOT =".";
    /**
     * Identifier of the time zone used in the system
     */
    private static final ZoneId ZONEID=TimeZone.getTimeZone("Europe/Rome").toZoneId();
    /**
     * Entity Transaction used to execute a transaction in the DBMS
     */
    private static EntityTransaction transaction=em.getTransaction();
    /**
     * Conventionally, first day of activity of the system. It is considered as start day when
     * the start day "from" of a filter is null.
     */
    private static final LocalDate FIRST_DATE=LocalDate.of(2019, 10, 1);
    /**
     * The constructor is hidden outside the package.
     * Use {@link ClientDataInterface#getClientDataInstance()} ()} or
     * use {@link MunicipalityDataInterface#getMunicipalityDataInstance()} ()} or
     * use {@link ReportsDataInterface#getReportsDataInstance()} ()} or
     * use {@link UserDataInterface#getUserDataInstance()} ()}.
     */
    DataManagerAdapter() {
    }
    /**
     * It saves the given user in the database with the given password.
     * @param info user to save
     * @param password password of the given user
     * @throws ImageStoreException if there was a problem during the store of the images of the user
     * @throws UserAlreadyPresentException if there exists already a user with the same username of the given one
     */
    @Override
    public void addUser(User info, String password) throws ImageStoreException, UserAlreadyPresentException {
        if(exists(info.getUsername()))
            throw new UserAlreadyPresentException();

        String picturePath=saveImage(info.getPicture(), info.getUsername()+ PICTURE_FOR_USER);

        String imageIdCardPath=saveImage(info.getImageIdCard(), info.getUsername()+ IDCARD_FOR_USER);

        transaction.begin();
        em.merge(info.toUserEntity(password, picturePath, imageIdCardPath));
        transaction.commit();
    }

    /**
     * It saves the given image in the directory PICTURESDATA_PATH with the
     * name filename with format PICTURE_FORMAT
     * @param image image to save
     * @param filename name to give to the image
     * @return path of the saved image
     * @throws ImageStoreException if there was a problem during the store of the image
     */
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

    /**
     * It returns the image corresponding to the given path
     * @param imagePath path in which the image must be read
     * @return image corresponding to the given path
     * @throws ImageReadException if there was a problem during the read of the image
     */
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
    /**
     * It returns the user with the given username, but only if the given password is correct.
     * @param username username of the user to return
     * @param password of the user
     * @return user with the given username, but only if the given password is correct.
     * @throws WrongPasswordException if the given password is wrong
     * @throws UserNotPresentException if the given username is not present in the database
     * @throws ImageReadException if there was a problem during the read of the images of the user
     */
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
    /**
     * It saves a Municipality with the given username and password corresponding to the given contract code.
     * @param contractCode contract code of the Municipality to add
     * @param username username of the Municipality to add
     * @param password password of the Municipality to add
     * @throws MunicipalityAlreadyPresentException if the username is already present
     */
    @Override
    public void addMunicipality(String contractCode, String username, String password) throws MunicipalityAlreadyPresentException {
        if(exists(username))
            throw new MunicipalityAlreadyPresentException();

        transaction.begin();
        MunicipalityEntity municipalityEntity=em.find(MunicipalityEntity.class, contractCode);
        transaction.commit();

        municipalityEntity.setName(username);
        municipalityEntity.setPassSalt(generateSalt());
        municipalityEntity.setPassword(generatePasswordHash(password, municipalityEntity.getPassSalt()));

        transaction.begin();
        em.merge(municipalityEntity);
        transaction.commit();
    }
    /**
     * It returns the place that contains the city of the Municipality with the given username
     * @param username username of the Municipality to consider
     * @return place that contains the city of the Municipality with the given username
     * @throws MunicipalityNotPresentException if no Municipality has the given username
     */
    @Override
    public Place getMunicipalityArea(String username) throws MunicipalityNotPresentException{
        MunicipalityEntity municipalityEntity=getMunicipalityByUsername(username);
        return municipalityEntity.getPlaceEntity().toPlace();
    }

    /**
     * It checks whether the given contract code has been registered
     * @param code contract code to check whether it has been registered
     * @return whether the given contract code has been registered
     */
    @Override
    public boolean checkContractCode(String code) {
        transaction.begin();
        MunicipalityEntity municipalityEntity= em.find(MunicipalityEntity.class, code);
        transaction.commit();

        return municipalityEntity!=null;
    }


    /**
     * It returns the MunicipalityEntity saved in the database corresponding to the given username
     * @param name usernmae of the MunicipalityEntity to return
     * @return MunicipalityEntity corresponding to the given username
     * @throws MunicipalityNotPresentException if no municipality has the given username
     */
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

    /**
     * It returns the Municipality with the given username and if the given password is correct.
     * @param username username of the Municipality to get
     * @param password password of the Municipality to get
     * @return the Municipality with the given username and if the given password is correct.
     * @throws WrongPasswordException if the given password is not correct
     * @throws MunicipalityNotPresentException if no Municipality has the given username
     */
    @Override
    public Municipality getMunicipality(String username, String password) throws MunicipalityNotPresentException,WrongPasswordException {
        MunicipalityEntity municipalityEntity = getMunicipalityByUsername(username);

        if(municipalityEntity==null)
            throw new MunicipalityNotPresentException();

        if(!verifyPassword(municipalityEntity.getPassword(), municipalityEntity.getPassSalt(), password))
            throw new WrongPasswordException();

        return municipalityEntity.toMunicipality();
    }
    /**
     * It checks whether the given password is correct for the given username, which refers to a user or
     * a Municipality
     * @param username username of a user or a Municipality
     * @param password password to check
     * @return whether the given password is correct for the given username, which refers to a user or
     * a Municipality
     */
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
    /**
     * It checks whether the given username refers to a user or a Municipality already registered.
     * @param username username of a user or a Municipality
     * @return whether the given username refers to a user or a Municipality already registered.
     */
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

    /**
     * It verifies whether the hash of password+salt is equal to the given hash
     * @param hash expected hash
     * @param salt salt of the given password
     * @param password password to verify
     * @return whether the hash of password+salt is equal to the given hash
     */
    private boolean verifyPassword(String hash, String salt, String password) {
        String stringToHash=password+salt;
        String hashToVerify = doHash(stringToHash);

        return hash.equals(hashToVerify);
    }

    /**
     * It makes the hash of password+salt. If one string is null, then it is replaced with an empty string
     * @param password password to hash
     * @param salt salt of the given password
     * @return hash of password+salt. If one string is null, then it is replaced with an empty string
     */
    public static String generatePasswordHash(String password, String salt) {
        if(password==null)
            password="";
        if(salt==null)
            salt="";
        return doHash(password+salt);
    }

    /**
     * It returns a random string with length SALT_LENGTH
     * @return random string with length SALT_LENGTH
     */
    public static String generateSalt() {
        return getRandomString(SALT_LENGTH);
    }

    /**
     * It returns the hash SHA3 with 512 bits of the given string
     * @param stringToHash string to hash
     * @return hash SHA3 with 512 bits of the given string
     */
    private static String doHash(String stringToHash) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(stringToHash.getBytes());
        return Hex.toHexString(digest);
    }

    /**
     * It returns a random string with the given length
     * @param length length of the string to generate
     * @return random string with the given length
     */
    private static String getRandomString(int length) {
        StringBuilder result= new StringBuilder();
        for(int i=0; i<length;i++)
            result.append(getRandomChar());
        return result.toString();
    }

    /**
     * It generates a String with a random printable character
     * @return String with a random printable character
     */
    private static String  getRandomChar() {
        double d=Math.random()*(126-32)+32;
        int n=(int)d;
        char c=(char)n;
        return Character.toString(c);
    }
    /**
     * It saves the given report done by a user.
     * @param userReport report to save
     * @throws ImageStoreException if there was a problem during the store of the images of the report
     */
    @Override
    public void addUserReport(UserReport userReport) throws ImageStoreException {

        String mainPicturePath=saveImage(userReport.getMainPicture(), MAIN_PICTURE_FOR_REPORT+userReport.getAuthorUser().getUsername()+Instant.now());

        UserReportEntity userReportEntity=userReport.toUserReportEntity(mainPicturePath);

        transaction.begin();
        em.merge(userReportEntity);
        transaction.commit();

        userReportEntity= findLastUserReport();

        List<BufferedImage> otherPicturesImages =userReport.getOtherPictures();
        if(otherPicturesImages!=null) {
            String otherPicturePath;
            for(int i=0; i<otherPicturesImages.size(); i++) {
                otherPicturePath=saveImage(otherPicturesImages.get(i), OTHER_PICTURE_FOR_REPORT+userReport.getAuthorUser().getUsername()+Instant.now()+i);
                OtherPictureEntity otherPictureEntity=new OtherPictureEntity();
                otherPictureEntity.setPicture(otherPicturePath);
                otherPictureEntity.setUserReportEntity(userReportEntity);

                transaction.begin();
                em.merge(otherPictureEntity);
                transaction.commit();
            }
        }

    }

    /**
     * It returns the user report with the highest id
     * @return UserReportEntity, representing the user report with the highest id
     */
    public UserReportEntity findLastUserReport() {
        String lastUserReportQuery= "SELECT uR " +
                "FROM UserReportEntity AS uR " +
                "WHERE uR.id=(SELECT max(uR2.id) FROM UserReportEntity AS uR2)";

        QueryFilter queryFilter=new QueryFilter(lastUserReportQuery, true);

        List<Object[]> resultList = getAggregatedResult(queryFilter);
        Object[] usersResult = resultList.get(0);

        return (UserReportEntity) usersResult[0];
    }
    /**
     * It returns the reports by applying the given filter.
     * So by considering the city of the filter, the address of the filter if it is not null and
     * the houseCode of the filter if the address and the houseCode are not null
     * @param filter filter to apply to the userReports. In this way are considered the reports of the city of the filter, the address of the filter if it is not null and
     * the houseCode of the filter if the address and the houseCode are not null
     * @return reports done by the user by applying the given filter
     * @throws ImageReadException if it is not possible to read the mainPicture or the otherPictures of the
     * userReports, for example because a path to one image is wrong or because one image is not present
     */
    @Override
    public List<Report> getReports(QueryFilter filter) throws ImageReadException {
        List<UserReport> userReportList=getUserReports(filter);
        List<Report> reportList=new ArrayList<>();

        for(UserReport userReport : userReportList)
            reportList.add(userReport.toReport());

        return reportList;
    }
    /**
     * It returns the reports done by the user by applying the given filter.
     * So by considering the city of the filter, the address of the filter if it is not null and
     * the houseCode of the filter if the address and the houseCode are not null
     * @param filter filter to apply to the userReports. In this way are considered the reports of the city of the filter, the address of the filter if it is not null and
     * the houseCode of the filter if the address and the houseCode are not null
     * @return reports done by the user by applying the given filter
     * @throws ImageReadException if it is not possible to read the mainPicture or the otherPictures of the
     * userReports, for example because a path to one image is wrong or because one image is not present
     */
    @Override
    public List<UserReport> getUserReports(QueryFilter filter) throws ImageReadException {
        String queryString;

        LocalDate from=filter.getFrom();
        LocalDate until=filter.getUntil();

        if(from==null)
            from=FIRST_DATE;

        if(until==null)
            until=LocalDate.now(DataManagerAdapter.getZONEID());



        if(filter.getPlace().getAddress()==null || filter.getPlace().getAddress().equals(""))
            queryString = "FROM UserReportEntity WHERE reportTimeStamp " +
                    "BETWEEN '"+toTimestampFromLocalDate(from, true)+
                    "'"+" AND '"+toTimestampFromLocalDate(until, false) +
                    "' AND place.city='"+getStdStringForCity(filter.getPlace().getCity())+"'";
        else if(filter.getPlace().getHouseCode()==null || filter.getPlace().getHouseCode().equals("")) {
            queryString = "FROM UserReportEntity WHERE reportTimeStamp " +
                    "BETWEEN '"+toTimestampFromLocalDate(from, true)+
                    "'"+" AND '"+toTimestampFromLocalDate(until, false) +
                    "' AND place.city='"+getStdStringForCity(filter.getPlace().getCity())+"'" +
                    " AND place.address='"+filter.getPlace().getAddress()+"'";
        } else {
            queryString = "FROM UserReportEntity WHERE reportTimeStamp " +
                    "BETWEEN '"+toTimestampFromLocalDate(from, true)+
                    "'"+" AND '"+toTimestampFromLocalDate(until, false) +
                    "' AND place.city='"+getStdStringForCity(filter.getPlace().getCity())+"'" +
                    " AND place.address='"+filter.getPlace().getAddress()+"'" +
                    " AND place.houseCode='"+filter.getPlace().getHouseCode()+"'";
        }

        transaction.begin();
        TypedQuery<UserReportEntity> query = em.createQuery(queryString, UserReportEntity.class);
        transaction.commit();

        List<UserReportEntity> userReportEntityList= query.getResultList();

        List<UserReport> userReportList=new ArrayList<>();

        for (UserReportEntity userReportEntity : userReportEntityList) {

            String otherPicturesQueryString="FROM OtherPictureEntity WHERE userReportEntity="+userReportEntity.getId();

            transaction.begin();
            TypedQuery<OtherPictureEntity> otherPicturesQuery = em.createQuery(otherPicturesQueryString, OtherPictureEntity.class);
            transaction.commit();

            List<OtherPictureEntity> otherPictureEntities=otherPicturesQuery.getResultList();

            userReportList.add(userReportEntity.toUserReportWithImages(otherPictureEntities));

        }

        return userReportList;
    }
    /**
     * It returns the result of the query contained in the given filter.
     * @param filter filter which contains the query to do to the database
     * @return a list of Object[], each element of the list contains one result of the query
     */
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

    /**
     * It converts the given Timestamp to an OffsetDateTime
     * @param timestamp Timestamp to convert
     * @return OffsetDateTime corresponding to the given Timestamp
     */
    public static OffsetDateTime toOffsetDateTimeFromTimestamp(Timestamp timestamp) {
        OffsetDateTime odt = OffsetDateTime.ofInstant(timestamp.toInstant(), ZONEID);
        return odt;
    }

    /**
     * It returns the Timestamp converted from the given OffsetDateTime
     * @param odt OffsetDateTime to convert
     * @return Timestamp converted from the given OffsetDateTime
     */
    public static Timestamp toTimestampFromOffsetDateTime(OffsetDateTime odt) {
        Timestamp timestamp = Timestamp.valueOf(odt.atZoneSameInstant(ZONEID).toLocalDateTime());
        return timestamp;
    }

    /**
     * It returns the Date converted from the given LocalDate
     * @param localDate LocalDate to convert
     * @return Date converted from the given LocalDate
     */
    public static Date toDateFromLocalDate(LocalDate localDate) {
        Date date= Date.valueOf(localDate);
        return date;
    }

    /**
     * It returns the LocalDate converted from the given Date
     * @param date Date to convert
     * @return LocalDate converted from the given Date
     */
    public static LocalDate toLocalDateFromDate(Date date) {
        LocalDate localDate=date.toLocalDate();
        return localDate;
    }

    /**
     * It returns the OffsetDateTime with date localDate and hour, minute and seconds:
     * 00:00:00 if isZeroHourAndNot24 is true
     * 23:59:50 if isZeroHourAndNot24 is false
     * @param localDate date of the returned OffsetDateTime
     * @param isZeroHourAndNot24 it indicates whether to consider the start of the day or the end
     * @return OffsetDateTime with date localDate and hour, minute and seconds:
     * 00:00:00 if isZeroHourAndNot24 is true
     * 23:59:50 if isZeroHourAndNot24 is false
     */
    public static OffsetDateTime toOffsetDateTimeFromLocalDate(LocalDate localDate, boolean isZeroHourAndNot24) {
        return toOffsetDateTimeFromTimestamp(toTimestampFromLocalDate(localDate, isZeroHourAndNot24));
    }

    /**
     * It returns the id of the time zone used in the system
     * @return id of the time zone used in the system
     */
    public static ZoneId getZONEID() {
        return ZONEID;
    }

    /**
     * It returns the Timestamp with the given date localDate and hour, minute and seconds:
     * 00:00:00 if isZeroHourAndNot24 is true
     * 23:59:50 if isZeroHourAndNot24 is false
     * @param localDate date of the returned Timestamp
     * @param isZeroHourAndNot24 it indicates whether to consider the start of the day or the end
     * @return Timestamp with the given date localDate and hour, minute and seconds:
     * 00:00:00 if isZeroHourAndNot24 is true
     * 23:59:50 if isZeroHourAndNot24 is false
     */
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

    /**
     * It returns the given string city but with the first character in upper case and the other characters in lower case
     * @param city city to which to adjust the characters
     * @return string city but with the first character in upper case and the other characters in lower case
     */
    public static String getStdStringForCity(String city) {
        if(city==null || city.isEmpty())
            return "";

        city=city.toLowerCase();
        String firstChar=Character.toString(city.charAt(0)).toUpperCase();

        return firstChar+city.substring(1);
    }
}
