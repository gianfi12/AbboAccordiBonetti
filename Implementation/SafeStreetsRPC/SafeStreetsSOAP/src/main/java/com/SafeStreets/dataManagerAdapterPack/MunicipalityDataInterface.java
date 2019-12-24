package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.exceptions.MunicipalityAlreadyPresentException;
import com.SafeStreets.model.Municipality;
import com.SafeStreets.model.Place;
import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;

public interface MunicipalityDataInterface extends ClientDataInterface {
    void addMunicipality(Place place, String username, String password) throws MunicipalityAlreadyPresentException;
    Place getMunicipalityArea(String username) throws MunicipalityNotPresentException;
    boolean checkContractCode(String code);
    Municipality getMunicipality(String username, String password) throws WrongPasswordException, MunicipalityNotPresentException;
}
