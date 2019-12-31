package com.SafeStreets.model;

import java.util.ArrayList;
import java.util.List;

public enum ViolationType {
    PARKING_ON_BIKE_LANES(true, true),
    PARKING_ON_RESERVED_STALL(true, true),
    DOUBLE_PARKING(true, true),
    PARKING_ON_PEDESTRIAN_CROSSING(true, true),
    PARKING_ON_SIDEWALK(true, true),
    PARKING_ON_TRAFFIC_ISLAND(true, true),
    PARKING_NOT_PAYED(true, true),
    PARKING_ON_RED_ZONE(true, true),
    TRAFFIC_LIGHT_VIOLATION(false, true),
    INCIDENT_VEHICLES_VIOLATION(false, true),
    SPEED_VIOLATION(false, true),
    AGAINST_TRAFFIC_VIOLATION(false, true),
    OTHER_VIOLATION(false, true);

    private boolean canBeReportedFromUser;
    private boolean canBeReportedFromMunicipality;

    ViolationType(boolean canBeReportedFromUser, boolean canBeReportedFromMunicipality) {
        this.canBeReportedFromUser = canBeReportedFromUser;
        this.canBeReportedFromMunicipality = canBeReportedFromMunicipality;
    }

    public boolean canBeReportedFromUser() {
        return canBeReportedFromUser;
    }

    public boolean canBeReportedFromMunicipality() {
        return canBeReportedFromMunicipality;
    }

    public List<ViolationType> getViolationTypeReportableFromUser() {
        List<ViolationType> violationTypeList=new ArrayList<>();

        for(ViolationType violationType : ViolationType.values()) {
            if(violationType.canBeReportedFromUser)
                violationTypeList.add(violationType);
        }
        return violationTypeList;
    }

    public List<ViolationType> getViolationTypeReportableFromMunicipality() {
        List<ViolationType> violationTypeList=new ArrayList<>();

        for(ViolationType violationType : ViolationType.values()) {
            if(violationType.canBeReportedFromMunicipality)
                violationTypeList.add(violationType);
        }
        return violationTypeList;
    }
}
