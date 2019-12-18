package com.SafeStreets.modelEntities;

import com.SafeStreets.model.Place;
import com.SafeStreets.model.User;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "User", schema = "SafeStreetsDB")
public class UserEntity {
    private String username;
    private String email;
    private String firstname;
    private String lastname;

    @ManyToOne
    private PlaceEntity placeOfBirthEntity;

    @ManyToOne
    private PlaceEntity placeOfResidenceEntity;

    private String picture;
    private String idCard;
    private String fiscalCode;
    private Date dateOfBirth;
    private String password;
    private String salt;



    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "picture")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Basic
    @Column(name = "idCard")
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Basic
    @Column(name = "fiscalCode")
    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    @Basic
    @Column(name = "dateOfBirth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public PlaceEntity getPlaceOfBirthEntity() {
        return placeOfBirthEntity;
    }

    public void setPlaceOfBirthEntity(PlaceEntity placeOfBirthEntity) {
        this.placeOfBirthEntity = placeOfBirthEntity;
    }

    public PlaceEntity getPlaceOfResidenceEntity() {
        return placeOfResidenceEntity;
    }

    public void setPlaceOfResidenceEntity(PlaceEntity placeOfResidenceEntity) {
        this.placeOfResidenceEntity = placeOfResidenceEntity;
    }

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
                Objects.equals(salt, that.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, firstname, lastname, picture, idCard, fiscalCode, dateOfBirth, password, salt);
    }


    public User toUser() {
        BufferedImage pictureImage = null;
        try
        {
            pictureImage = ImageIO.read(new File(picture));
        }
        catch (Exception ignored)
        {

        }

        BufferedImage idCardImage = null;
        try
        {
            idCardImage = ImageIO.read(new File(idCard));
        }
        catch (Exception ignored)
        {

        }

        Place placeOfBirth = placeOfBirthEntity.toPlace();

        Place placeOfResidence = placeOfResidenceEntity.toPlace();


        return new User(username, email, firstname, lastname, placeOfBirth, placeOfResidence, pictureImage, idCardImage, fiscalCode, dateOfBirth, password);
    }
}