package com.SafeStreets.model;

import com.SafeStreets.Dispatcher;
import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.mapsserviceadapter.GeocodeException;
import com.SafeStreets.mapsserviceadapter.MapsServiceInterface;
import com.SafeStreets.modelEntities.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Type;
import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    /**
     * This is used to print log message if an error occurs
     */
    private final static Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Place placeOfBirth;
    private Place placeOfResidence;
    private BufferedImage picture;
    private BufferedImage imageIdCard;
    private String fiscalCode;
    private LocalDate dateOfBirth;

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

    public BufferedImage getPicture() {
        return picture;
    }

    public BufferedImage getImageIdCard() {
        return imageIdCard;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

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

        return newUserEntity;
    }

    public boolean isEqual(User userToCompare) {
        return username.equals(userToCompare.username)&&email.equals(userToCompare.email)
                &&firstName.equals(userToCompare.firstName)
                &&lastName.equals(userToCompare.lastName)
                &&placeOfBirth.isEqual(userToCompare.placeOfBirth)
                &&placeOfResidence.isEqual(userToCompare.placeOfResidence)
                &&fiscalCode.equals(userToCompare.fiscalCode)
                &&dateOfBirth.isEqual(userToCompare.dateOfBirth);
    }

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
     class UserSend {
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
