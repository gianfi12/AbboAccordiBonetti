package com.SafeStreets;

import java.awt.image.BufferedImage;
import java.util.Date;

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
    private Date dateOfBirth;
    private String password;

    public User(String username, String email, String firstName, String lastName, Place placeOfBirth, Place placeOfResidence, BufferedImage picture, BufferedImage imageIdCard, String fiscalCode, Date dateOfBirth, String password) {
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
        this.password = password;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
    }


}
