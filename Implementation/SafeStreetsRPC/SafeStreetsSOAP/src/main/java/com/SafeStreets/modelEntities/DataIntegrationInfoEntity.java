package com.SafeStreets.modelEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DataIntegrationInfo", schema = "SafeStreetsDB")
public class DataIntegrationInfoEntity {
    private String ip;
    private int port;
    private String password;
    private String municipality;

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "port")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Id
    @Column(name = "Municipality")
    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataIntegrationInfoEntity that = (DataIntegrationInfoEntity) o;
        return port == that.port &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(password, that.password) &&
                Objects.equals(municipality, that.municipality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port, password, municipality);
    }
}
