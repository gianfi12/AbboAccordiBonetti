package com.SafeStreets;

import com.SafeStreets.dataManagerAdapterPack.DataManagerAdapter;
import com.SafeStreets.dataManagerAdapterPack.ReportsDataInterface;
import com.SafeStreets.exceptions.ElaborationException;
import com.SafeStreets.exceptions.ImageStoreException;
import com.SafeStreets.mapsserviceadapter.FieldsException;
import com.SafeStreets.mapsserviceadapter.GeocodeException;
import com.SafeStreets.mapsserviceadapter.MapsServiceInterface;
import com.SafeStreets.model.Place;
import com.SafeStreets.model.UserReport;

import javax.ejb.Stateless;

/**
 * This is the class that implements the method of the elaboration manager
 */
@Stateless
public class ElaborationManager implements ElaborationManagerInterface {

    /**
     * This method is called to elaborate a report sent by a User
     * @param userReport Is the user's report
     * @throws ElaborationException Is thrown if some errors occurs during the elaboration
     */
    @Override
    public void elaborate(UserReport userReport) throws ElaborationException {
        MapsServiceInterface mapsService = MapsServiceInterface.getInstance();
        Place userPlace=userReport.getPlace();
        Place place;
        try {
            place=mapsService.geocoding(userPlace.getCoordinate());
        }catch (FieldsException | GeocodeException e){
            try {
                place=mapsService.geocoding(userPlace.getAddress());
            }catch (GeocodeException x){
                throw  new ElaborationException();
            }
        }
        ReportsDataInterface reportsData= new DataManagerAdapter();
        userReport.setPlace(place);
        try {
            reportsData.addUserReport(userReport);
        }catch (ImageStoreException e){
            throw new ElaborationException();
        }
    }
}
