package com.SafeStreets.model;

import java.util.ArrayList;
import java.util.List;

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

    public List<StatisticType> getStatisticTypeForUser() {
        List<StatisticType> statisticTypeList=new ArrayList<>();

        for(StatisticType statisticType : StatisticType.values()) {
            if(statisticType.canBeForUser)
                statisticTypeList.add(statisticType);
        }
        return statisticTypeList;
    }

    public List<StatisticType> getStatisticTypeForMunicipality() {
        List<StatisticType> statisticTypeList=new ArrayList<>();

        for(StatisticType statisticType : StatisticType.values()) {
            if(statisticType.canBeForMunicipality)
                statisticTypeList.add(statisticType);
        }
        return statisticTypeList;
    }
}
