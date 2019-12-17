package com.SafeStreets.modelEntities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Coordinate", schema = "SafeStreetsDB")
public class CoordinateEntity {
    private int id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal altitude;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "latitude")
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "altitude")
    public BigDecimal getAltitude() {
        return altitude;
    }

    public void setAltitude(BigDecimal altitude) {
        this.altitude = altitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinateEntity that = (CoordinateEntity) o;
        return id == that.id &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(altitude, that.altitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, altitude);
    }
}
