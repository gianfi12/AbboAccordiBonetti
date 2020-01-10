package com.SafeStreets.modelEntities;

import com.SafeStreets.model.Coordinate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * It is class used to map the table "coordinate" in the database.
 * It represents a coordinate with latitude, longitude and altitude.
 */
@Entity
@Table(name = "coordinate", schema = "safe_streets_db")
public class CoordinateEntity {
    /**
     * Identifier of the element
     */
    private int id;
    /**
     * Latitude of the coordinate
     */
    private BigDecimal latitude;
    /**
     * Longitude of the coordinate
     */
    private BigDecimal longitude;
    /**
     * Altitude of the coordinate
     */
    private BigDecimal altitude;

    /**
     * It returns the id of the coordinate, which is the key of the element and it corresponds to the attribute "id" in the database.
     * The key is generated automatically in ascending order.
     * @return int representing the id of the coordinate
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
     * It returns the latitude, which corresponds to the attribute "latitude" in the database.
     * @return latitude as BigDecimal
     */
    @Basic
    @Column(name = "latitude")
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * It sets the latitude with the given one
     * @param latitude new latitude
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * It returns the longitude, which corresponds to the attribute "longitude" in the database.
     * @return longitude as BigDecimal
     */
    @Basic
    @Column(name = "longitude")
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * It sets the longitude with the given one
     * @param longitude new longitude
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * It returns the altitude, which corresponds to the attribute "altitude" in the database.
     * @return altitude as BigDecimal
     */
    @Basic
    @Column(name = "altitude")
    public BigDecimal getAltitude() {
        return altitude;
    }

    /**
     * It sets the altitude with the given one
     * @param altitude new altitude
     */
    public void setAltitude(BigDecimal altitude) {
        this.altitude = altitude;
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
        CoordinateEntity that = (CoordinateEntity) o;
        return id == that.id &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(altitude, that.altitude);
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
        return Objects.hash(id, latitude, longitude, altitude);
    }

    /**
     * It returns the Coordinate converted from this object
     * @return Coordinate converted from this object
     */
    public Coordinate toCoordinate() {
        if(altitude==null)
            return new Coordinate(latitude.doubleValue(), longitude.doubleValue(), null);

        return new Coordinate(latitude.doubleValue(), longitude.doubleValue(), altitude.doubleValue());
    }

    /**
     * Returns a string representation of the object, made by: id, latitude, longitude and altitude
     *
     * @return  a string representation of the object, made by: id, latitude, longitude and altitude
     */
    @Override
    public String toString() {
        return "id: "+id+", lat: "+latitude+", long: "+longitude+", alt: "+altitude;
    }
}
