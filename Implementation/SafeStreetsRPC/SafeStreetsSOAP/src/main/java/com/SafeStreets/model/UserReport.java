package com.SafeStreets.model;

import com.SafeStreets.modelEntities.UserReportEntity;

import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class UserReport extends Report{
    private User authorUser;
    private BufferedImage mainPicture;
    private List<BufferedImage> otherPictures;

    public UserReport(Instant reportInstant, Instant instantOfWatchedViolation, Place place, ViolationType violationType, String description, Vehicle vehicle, User authorUser, BufferedImage mainPicture, List<BufferedImage> otherPictures) {
        super(reportInstant, instantOfWatchedViolation, place, violationType, description, vehicle);
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
        userReportEntity.setReportTimeStamp(Timestamp.from(getReportInstant()));
        userReportEntity.setTimeStampOfWatchedViolation(Timestamp.from(getInstantOfWatchedViolation()));
        userReportEntity.setViolationType(getViolationType().toString());
        userReportEntity.setDescription(getDescription());
        userReportEntity.setMainPicture(mainPicturePath);
        userReportEntity.setPlace(getPlace().toPlaceEntity());
        userReportEntity.setVehicleEntity(getVehicle().toVehicleEntity());
        userReportEntity.setUserEntity(getAuthorUser().toUserEntity("","",""));

        return userReportEntity;
    }

    public Report toReport() {
            return new Report(getReportInstant(), getInstantOfWatchedViolation(), getPlace(), getViolationType(),
                    getDescription(), getVehicle());
    }
}
