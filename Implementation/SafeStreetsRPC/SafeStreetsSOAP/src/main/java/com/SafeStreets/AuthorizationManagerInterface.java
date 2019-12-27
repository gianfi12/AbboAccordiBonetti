package com.SafeStreets;

import com.SafeStreets.model.AccessType;
import com.SafeStreets.model.Place;

public interface AuthorizationManagerInterface {
    AccessType getAccessType(String username, String password);
    Place getMunicipality(String username);

}
