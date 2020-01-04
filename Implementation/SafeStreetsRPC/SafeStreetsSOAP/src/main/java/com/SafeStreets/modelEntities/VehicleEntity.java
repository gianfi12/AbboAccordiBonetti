package com.SafeStreets.modelEntities;

import com.SafeStreets.model.Vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "vehicle", schema = "safe_streets_db")
public class VehicleEntity {
    private String licensePlate;

    @Id
    @Column(name = "license_plate")
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleEntity that = (VehicleEntity) o;
        return Objects.equals(licensePlate, that.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate);
    }

    public Vehicle toVehicle() {
        return new Vehicle(licensePlate);
    }
}
