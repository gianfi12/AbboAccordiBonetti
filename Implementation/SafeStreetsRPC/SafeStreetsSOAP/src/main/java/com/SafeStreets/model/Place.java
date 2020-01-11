package com.SafeStreets.model;

import com.SafeStreets.modelEntities.PlaceEntity;

/**
 * It represent a place in the real world with city, address, house code and coordinate.
 */
public class Place {
    /**
     * city
     */
    private String city;
    /**
     * address
     */
    private String address;
    /**
     * house code
     */
    private String houseCode;
    /**
     * coordinate
     */
    private Coordinate coordinate;

    /**
     * It constructs one Place with the given city, address, house code and coordinate
     * @param city city
     * @param address address
     * @param houseCode house code
     * @param coordinate coordinate
     */
    public Place(String city, String address, String houseCode, Coordinate coordinate) {
        this.city = city;
        this.address = address;
        this.houseCode = houseCode;
        this.coordinate = coordinate;
    }

    /**
     * It returns the city
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * It returns the address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * It sets the address with the given one
     * @param address new address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * It returns the house code
     * @return house code
     */
    public String getHouseCode() {
        return houseCode;
    }
    /**
     * It returns the coordinate
     * @return coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * It sets the coordinate with the given one
     * @param coordinate new coordinate
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * It returns the PlaceEntity converted from this object
     * @return PlaceEntity converted from this object
     */
    public PlaceEntity toPlaceEntity() {
        PlaceEntity placeEntity=new PlaceEntity();
        placeEntity.setCity(city);
        placeEntity.setAddress(address);
        placeEntity.setHouseCode(houseCode);
        if(coordinate!=null)
            placeEntity.setCoordinateEntity(coordinate.toCoordinateEntity());
        return placeEntity;
    }

    /**
     * It verifies whether this object and the given one have the same values in the attributes
     * @param placeToCompare Place to which this object is compared
     * @return whether this object and the given one have the same values in the attributes
     */
    public boolean isEqual(Place placeToCompare) {
        boolean result=true;
        if(address==null)
            result=placeToCompare.address==null;

        if(houseCode==null)
            result=result&&placeToCompare.houseCode==null;

        if(coordinate==null)
            result=result&&placeToCompare.coordinate==null;
        else
            result=coordinate.isEqual(placeToCompare.coordinate);

        return city.equals(placeToCompare.city)&&result;
    }
}
