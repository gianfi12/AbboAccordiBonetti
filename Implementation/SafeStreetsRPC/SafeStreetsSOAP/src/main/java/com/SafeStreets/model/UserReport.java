package com.SafeStreets.model;


import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class UserReport extends Report{
    private User authorUser;
    private BufferedImage mainPicture;
    private List<BufferedImage> otherPictures;

    public UserReport(Date reportDate, LocalTime reportTime, Date dateOfWatchedViolation, LocalTime timeOfWatchedViolation, Place place, ViolationType violationType, String description, Vehicle vehicle, User authorUser, BufferedImage mainPicture, List<BufferedImage> otherPictures) {
        super(reportDate, reportTime, dateOfWatchedViolation, timeOfWatchedViolation, place, violationType, description, vehicle);
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
}
