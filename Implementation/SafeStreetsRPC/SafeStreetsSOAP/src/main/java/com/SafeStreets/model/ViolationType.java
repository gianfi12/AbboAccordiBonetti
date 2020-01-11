package com.SafeStreets.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Type of violation
 */
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

    /**
     * It indicates whether the user can report this violation
     */
    private boolean canBeReportedFromUser;
    /**
     * It indicates whether the Municipality can report this violation
     */
    private boolean canBeReportedFromMunicipality;

    /**
     * It creates one ViolationType with the given parameters
     * @param canBeReportedFromUser whether the user can report this violation
     * @param canBeReportedFromMunicipality whether the Municipality can report this violation
     */
    ViolationType(boolean canBeReportedFromUser, boolean canBeReportedFromMunicipality) {
        this.canBeReportedFromUser = canBeReportedFromUser;
        this.canBeReportedFromMunicipality = canBeReportedFromMunicipality;
    }

    /**
     * It indicates whether the user can report this violation
     * @return whether the user can report this violation
     */
    public boolean canBeReportedFromUser() {
        return canBeReportedFromUser;
    }

    /**
     * It indicates whether the Municipality can report this violation
     * @return whether the Municipality can report this violation
     */
    public boolean canBeReportedFromMunicipality() {
        return canBeReportedFromMunicipality;
    }

    /**
     * It returns the types of violations reportable by the user
     * @return types of violations reportable by the user
     */
    public static List<ViolationType> getViolationTypeReportableFromUser() {
        List<ViolationType> violationTypeList=new ArrayList<>();

        for(ViolationType violationType : ViolationType.values()) {
            if(violationType.canBeReportedFromUser)
                violationTypeList.add(violationType);
        }
        return violationTypeList;
    }

}
