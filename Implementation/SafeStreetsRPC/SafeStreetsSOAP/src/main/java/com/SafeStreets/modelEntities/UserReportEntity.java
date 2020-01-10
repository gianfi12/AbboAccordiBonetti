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

/**
 * It is class used to map the table "user_report" in the database.
 * It represents a report done by a user.
 */
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

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param   o   the reference object with which to compare.
     * @return  {@code true} if this object is the same as the obj
     *          argument; {@code false} otherwise.
     * @see     #hashCode()
     */
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

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     *     method, then calling the {@code hashCode} method on each of
     *     the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link java.lang.Object#equals(java.lang.Object)}
     *     method, then calling the {@code hashCode} method on each of the
     *     two objects must produce distinct integer results.  However, the
     *     programmer should be aware that producing distinct integer results
     *     for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return  a hash code value for this object.
     * @see     java.lang.Object#equals(java.lang.Object)
     * @see     java.lang.System#identityHashCode
     */
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
