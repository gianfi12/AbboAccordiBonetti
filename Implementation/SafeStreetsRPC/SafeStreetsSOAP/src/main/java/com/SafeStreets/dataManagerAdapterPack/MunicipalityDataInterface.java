package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.model.DataIntegrationInfo;
import com.SafeStreets.model.Municipality;
import com.SafeStreets.model.Place;
import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;

import java.util.List;

public interface MunicipalityDataInterface extends ClientDataInterface {
    void addMunicipality(Place place, String username, String password);
    Place getMunicipalityArea(String username);
    void addDataIntegration(String username, DataIntegrationInfo dataIntegrationInfo);
    Place checkContractCode(String code);
    DataIntegrationInfo getDataIntegrationInfo(Place place);
    List<DataIntegrationInfo> getAllDataIntegrationInfo();
    Municipality getMunicipality(String username, String password) throws WrongPasswordException, MunicipalityNotPresentException;
}
