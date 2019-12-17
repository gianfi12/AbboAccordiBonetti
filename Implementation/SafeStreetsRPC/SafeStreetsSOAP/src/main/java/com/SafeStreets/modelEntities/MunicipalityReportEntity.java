package com.SafeStreets.modelEntities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "MunicipalityReport", schema = "SafeStreetsDB")
public class MunicipalityReportEntity {
    private int id;
    private Timestamp reportTimeStamp;
    private Timestamp timeStampOfWatchedViolation;
    private String violationType;
    private String description;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "reportTimeStamp")
    public Timestamp getReportTimeStamp() {
        return reportTimeStamp;
    }

    public void setReportTimeStamp(Timestamp reportTimeStamp) {
        this.reportTimeStamp = reportTimeStamp;
    }

    @Basic
    @Column(name = "timeStampOfWatchedViolation")
    public Timestamp getTimeStampOfWatchedViolation() {
        return timeStampOfWatchedViolation;
    }

    public void setTimeStampOfWatchedViolation(Timestamp timeStampOfWatchedViolation) {
        this.timeStampOfWatchedViolation = timeStampOfWatchedViolation;
    }

    @Basic
    @Column(name = "violationType")
    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MunicipalityReportEntity that = (MunicipalityReportEntity) o;
        return id == that.id &&
                Objects.equals(reportTimeStamp, that.reportTimeStamp) &&
                Objects.equals(timeStampOfWatchedViolation, that.timeStampOfWatchedViolation) &&
                Objects.equals(violationType, that.violationType) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportTimeStamp, timeStampOfWatchedViolation, violationType, description);
    }
}
