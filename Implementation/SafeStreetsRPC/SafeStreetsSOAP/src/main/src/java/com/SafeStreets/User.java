package com.SafeStreets;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Place placeOfBirth;
    private Place placeOfResidence;
    private Image imageIdCard;
    private String fiscalCode;
    private Date dateOfBirth;
    private String password;

    public User(String username, String email, String firstName, String lastName, Place placeOfBirth, Place placeOfResidence, Image imageIdCard, String fiscalCode, Date dateOfBirth, String password) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.placeOfBirth = placeOfBirth;
        this.placeOfResidence = placeOfResidence;
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

    public Image getImageIdCard() {
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
