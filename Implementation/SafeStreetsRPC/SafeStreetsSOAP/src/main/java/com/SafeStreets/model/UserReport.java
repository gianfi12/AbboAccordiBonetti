package com.SafeStreets.model;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.modelEntities.UserReportEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserReport extends Report{
    /**
     * This is used to print log message if an error occurs
     */
    private final static Logger LOGGER = Logger.getLogger(UserReport.class.getName());
    private User authorUser;
    private BufferedImage mainPicture;
    private List<BufferedImage> otherPictures;

    public UserReport(OffsetDateTime reportOffsetDateTime, OffsetDateTime odtOfWatchedViolation, Place place, ViolationType violationType, String description, Vehicle vehicle, User authorUser, BufferedImage mainPicture, List<BufferedImage> otherPictures) {
        super(reportOffsetDateTime, odtOfWatchedViolation, place, violationType, description, vehicle);
        this.authorUser = authorUser;
        this.mainPicture = mainPicture;
        this.otherPictures = otherPictures;
    }

    public User getAuthorUser() {
        return authorUser;
    }

    public BufferedImage getMainPicture() {
        return mainPicture;
    }

    public List<BufferedImage> getOtherPictures() {
        return otherPictures;
    }


    public UserReportEntity toUserReportEntity(String mainPicturePath) {
        UserReportEntity userReportEntity=new UserReportEntity();
        userReportEntity.setReportTimeStamp(DataManagerAdapter.toTimestampFromOffsetDateTime(getReportOffsetDateTime()));
        userReportEntity.setTimeStampOfWatchedViolation(DataManagerAdapter.toTimestampFromOffsetDateTime(getOdtOfWatchedViolation()));
        userReportEntity.setViolationType(getViolationType().toString());
        userReportEntity.setDescription(getDescription());
        userReportEntity.setMainPicture(mainPicturePath);
        userReportEntity.setPlace(getPlace().toPlaceEntity());
        userReportEntity.setVehicleEntity(getVehicle().toVehicleEntity());
        userReportEntity.setUserEntity(getAuthorUser().toUserEntity("","",""));

        return userReportEntity;
    }

    public Report toReport() {
            return new Report(getReportOffsetDateTime(), getOdtOfWatchedViolation(), getPlace(), getViolationType(),
                    getDescription(), getVehicle());
    }

    public static UserReport fromJSON(String jsonstring,User user){
        Report report = Report.fromJSON(jsonstring);
        UserReport userReport = (UserReport) report;
        userReport.authorUser=user;
        JSONObject obj = new JSONObject(jsonstring);
        String mainPicture = obj.getString("mainPicture");

        byte[] imageByte;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            imageByte = decoder.decodeBuffer(mainPicture);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            userReport.mainPicture = ImageIO.read(bis);
            bis.close();

            userReport.otherPictures = new ArrayList<>();
            JSONArray otherPictures = obj.getJSONArray("otherPictures");
            for (int i = 0; i < otherPictures.length(); i++){
                imageByte = decoder.decodeBuffer(otherPictures.getString(i));
                bis = new ByteArrayInputStream(imageByte);
                userReport.otherPictures.add(ImageIO.read(bis));
                bis.close();
            }
            return userReport;
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error decode report main image!");
            return null;
        }
    }


}
