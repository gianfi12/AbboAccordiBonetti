package com.SafeStreets.modelEntities;

import javax.persistence.*;
import java.util.Objects;

/**
 * It is class used to map the table "other_picture" in the database.
 * It represents an optional picture of a report done by a user.
 */
@Entity
@Table(name = "other_picture", schema = "safe_streets_db")
public class OtherPictureEntity {
    /**
     * Identifier of this element
     */
    private int id;
    /**
     * Path to the optional picture of the report userReportEntity
     */
    private String picture;

    /**
     * User report to which the picture refers
     */
    private UserReportEntity userReportEntity;

    /**
     * It returns the id of this element, it is a key of this element and it corresponds to the attribute "id" in the database.
     * The key is generated automatically in ascending order.
     * @return id of this element
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    /**
     * It sets the id with the given one
     * @param id new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * It returns the path of the picture, which corresponds to the attribute "picture" in the database.
     * @return path of the picture
     */
    @Basic
    @Column(name = "picture")
    public String getPicture() {
        return picture;
    }

    /**
     * It sets the picture's path with the given one
     * @param picture new id
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * It returns the userReportEntity, which corresponds to the attribute "user_report_id" in the database.
     * @return userReportEntity
     */
    @ManyToOne
    @JoinColumn(name="user_report_id")
    public UserReportEntity getUserReportEntity() {
        return userReportEntity;
    }

    /**
     * It sets the userReportEntity with the given one
     * @param userReportEntity new userReportEntity
     */
    public void setUserReportEntity(UserReportEntity userReportEntity) {
        this.userReportEntity = userReportEntity;
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
        OtherPictureEntity that = (OtherPictureEntity) o;
        return id == that.id &&
                Objects.equals(picture, that.picture);
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
        return Objects.hash(id, picture);
    }
}
