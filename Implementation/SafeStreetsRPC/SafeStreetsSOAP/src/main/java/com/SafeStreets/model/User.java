package com.SafeStreets.model;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.modelEntities.UserEntity;

import java.awt.image.BufferedImage;
import java.sql.Date;
import java.time.LocalDate;

public class User {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Place placeOfBirth;
    private Place placeOfResidence;
    private BufferedImage picture;
    private BufferedImage imageIdCard;
    private String fiscalCode;
    private LocalDate dateOfBirth;

    public User(String username, String email, String firstName, String lastName, Place placeOfBirth, Place placeOfResidence, BufferedImage picture, BufferedImage imageIdCard, String fiscalCode, LocalDate dateOfBirth) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.placeOfBirth = placeOfBirth;
        this.placeOfResidence = placeOfResidence;
        this.picture=picture;
        this.imageIdCard = imageIdCard;
        this.fiscalCode = fiscalCode;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Place getPlaceOfBirth() {
        return placeOfBirth;
    }

    public Place getPlaceOfResidence() {
        return placeOfResidence;
    }

    public BufferedImage getPicture() {
        return picture;
    }

    public BufferedImage getImageIdCard() {
        return imageIdCard;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public UserEntity toUserEntity(String password, String picturePath, String imageIdCardPath) {
        UserEntity newUserEntity=new UserEntity();

        newUserEntity.setUsername(getUsername());
        newUserEntity.setEmail(getEmail());
        newUserEntity.setFirstname(getFirstName());
        newUserEntity.setLastname(getLastName());


        newUserEntity.setPlaceOfBirthEntity(getPlaceOfBirth().toPlaceEntity());
        newUserEntity.setPlaceOfResidenceEntity(getPlaceOfResidence().toPlaceEntity());

        newUserEntity.setPicture(picturePath);
        newUserEntity.setIdCard(imageIdCardPath);

        newUserEntity.setFiscalCode(getFiscalCode());

        newUserEntity.setDateOfBirth(DataManagerAdapter.toDateFromLocalDate(getDateOfBirth()));
        newUserEntity.setSalt(DataManagerAdapter.generateSalt());
        newUserEntity.setPassword(DataManagerAdapter.generatePasswordHash(password, newUserEntity.getSalt()));

        return newUserEntity;
    }

    public boolean isEqual(User userToCompare) {
        return username.equals(userToCompare.username)&&email.equals(userToCompare.email)
                &&firstName.equals(userToCompare.firstName)
                &&lastName.equals(userToCompare.lastName)
                &&placeOfBirth.isEqual(userToCompare.placeOfBirth)
                &&placeOfResidence.isEqual(userToCompare.placeOfResidence)
                &&fiscalCode.equals(userToCompare.fiscalCode)
                &&dateOfBirth.isEqual(userToCompare.dateOfBirth);
    }

    public void setPlaceOfBirth(Place placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setPlaceOfResidence(Place placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }
}
