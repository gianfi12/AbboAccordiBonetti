package com.SafeStreets.model;

import com.SafeStreets.modelEntities.CoordinateEntity;

import java.math.BigDecimal;

/**
 * It represent a coordinate in the real world with latitude, longitued and altitude.
 */
public class Coordinate {
    /**
     * latitude
     */
    private Double latitude;
    /**
     * longitude
     */
    private Double longitude;
    /**
     * altitude
     */
    private Double altitude;

    /**
     * It construct one Coordinate with the given values
     * @param latitude new latitude
     * @param longitude new longitude
     * @param altitude new altitude
     */
    public Coordinate(Double latitude, Double longitude, Double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * It returns the latitude
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * It sets the latitude with the given one
     * @param latitude new latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * It returns the longitude
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * It sets the longitude with the given one
     * @param longitude new latitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * It returns the altitude
     * @return altitude
     */
    public Double getAltitude() {
        return altitude;
    }

    /**
     * It sets the altitude with the given one
     * @param altitude new latitude
     */
    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    /**
     * It returns the CoordinateEntity converted from this object
     * @return CoordinateEntity converted from this object
     */
    public CoordinateEntity toCoordinateEntity() {
        CoordinateEntity coordinateEntity=new CoordinateEntity();
        coordinateEntity.setLatitude(new BigDecimal(latitude.toString()));
        coordinateEntity.setLongitude(new BigDecimal(longitude.toString()));
        if(altitude!=null)
            coordinateEntity.setAltitude(new BigDecimal(altitude.toString()));

        return coordinateEntity;
    }

    /**
     * It verifies whether this object and the given one have the same values in the attributes
     * @param coordinateToCompare Coordinate to which this object is compared
     * @return whether this object and the given one have the same values in the attributes
     */
    public boolean isEqual(Coordinate coordinateToCompare) {
        if(altitude==null && coordinateToCompare.altitude!=null ||
                altitude!=null && coordinateToCompare.altitude==null)
            return false;

        if(altitude==null)
            return latitude.equals(coordinateToCompare.latitude)&&longitude.equals(coordinateToCompare.longitude);

        return latitude.equals(coordinateToCompare.latitude)&&longitude.equals(coordinateToCompare.longitude) &&
                altitude.equals(coordinateToCompare.altitude);

    }
    /**
     * Returns a string representation of the object, made by: latitude, longitude and altitude
     *
     * @return  a string representation of the object, made by: latitude, longitude and altitude
     */
    @Override
    public String toString() {
        return "lat: "+latitude+", long: "+longitude+", alt: "+altitude;
    }
}
