package com.SafeStreets.modelEntities;

import com.SafeStreets.model.Coordinate;
import com.SafeStreets.model.Place;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Place", schema = "SafeStreetsDB")
public class PlaceEntity {
    private int id;
    private String city;
    private String address;
    private String houseCode;

    @OneToOne
    @JoinColumn(name = "id")
    private CoordinateEntity coordinateEntity;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "houseCode")
    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public CoordinateEntity getCoordinateEntity() {
        return coordinateEntity;
    }

    public void setCoordinateEntity(CoordinateEntity coordinateEntity) {
        this.coordinateEntity = coordinateEntity;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, city, address, houseCode);
    }

    public Place toPlace() {
        return new Place(city, address, houseCode, coordinateEntity.toCoordinate());
    }
}
