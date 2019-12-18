package com.SafeStreets.modelEntities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "UserReport", schema = "SafeStreetsDB")
public class UserReportEntity {
    private int id;
    private Timestamp reportTimeStamp;
    private Timestamp timeStampOfWatchedViolation;
    private String violationType;
    private String description;
    private String mainPicture;

    @ManyToOne
    private PlaceEntity place;
    @ManyToOne
    private VehicleEntity vehicleEntity;
    @ManyToOne
    private UserEntity userEntity;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "reportTimeStamp")
    public Timestamp getReportTimeStamp() {
        return reportTimeStamp;
    }

    public void setReportTimeStamp(Timestamp reportTimeStamp) {
        this.reportTimeStamp = reportTimeStamp;
    }

    @Basic
    @Column(name = "timeStampOfWatchedViolation")
    public Timestamp getTimeStampOfWatchedViolation() {
        return timeStampOfWatchedViolation;
    }

    public void setTimeStampOfWatchedViolation(Timestamp timeStampOfWatchedViolation) {
        this.timeStampOfWatchedViolation = timeStampOfWatchedViolation;
    }

    @Basic
    @Column(name = "violationType")
    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "mainPicture")
    public String getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }

    public PlaceEntity getPlace() {
        return place;
    }

    public void setPlace(PlaceEntity place) {
        this.place = place;
    }

    public VehicleEntity getVehicleEntity() {
        return vehicleEntity;
    }

    public void setVehicleEntity(VehicleEntity vehicleEntity) {
        this.vehicleEntity = vehicleEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReportEntity that = (UserReportEntity) o;
        return id == that.id &&
                Objects.equals(reportTimeStamp, that.reportTimeStamp) &&
                Objects.equals(timeStampOfWatchedViolation, that.timeStampOfWatchedViolation) &&
                Objects.equals(violationType, that.violationType) &&
                Objects.equals(description, that.description) &&
                Objects.equals(mainPicture, that.mainPicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportTimeStamp, timeStampOfWatchedViolation, violationType, description, mainPicture);
    }
}
