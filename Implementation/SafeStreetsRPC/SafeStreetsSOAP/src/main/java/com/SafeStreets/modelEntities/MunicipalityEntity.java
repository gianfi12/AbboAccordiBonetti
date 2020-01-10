package com.SafeStreets.modelEntities;

import com.SafeStreets.model.Municipality;

import javax.persistence.*;
import java.util.Objects;

/**
 * It is class used to map the table "municipality" in the database.
 * It represents a Municipality
 */
@Entity
@Table(name = "municipality", schema = "safe_streets_db")
public class MunicipalityEntity {
    /**
     * contract code of the Municipality
     */
    private String contractCode;
    /**
     * username of the Municipality
     */
    private String name;
    /**
     * password of the Municipality
     */
    private String password;
    /**
     * salt of the password
     */
    private String passSalt;

    /**
     * place containing the city of the Municipality
     */
    private PlaceEntity placeEntity;

    /**
     * It returns the name of the Municipality, which corresponds to the attribute "name" in the database.
     * @return name of the Municipality
     */
    @Column(name = "name", unique=true)
    public String getName() {
        return name;
    }

    /**
     * It sets the name with the given one
     * @param name new username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * It returns the password of the Municipality, which corresponds to the attribute "password" in the database.
     * @return password of the Municipality
     */
    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    /**
     * It sets the password with the given one
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * It returns the salt of the password of the Municipality, which corresponds to the attribute "pass_salt" in the database.
     * @return salt of the password of the Municipality
     */
    @Basic
    @Column(name = "pass_salt")
    public String getPassSalt() {
        return passSalt;
    }

    /**
     * It sets the salt with the given one
     * @param passSalt new salt
     */
    public void setPassSalt(String passSalt) {
        this.passSalt = passSalt;
    }

    /**
     * It returns the contract code of the Municipality, which is the key of the element and it corresponds to the attribute "contract_code" in the database.
     * @return contract_code of the Municipality
     */
    @Id
    @Column(name = "contract_code")
    public String getContractCode() {
        return contractCode;
    }

    /**
     * It sets the contract code with the given one
     * @param contractCode new contract code
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * It returns the place of the Municipality, which corresponds to the attribute "place_id" in the database and it is obtain
     * with a join many to one with the PlaceEntity
     * @return place of the Municipality
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name="place_id")
    public PlaceEntity getPlaceEntity() {
        return placeEntity;
    }

    /**
     * It sets the place entity with the given one
     * @param placeEntity new place entity
     */
    public void setPlaceEntity(PlaceEntity placeEntity) {
        this.placeEntity = placeEntity;
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
        MunicipalityEntity that = (MunicipalityEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(password, that.password) &&
                Objects.equals(passSalt, that.passSalt) &&
                Objects.equals(contractCode, that.contractCode);
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
        return Objects.hash(name, password, passSalt, contractCode);
    }

    /**
     * It returns the Municipality converted from this object
     * @return Municipality converted from this object
     */
    public Municipality toMunicipality() {
        return new Municipality(name, placeEntity.toPlace());
    }
}
