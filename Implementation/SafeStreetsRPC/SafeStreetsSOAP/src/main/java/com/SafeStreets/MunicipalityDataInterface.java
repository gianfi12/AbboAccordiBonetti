package com.SafeStreets;

import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;

import java.util.List;

public interface MunicipalityDataInterface {
    void addMunicipality(Place place, String username, String password);
    Place getMunicipalityArea(String username);
    void addDataIntegration(String username, DataIntegrationInfo dataIntegrationInfo);
    Place checkContractCode(String code);
    DataIntegrationInfo getDataIntegrationInfo(Place place);
    List<DataIntegrationInfo> getAllDataIntegrationInfo();
    Municipality getMunicipality(String username,String password) throws WrongPasswordException, MunicipalityNotPresentException;
}
