package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.data_analysis_manager.DataAnalysisInterface;
import com.SafeStreets.data_analysis_manager.DataAnalysisManager;
import com.SafeStreets.exceptions.MunicipalityAlreadyPresentException;
import com.SafeStreets.exceptions.PlaceForMunicipalityNotPresentException;
import com.SafeStreets.model.Municipality;
import com.SafeStreets.model.Place;
import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;

public interface MunicipalityDataInterface extends ClientDataInterface {
    /**
     * Returns a new instance of a class that implements this interface.
     *
     * @return a new instance of a class that implements this interface
     */
    static MunicipalityDataInterface getInstance() {
        return new DataManagerAdapter();
    }

    void addMunicipality(String contractCode, String username, String password) throws MunicipalityAlreadyPresentException, PlaceForMunicipalityNotPresentException;
    Place getMunicipalityArea(String username) throws MunicipalityNotPresentException;
    boolean checkContractCode(String code);
    Municipality getMunicipality(String username, String password) throws WrongPasswordException, MunicipalityNotPresentException;
}
