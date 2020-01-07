package com.SafeStreets.model;


public enum StatisticType {
    STREETS_STAT(true, true),
    EFFECTIVENESS_STAT(true, true),
    VEHICLES_STAT(false, true),
    VIOLATIONS_STAT(true, true);

    private boolean canBeForUser;
    private boolean canBeForMunicipality;

    StatisticType(boolean canBeForUser, boolean canBeForMunicipality) {
        this.canBeForUser = canBeForUser;
        this.canBeForMunicipality = canBeForMunicipality;
    }

    public boolean canBeForUser() {
        return canBeForUser;
    }

    public boolean canBeForMunicipality() {
        return canBeForMunicipality;
    }
}
