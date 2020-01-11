package com.SafeStreets.model;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.modelEntities.UserReportEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * It represents a report done by a user
 */
public class UserReport extends Report{
    /**
     * This is used to print log message if an error occurs
     */
    private static final Logger LOGGER = Logger.getLogger(UserReport.class.getName());
    /**
     * Author of the report
     */
    private User authorUser;
    /**
     * Main picture
     */
    private BufferedImage mainPicture;
    /**
     * Optional pictures
     */
    private List<BufferedImage> otherPictures;
    /**
     * Is the images format used to save the photo on the server
     */
    private final String IMAGE_TYPE="png";

    /**
     * It creates a UserReport with the given parameters
     * @param reportOffsetDateTime Time of when the report has been done
     * @param odtOfWatchedViolation Time of when the violation has been seen, if it is null then it is assumed that the report
     * has been done at the moment in which the violation has been seen
     * @param place Place in which the violation happen
     * @param violationType Type of the reported violation
     * @param description Description of the report
     * @param vehicle Vehicle that has been accused to have done the violation
     * @param authorUser Author of the report
     * @param mainPicture Main picture
     * @param otherPictures Optional pictures
     */
    public UserReport(OffsetDateTime reportOffsetDateTime, OffsetDateTime odtOfWatchedViolation, Place place, ViolationType violationType, String description, Vehicle vehicle, User authorUser, BufferedImage mainPicture, List<BufferedImage> otherPictures) {
        super(reportOffsetDateTime, odtOfWatchedViolation, place, violationType, description, vehicle);
        this.authorUser = authorUser;
        this.mainPicture = mainPicture;
        this.otherPictures = otherPictures;
    }

    /**
     * It returns the authorUser
     * @return authorUser
     */
    public User getAuthorUser() {
        return authorUser;
    }
    /**
     * It returns the mainPicture
     * @return mainPicture
     */
    public BufferedImage getMainPicture() {
        return mainPicture;
    }
    /**
     * It returns the otherPictures
     * @return otherPictures
     */
    public List<BufferedImage> getOtherPictures() {
        return otherPictures;
    }

    /**
     * It returns the UserReportEntity converted from this object
     * @param mainPicturePath path to the main picture
     * @return UserReportEntity converted from this object
     */
    public UserReportEntity toUserReportEntity(String mainPicturePath) {
        UserReportEntity userReportEntity=new UserReportEntity();
        userReportEntity.setReportTimeStamp(DataManagerAdapter.toTimestampFromOffsetDateTime(getReportOffsetDateTime()));

        if(getOdtOfWatchedViolation()!=null)
            userReportEntity.setTimeStampOfWatchedViolation(DataManagerAdapter.toTimestampFromOffsetDateTime(getOdtOfWatchedViolation()));

        userReportEntity.setViolationType(getViolationType().toString());
        userReportEntity.setDescription(getDescription());
        userReportEntity.setMainPicture(mainPicturePath);
        userReportEntity.setPlace(getPlace().toPlaceEntity());
        userReportEntity.setVehicleEntity(getVehicle().toVehicleEntity());
        userReportEntity.setUserEntity(getAuthorUser().toUserEntity("","",""));

        return userReportEntity;
    }

    /**
     * It returns the Report converted from this object
     * @return Report converted from this object
     */
    public Report toReport() {
            return new Report(getReportOffsetDateTime(), getOdtOfWatchedViolation(), getPlace(), getViolationType(),
                    getDescription(), getVehicle());
    }

    /**
     * This method is used to reconstruct a User report that comes from the client, the definition of the user class is defined as a json string
     * with the structure presented in this method and with the required parameters
     * @param jsonstring Is the correctly fomatted json string
     * @param user is the user that has sent this report
     * @return Is the reconstructed user report
     */
    public static UserReport fromJSON(String jsonstring,User user){
        Report report = Report.fromJSON(jsonstring);
        JSONObject obj = new JSONObject(jsonstring);
        String mainPicture = obj.getString("mainPicture");

        byte[] imageByte;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            imageByte = decoder.decodeBuffer(mainPicture);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            BufferedImage newmainPicture = ImageIO.read(bis);
            bis.close();

            List<BufferedImage> newotherPictures = new ArrayList<>();
            JSONArray otherPictures = obj.getJSONArray("otherPictures");
            for (int i = 0; i < otherPictures.length(); i++){
                imageByte = decoder.decodeBuffer(otherPictures.getString(i));
                bis = new ByteArrayInputStream(imageByte);
                newotherPictures.add(ImageIO.read(bis));
                bis.close();
            }
            return new UserReport(report.getReportOffsetDateTime(),report.getOdtOfWatchedViolation(),report.getPlace(),report.getViolationType(),report.getDescription(),report.getVehicle(),user,newmainPicture,newotherPictures);
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error decode report main image!");
            return null;
        }
    }

    /**
     * This method is used to return a json string that represents the User report, that has a structure as described in this method
     * @return Is the json string that represents the object
     */
    public String toJson(){
        JSONObject jsonObject = Report.toJSON(this);
        jsonObject.put("author", authorUser.getUsername());
        //jsonObject.put("mainPicture", encodeToString(mainPicture,IMAGE_TYPE));
        //the comment where necessary in order to send report to the client only without image, otherwise java goes out of memory
        jsonObject.put("mainPicture", "");
        List<String> picturesString = new ArrayList<>();
        /*for(BufferedImage picture: otherPictures){
            picturesString.add(encodeToString(picture,"png"));
        }*/
        jsonObject.put("otherPictures", picturesString);


        return jsonObject.toString();
    }

    /**
     * This method is used to encode an image as a string in the Base64 standard
     * @param image Is the image that has to be encoded as a string
     * @param type Is the format of the image, that in our server are saved as a png
     * @return Is the string that encodes the image
     */
    private String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }



}
