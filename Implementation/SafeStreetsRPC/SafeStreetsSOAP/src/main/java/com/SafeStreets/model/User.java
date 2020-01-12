package com.SafeStreets.model;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.mapsserviceadapter.GeocodeException;
import com.SafeStreets.mapsserviceadapter.MapsServiceInterface;
import com.SafeStreets.modelEntities.UserEntity;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * It represents a user
 */
public class User {
    /**
     * This is used to print log message if an error occurs
     */
    private final static Logger LOGGER = Logger.getLogger(User.class.getName());
    /**
     * username of the user
     */
    private String username;
    /**
     * email
     */
    private String email;
    /**
     * first name
     */
    private String firstName;
    /**
     * last name
     */
    private String lastName;
    /**
     * place of birth
     */
    private Place placeOfBirth;
    /**
     * place of residence
     */
    private Place placeOfResidence;
    /**
     * picture of the user
     */
    private BufferedImage picture;
    /**
     * picture of the identity card of the user
     */
    private BufferedImage imageIdCard;
    /**
     * fiscal code
     */
    private String fiscalCode;
    /**
     * date of birth
     */
    private LocalDate dateOfBirth;

    /**
     * It creates one User with the given parameters
     * @param username username of the user
     * @param email email
     * @param firstName first name
     * @param lastName last name
     * @param placeOfBirth place of birth
     * @param placeOfResidence place of residence
     * @param picture picture of the user
     * @param imageIdCard picture of the identity card
     * @param fiscalCode fiscal code
     * @param dateOfBirth date of birth
     */
    public User(String username, String email, String firstName, String lastName, Place placeOfBirth, Place placeOfResidence, BufferedImage picture, BufferedImage imageIdCard, String fiscalCode, LocalDate dateOfBirth) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.placeOfBirth = placeOfBirth;
        this.placeOfResidence = placeOfResidence;
        this.picture=picture;
        this.imageIdCard = imageIdCard;
        this.fiscalCode = fiscalCode;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * It returns the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * It returns the email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * It returns the firstName
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * It returns the lastName
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * It returns the placeOfBirth
     * @return placeOfBirth
     */
    public Place getPlaceOfBirth() {
        return placeOfBirth;
    }

    /**
     * It returns the placeOfResidence
     * @return placeOfResidence
     */
    public Place getPlaceOfResidence() {
        return placeOfResidence;
    }

    /**
     * It returns the picture
     * @return picture
     */
    public BufferedImage getPicture() {
        return picture;
    }

    /**
     * It returns the imageIdCard
     * @return imageIdCard
     */
    public BufferedImage getImageIdCard() {
        return imageIdCard;
    }

    /**
     * It returns the fiscalCode
     * @return fiscalCode
     */
    public String getFiscalCode() {
        return fiscalCode;
    }

    /**
     * It returns the dateOfBirth
     * @return dateOfBirth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * It returns the UserEntity converted from this object
     * @param password password of the user
     * @param picturePath path to the picture of the user
     * @param imageIdCardPath path to the picture of the identity card of the user
     * @return UserEntity converted from this object
     */
    public UserEntity toUserEntity(String password, String picturePath, String imageIdCardPath) {
        UserEntity newUserEntity=new UserEntity();

        newUserEntity.setUsername(getUsername());
        newUserEntity.setEmail(getEmail());
        newUserEntity.setFirstname(getFirstName());
        newUserEntity.setLastname(getLastName());


        newUserEntity.setPlaceOfBirthEntity(getPlaceOfBirth().toPlaceEntity());
        newUserEntity.setPlaceOfResidenceEntity(getPlaceOfResidence().toPlaceEntity());

        newUserEntity.setPicture(picturePath);
        newUserEntity.setIdCard(imageIdCardPath);

        newUserEntity.setFiscalCode(getFiscalCode());

        newUserEntity.setDateOfBirth(DataManagerAdapter.toDateFromLocalDate(getDateOfBirth()));
        newUserEntity.setSalt(DataManagerAdapter.generateSalt());
        newUserEntity.setPassword(DataManagerAdapter.generatePasswordHash(password, newUserEntity.getSalt()));

        newUserEntity.setDateOfRegistration(DataManagerAdapter.toDateFromLocalDate(LocalDate.now(DataManagerAdapter.getZONEID())));


        return newUserEntity;
    }

    /**
     * It indicates whether the given user has the same attributes of this one
     * @param userToCompare user to compare with this one
     * @return whether the given user has the same attributes of this one
     */
    public boolean isEqual(User userToCompare) {
        return username.equals(userToCompare.username)&&email.equals(userToCompare.email)
                &&firstName.equals(userToCompare.firstName)
                &&lastName.equals(userToCompare.lastName)
                &&placeOfBirth.isEqual(userToCompare.placeOfBirth)
                &&placeOfResidence.isEqual(userToCompare.placeOfResidence)
                &&fiscalCode.equals(userToCompare.fiscalCode)
                &&dateOfBirth.isEqual(userToCompare.dateOfBirth);
    }

