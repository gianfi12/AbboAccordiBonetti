package com.SafeStreets;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class User {
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



}
