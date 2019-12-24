package com.SafeStreets.modelEntities;

import com.SafeStreets.model.Municipality;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Municipality", schema = "SafeStreetsDB")
public class MunicipalityEntity {
    private String contractCode;
    private String name;
    private String password;
    private String passSalt;

    private PlaceEntity placeEntity;


    @Column(name = "name", unique=true)
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

    @Id
    @Column(name = "contractCode")
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    @ManyToOne
    public PlaceEntity getPlaceEntity() {
        return placeEntity;
    }

    public void setPlaceEntity(PlaceEntity placeEntity) {
        this.placeEntity = placeEntity;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(name, password, passSalt, contractCode);
    }

    public Municipality toMunicipality() {
        return new Municipality(name, placeEntity.toPlace());
    }
}
