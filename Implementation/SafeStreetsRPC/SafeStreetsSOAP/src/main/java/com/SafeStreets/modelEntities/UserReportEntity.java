package com.SafeStreets.modelEntities;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.*;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_report", schema = "safe_streets_db")
public class UserReportEntity {
    private int id;
    private Timestamp reportTimeStamp;
    private Timestamp timeStampOfWatchedViolation;
    private String violationType;
    private String description;
    private String mainPicture;

    private PlaceEntity place;

    private VehicleEntity vehicleEntity;

    private UserEntity userEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "report_time_stamp")
    public Timestamp getReportTimeStamp() {
        return reportTimeStamp;
    }

    public void setReportTimeStamp(Timestamp reportTimeStamp) {
        this.reportTimeStamp = reportTimeStamp;
    }

    @Basic
    @Column(name = "time_stamp_of_watched_violation")
    public Timestamp getTimeStampOfWatchedViolation() {
        return timeStampOfWatchedViolation;
    }

    public void setTimeStampOfWatchedViolation(Timestamp timeStampOfWatchedViolation) {
        this.timeStampOfWatchedViolation = timeStampOfWatchedViolation;
    }

    @Basic
    @Column(name = "violation_type")
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
    @Column(name = "main_picture")
    public String getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "place_id")
    public PlaceEntity getPlace() {
        return place;
    }

    public void setPlace(PlaceEntity place) {
        this.place = place;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "vehicle_license_plate")
    public VehicleEntity getVehicleEntity() {
        return vehicleEntity;
    }

    public void setVehicleEntity(VehicleEntity vehicleEntity) {
        this.vehicleEntity = vehicleEntity;
    }

    @ManyToOne
    @JoinColumn(name = "user")
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

    public UserReport toUserReportWithImages(List<OtherPictureEntity> otherPictureEntities) throws ImageReadException {
        OffsetDateTime odtOfWatchedViolation=null;
        if(timeStampOfWatchedViolation!=null)
            odtOfWatchedViolation=DataManagerAdapter.toOffsetDateTimeFromTimestamp(timeStampOfWatchedViolation);

        Vehicle vehicle=null;
        if(vehicleEntity!=null)
            vehicle=vehicleEntity.toVehicle();

        BufferedImage mainPictureBI=DataManagerAdapter.readImage(mainPicture);

        List<BufferedImage> otherPicturesBI = new ArrayList<>();

        for(OtherPictureEntity otherPictureEntity : otherPictureEntities) {
            otherPicturesBI.add(DataManagerAdapter.readImage(otherPictureEntity.getPicture()));
        }

        return new UserReport(DataManagerAdapter.toOffsetDateTimeFromTimestamp(reportTimeStamp), odtOfWatchedViolation,
                place.toPlace(), ViolationType.valueOf(violationType), description,
                vehicle,
                userEntity.toUser(), mainPictureBI, otherPicturesBI);
    }
}
