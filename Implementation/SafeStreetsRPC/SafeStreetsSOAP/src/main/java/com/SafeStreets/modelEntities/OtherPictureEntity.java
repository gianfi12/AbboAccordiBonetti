package com.SafeStreets.modelEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OtherPicture", schema = "SafeStreetsDB")
public class OtherPictureEntity {
    private int id;
    private String picture;

    private UserReportEntity userReportEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "picture")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @ManyToOne
    @JoinColumn(name="UserReport_id")
    public UserReportEntity getUserReportEntity() {
        return userReportEntity;
    }

    public void setUserReportEntity(UserReportEntity userReportEntity) {
        this.userReportEntity = userReportEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OtherPictureEntity that = (OtherPictureEntity) o;
        return id == that.id &&
                Objects.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, picture);
    }
}