    /**
     * This method is used in order to get a json string that represents the User instance
     * @return Is the json string that represents the User
     */
    public String toJSON(){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStream b64 = new Base64OutputStream(os);
        ByteArrayOutputStream os1 = new ByteArrayOutputStream();
        OutputStream b641 = new Base64OutputStream(os1);
        try {
            ImageIO.write(picture, "jpg", b64);
            ImageIO.write(picture, "jpg", b641);
            //String result = os.toString("UTF-8");
        }catch (IOException e){
            LOGGER.log(Level.SEVERE,"Error with user image!");
            return null;
        }
        try {
            UserSend userSend = new UserSend(username, email, firstName, lastName, placeOfBirth, placeOfResidence, os.toString("UTF-8"), os1.toString("UTF-8"), fiscalCode, dateOfBirth);
            Gson json = new Gson();
            return json.toJson(userSend);
        }catch (UnsupportedEncodingException e){
            LOGGER.log(Level.SEVERE,"Error with user image!");
            return null;
        }


    }

    /**
     * This is an intermediate class used to transform the instance of the user inside a jsons string
     */
     class UserSend {
        /**
         * These are the same attributes define in the previous User class
         */
        private String username;
         private String email;
         private String firstName;
         private String lastName;
         private Place placeOfBirth;
         private Place placeOfResidence;
         private String picture;
         private String imageIdCard;
         private String fiscalCode;
         private LocalDate dateOfBirth;

        /**
         * This is the constructor of the class that takes the same arguments of the User class but in a serializable way
         */
         public UserSend(String username, String email, String firstName, String lastName, Place placeOfBirth, Place placeOfResidence, String picture, String imageIdCard, String fiscalCode, LocalDate dateOfBirth) {
             this.username = username;
             this.email = email;
             this.firstName = firstName;
             this.lastName = lastName;
             this.placeOfBirth = placeOfBirth;
             this.placeOfResidence = placeOfResidence;
             this.picture = picture;
             this.imageIdCard = imageIdCard;
             this.fiscalCode = fiscalCode;
             this.dateOfBirth = dateOfBirth;
         }

        /**
         * These are the different getter of the attributes of the class
         */
         public String getUsername() {
             return username;
         }

         public String getEmail() {
             return email;
         }

         public String getFirstName() {
             return firstName;
         }

         public String getLastName() {
             return lastName;
         }

         public Place getPlaceOfBirth() {
             return placeOfBirth;
         }

         public Place getPlaceOfResidence() {
             return placeOfResidence;
         }

         public String getPicture() {
             return picture;
         }

         public String getImageIdCard() {
             return imageIdCard;
         }

         public String getFiscalCode() {
             return fiscalCode;
         }

         public LocalDate getDateOfBirth() {
             return dateOfBirth;
         }

     }

    /**
     * This method takes a json string that represents a User and it returns the User instance
     * @param info Is the json string that represents the User
     * @return Is the instance of the User obtained by the given string
     */
     public static User fromJSON(String info){
         JSONObject obj = new JSONObject(info);
         String username = obj.getString("username");
         String email = obj.getString("email");
         String firstname = obj.getString("firstName");
         String lastname = obj.getString("lastName");
         String placeOfBirthString = obj.getString("placeOfBirth");
         String placeOfResidenceString = obj.getString("placeOfResidence");
         String picture = obj.getString("picture");
         String idCard = obj.getString("idCard");
         String fiscalCode = obj.getString("fiscalCode");
         String dateOfBirth = obj.getString("dateOfBirth");

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         LocalDate date = LocalDate.parse(dateOfBirth.substring(0,dateOfBirth.indexOf(" ")), formatter);
         MapsServiceInterface mapsService = MapsServiceInterface.getInstance();
         try {
             Place placeOfBirth = mapsService.geocoding(placeOfBirthString);
             Place placeOfResidence = mapsService.geocoding(placeOfResidenceString);

             try {
                 BufferedImage image;
                 byte[] imageByte;
                 BASE64Decoder decoder = new BASE64Decoder();
                 imageByte = decoder.decodeBuffer(picture);
                 ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                 image = ImageIO.read(bis);
                 bis.close();

                 BufferedImage image1;
                 byte[] imageByte1;
                 BASE64Decoder decoder1 = new BASE64Decoder();
                 imageByte1 = decoder1.decodeBuffer(idCard);
                 ByteArrayInputStream bis1 = new ByteArrayInputStream(imageByte1);
                 image1 = ImageIO.read(bis1);
                 bis1.close();
                 return new User(username, email, firstname, lastname, placeOfBirth, placeOfResidence, image, image1, fiscalCode, date);
             } catch (IOException e) {
                 LOGGER.log(Level.SEVERE, "Error decode user image!");
             }
         }catch(GeocodeException e){
             LOGGER.log(Level.SEVERE,"Error geocoding!");
         }
         return null;
     }
}
