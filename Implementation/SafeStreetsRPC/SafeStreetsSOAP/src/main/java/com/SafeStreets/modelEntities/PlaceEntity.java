package com.SafeStreets.modelEntities;

import com.SafeStreets.model.Coordinate;
import com.SafeStreets.model.Place;

import javax.persistence.*;
import java.util.Objects;

/**
 * It is class used to map the table "place" in the database.
 * It represents a place with an identifier, a city, an address, a houseCode and a coordinate
 */
@Entity
@Table(name = "place", schema = "safe_streets_db")
public class PlaceEntity {
    /**
     * Identifier of this element
     */
    private int id;
    /**
     * City
     */
    private String city;
    /**
     * Address
     */
    private String address;
    /**
     * House code
     */
    private String houseCode;
    /**
     * It represents the coordinate of this place
     */
    private CoordinateEntity coordinateEntity;

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
     * It returns the city of this element, which corresponds to the attribute "city" in the database.
     * @return city of this element
     */
    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    /**
     * It sets the city with the given one
     * @param city new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * It returns the address of this element, which corresponds to the attribute "address" in the database.
     * @return address of this element
     */
    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    /**
     * It sets the address with the given one
     * @param address new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * It returns the houseCode of this element, which corresponds to the attribute "house_code" in the database.
     * @return houseCode of this houseCode
     */
    @Basic
    @Column(name = "house_code")
    public String getHouseCode() {
        return houseCode;
    }

    /**
     * It sets the id house code the given one
     * @param houseCode new house code
     */
    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    /**
     * It returns the CoordinateEntity of this element, which corresponds to the attribute "coordinate_id" in the database.
     * It is one-to-one joined with CoordinateEntity
     * @return CoordinateEntity of this element
     */
    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "coordinate_id")
    public CoordinateEntity getCoordinateEntity() {
        return coordinateEntity;
    }

    /**
     * It sets the coordinateEntity with the given one
     * @param coordinateEntity new coordinateEntity
     */
    public void setCoordinateEntity(CoordinateEntity coordinateEntity) {
        this.coordinateEntity = coordinateEntity;
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
        PlaceEntity that = (PlaceEntity) o;
        return id == that.id &&
                Objects.equals(city, that.city) &&
                Objects.equals(address, that.address) &&
                Objects.equals(houseCode, that.houseCode);
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
        return Objects.hash(id, city, address, houseCode);
    }

    /**
     * It returns the Place converted from this object
     * @return Place converted from this object
     */
    public Place toPlace() {
        Coordinate coordinate=null;
        if(coordinateEntity!=null)
            coordinate=coordinateEntity.toCoordinate();
        return new Place(city, address, houseCode, coordinate);
    }
}
