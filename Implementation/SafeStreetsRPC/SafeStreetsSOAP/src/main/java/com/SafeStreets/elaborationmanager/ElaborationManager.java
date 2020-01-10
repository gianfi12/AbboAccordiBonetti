package com.SafeStreets.elaborationmanager;

import com.SafeStreets.dataManagerAdapterPack.ReportsDataInterface;
import com.SafeStreets.exceptions.ElaborationException;
import com.SafeStreets.exceptions.ImageStoreException;
import com.SafeStreets.exceptions.PlateNotRecognizedException;
import com.SafeStreets.licensePlateRecognized.LicensePlateRecognizerInterface;
import com.SafeStreets.model.UserReport;
import com.SafeStreets.model.Vehicle;

import javax.ejb.Stateless;

/**
 * This is the class that implements the method of the elaboration manager
 */
@Stateless
class ElaborationManager implements ElaborationManagerInterface {

    /**
     * This method is called to elaborate a report sent by a User
     * @param userReport Is the user's report
     * @throws ElaborationException Is thrown if some errors occurs during the elaboration
     */
    @Override
    public void elaborate(UserReport userReport) throws ElaborationException {
        ReportsDataInterface reportsData = ReportsDataInterface.getReportsDataInstance();
        try {
            LicensePlateRecognizerInterface licensePlateRecognizer = LicensePlateRecognizerInterface.getInstance();
            if(userReport.getVehicle().getLicensePlate().equals("")){
                userReport.setVehicle(new Vehicle("AB123CD"));
            }else if(!licensePlateRecognizer.recognize(userReport.getMainPicture(),userReport.getVehicle().getLicensePlate())){
                throw new ElaborationException();
            }
            reportsData.addUserReport(userReport);
        }catch (ImageStoreException | PlateNotRecognizedException e) {
            throw new ElaborationException();
        }
    }
}
