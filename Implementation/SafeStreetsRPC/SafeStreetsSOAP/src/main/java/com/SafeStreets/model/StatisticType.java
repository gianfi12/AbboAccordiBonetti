package com.SafeStreets.model;

/**
 * Type of statistic
 */
public enum StatisticType {
    /**
     * statistic about the streets, see requirement R10A
     */
    STREETS_STAT(true, true),
    /**
     * statistic about the effectiveness of the system, see requirement R10B
     */
    EFFECTIVENESS_STAT(true, true),
    /**
     * statistic about the vehicles, see requirement R10C
     */
    VEHICLES_STAT(false, true),
    /**
     * statistic about the violations, see requirement R10D
     */
    VIOLATIONS_STAT(true, true);
    /**
     * It indicates whether the user can ask this statistic type
     */
    private boolean canBeForUser;
    /**
     * It indicates whether the Municipality can ask this statistic type
     */
    private boolean canBeForMunicipality;

    /**
     * It creates one StatisticType with the given parameters
     * @param canBeForUser whether the user can ask this statistic type
     * @param canBeForMunicipality whether the Municipality can ask this statistic type
     */
    StatisticType(boolean canBeForUser, boolean canBeForMunicipality) {
        this.canBeForUser = canBeForUser;
        this.canBeForMunicipality = canBeForMunicipality;
    }

    /**
     * It indicates whether the user can ask this statistic type
     * @return whether the user can ask this statistic type
     */
    public boolean canBeForUser() {
        return canBeForUser;
    }

    /**
     * It indicates whether the Municipality can ask this statistic type
     * @return whether the Municipality can ask this statistic type
     */
    public boolean canBeForMunicipality() {
        return canBeForMunicipality;
    }
}
