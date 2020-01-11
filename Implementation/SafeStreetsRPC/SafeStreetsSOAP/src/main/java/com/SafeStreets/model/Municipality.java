package com.SafeStreets.model;

/**
 * It represent the Municipality with its username and place (which contains the city of the Municipality)
 */
public class Municipality {
    /**
     * username of the Municipality
     */
    private String name;
    /**
     * Place that contains the city of the Municipality
     */
    private Place place;

    /**
     * It constructs one Municipality with the given name and Place
     * @param name username of the Municipality
     * @param place Place that contains the city of the Municipality
     */
    public Municipality(String name, Place place) {
        this.name = name;
        this.place = place;
    }

    /**
     * It returns the username of the Municipality
     * @return username of the Municipality
     */
    public String getName() {
        return name;
    }

    /**
     * It returns the place that contains the city of the Municipality
     * @return place that contains the city of the Municipality
     */
    public Place getPlace() {
        return place;
    }
}
