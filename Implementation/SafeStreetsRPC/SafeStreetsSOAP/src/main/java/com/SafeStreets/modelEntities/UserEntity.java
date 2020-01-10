package com.SafeStreets.modelEntities;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.exceptions.ImageReadException;
import com.SafeStreets.model.Place;
import com.SafeStreets.model.User;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.sql.Date;
import java.util.Objects;

/**
 * It is class used to map the table "user" in the database.
 * It represents a user.
 */
@Entity
@Table(name = "user", schema = "safe_streets_db")
public class UserEntity {
    /**
     * Username of the user
     */
    private String username;
    /**
     * email
     */
    private String email;
    /**
     * firstname
     */
    private String firstname;
    /**
     * lastname
     */
    private String lastname;
    /**
     * place of birth
     */
    private PlaceEntity placeOfBirthEntity;
    /**
     * place of residence
     */
    private PlaceEntity placeOfResidenceEntity;
    /**
     * path to the picture of the user
     */
    private String picture;
    /**
     * path to the identity card picture of the user
     */
    private String idCard;
    /**
     * fiscal code
     */
    private String fiscalCode;
    /**
     * date of birth
     */
    private Date dateOfBirth;
    /**
     * hash of the password+salt
     */
    private String password;
    /**
     * salt of the password
     */
    private String salt;
    /**
     * date of registration
     */
    private Date dateOfRegistration;


    /**
     * It returns the username of the user, which is the key of the element and it corresponds to the attribute "username" in the database.
     * @return username of the user
     */
    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * It sets the username with the given one
     * @param username new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * It returns the email of the user, which  corresponds to the attribute "email" in the database.
     * @return email of the user
     */
    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    /**
     * It sets the email with the given one
     * @param email new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * It returns the first name of the user, which  corresponds to the attribute "firstname" in the database.
     * @return first name of the user
     */
    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    /**
     * It sets the first name with the given one
     * @param firstname new first name
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * It returns the last name of the user, which  corresponds to the attribute "lastname" in the database.
     * @return last name of the user
     */
    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    /**
     * It sets the last name with the given one
     * @param lastname new last name
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * It returns the picture path of the user, which  corresponds to the attribute "picture" in the database.
     * @return picture path of the user
     */
    @Basic
    @Column(name = "picture")
    public String getPicture() {
        return picture;
    }

    /**
     * It sets the picture path with the given one
     * @param picture new picture path
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * It returns the id card path of the user, which  corresponds to the attribute "id_card" in the database.
     * @return id_card path of the user
     */
    @Basic
    @Column(name = "id_card")
    public String getIdCard() {
        return idCard;
    }

    /**
     * It sets the id card path with the given one
     * @param idCard new id card path
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * It returns the fiscal code of the user, which  corresponds to the attribute "fiscal_code" in the database.
     * @return fiscal code of the user
     */
    @Basic
    @Column(name = "fiscal_code")
    public String getFiscalCode() {
        return fiscalCode;
    }

    /**
     * It sets the fiscal code with the given one
     * @param fiscalCode new fiscal code
     */
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    /**
     * It returns the date of birth of the user, which  corresponds to the attribute "date_of_birth" in the database.
     * @return date of birth of the user
     */
    @Basic
    @Column(name = "date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * It sets the date of birth with the given one
     * @param dateOfBirth new date of birth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * It returns the hash of the password of the user, which  corresponds to the attribute "password" in the database.
     * @return hash of the password of the user
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
     * It returns the salt of the user, which  corresponds to the attribute "salt" in the database.
     * @return salt of the user
     */
    @Basic
    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    /**
     * It sets the salt with the given one
     * @param salt new salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * It returns the date of registration of the user, which  corresponds to the attribute "date_of_registration" in the database.
     * @return date of registration of the user
     */
    @Basic
    @Column(name = "date_of_registration")
    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    /**
     * It sets the date of registration with the given one
     * @param dateOfRegistration new date of registration
     */
    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    /**
     * It returns the place of birth of the user, which corresponds to the attribute "place_of_birth_id" in the database.
     * It is many-to-one joined with PlaceEntity
     * @return place of birth of the user
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "place_of_birth_id")
    public PlaceEntity getPlaceOfBirthEntity() {
        return placeOfBirthEntity;
    }

    /**
     * It sets the place of birth with the given one
     * @param placeOfBirthEntity new place of birth
     */
    public void setPlaceOfBirthEntity(PlaceEntity placeOfBirthEntity) {
        this.placeOfBirthEntity = placeOfBirthEntity;
    }

    /**
     * It returns the place of residence of the user, which corresponds to the attribute "place_of_residence_id" in the database.
     * It is many-to-one joined with PlaceEntity
     * @return place of residence of the user
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "place_of_residence_id")
    public PlaceEntity getPlaceOfResidenceEntity() {
        return placeOfResidenceEntity;
    }

    /**
     * It sets the place of residence with the given one
     * @param placeOfResidenceEntity new place of residence
     */
    public void setPlaceOfResidenceEntity(PlaceEntity placeOfResidenceEntity) {
        this.placeOfResidenceEntity = placeOfResidenceEntity;
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
        UserEntity that = (UserEntity) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(picture, that.picture) &&
                Objects.equals(idCard, that.idCard) &&
                Objects.equals(fiscalCode, that.fiscalCode) &&
                Objects.equals(dateOfBirth, that.dateOfBirth) &&
                Objects.equals(password, that.password) &&
                Objects.equals(salt, that.salt) &&
                Objects.equals(dateOfRegistration, that.dateOfRegistration);
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
        return Objects.hash(username, email, firstname, lastname, picture, idCard, fiscalCode, dateOfBirth, password, salt, dateOfRegistration);
    }

    /**
     * It returns the User converted from this object
     * @return User converted from this object
     * @throws ImageReadException if there was a problem during the read of the pictures of the user
     */
    public User toUser() throws ImageReadException {
        BufferedImage pictureImage=null;
        if(picture!=null && !picture.equals(""))
            pictureImage = DataManagerAdapter.readImage(picture);

        BufferedImage idCardImage = null;

        if(idCard!=null && !idCard.equals(""))
            idCardImage=DataManagerAdapter.readImage(idCard);

        Place placeOfBirth = placeOfBirthEntity.toPlace();

        Place placeOfResidence = placeOfResidenceEntity.toPlace();


        return new User(username, email, firstname, lastname, placeOfBirth, placeOfResidence, pictureImage, idCardImage, fiscalCode, DataManagerAdapter.toLocalDateFromDate(dateOfBirth));
    }
}
