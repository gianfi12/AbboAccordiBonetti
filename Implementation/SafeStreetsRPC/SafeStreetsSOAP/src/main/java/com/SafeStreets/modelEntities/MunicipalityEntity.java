package com.SafeStreets.modelEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Municipality", schema = "SafeStreetsDB")
public class MunicipalityEntity {
    private String name;
    private String password;
    private String passSalt;
    private String contractCode;
    private String contractSalt;

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "passSalt")
    public String getPassSalt() {
        return passSalt;
    }

    public void setPassSalt(String passSalt) {
        this.passSalt = passSalt;
    }

    @Basic
    @Column(name = "contractCode")
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    @Basic
    @Column(name = "contractSalt")
    public String getContractSalt() {
        return contractSalt;
    }

    public void setContractSalt(String contractSalt) {
        this.contractSalt = contractSalt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MunicipalityEntity that = (MunicipalityEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(password, that.password) &&
                Objects.equals(passSalt, that.passSalt) &&
                Objects.equals(contractCode, that.contractCode) &&
                Objects.equals(contractSalt, that.contractSalt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, passSalt, contractCode, contractSalt);
    }
}
