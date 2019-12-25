package com.SafeStreets.model;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.modelEntities.UserReportEntity;

import java.awt.image.BufferedImage;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

public class UserReport extends Report{
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
}
