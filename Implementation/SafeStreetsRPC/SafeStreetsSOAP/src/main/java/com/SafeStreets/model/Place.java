package com.SafeStreets.model;

import com.SafeStreets.modelEntities.PlaceEntity;

public class Place {
    private String city;
    private String address;
    private String houseCode;
    private Coordinate coordinate;

    public Place() {

    }
    public Place(String city, String address, String houseCode, Coordinate coordinate) {
        this.city = city;
        this.address = address;
        this.houseCode = houseCode;
        this.coordinate = coordinate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public PlaceEntity toPlaceEntity() {
        PlaceEntity placeEntity=new PlaceEntity();
        placeEntity.setCity(city);
        placeEntity.setAddress(address);
        placeEntity.setHouseCode(houseCode);
        if(coordinate!=null)
            placeEntity.setCoordinateEntity(coordinate.toCoordinateEntity());
        return placeEntity;
    }

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
