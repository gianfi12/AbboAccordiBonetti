package com.SafeStreets;

import java.util.List;

public interface MunicipalityDataInterface {
    void addMunicipality(Place place, String username, String password);
    Place getMunicipalityArea(String username);
    void addDataIntegration(String username, DataIntegrationInfo dataIntegrationInfo);
    Place checkContractCode(String code);
    DataIntegrationInfo getDataIntegrationInfo(Place place);
    List<DataIntegrationInfo> getAllDataIntegrationInfo();
}
