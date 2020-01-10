package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.exceptions.MunicipalityAlreadyPresentException;
import com.SafeStreets.model.Municipality;
import com.SafeStreets.model.Place;
import com.SafeStreets.exceptions.MunicipalityNotPresentException;
import com.SafeStreets.exceptions.WrongPasswordException;

/**
 * Interface of the DataManager component. It allows to add a Municipality, to get the city of a
 * Municipality, to check whether a contract code has been registered and to get a Municipality object.
 * @author Massimiliano Bonetti
 */
public interface MunicipalityDataInterface extends ClientDataInterface {
    /**
     * Returns a new instance of a class that implements this interface.
     *
     * @return a new instance of a class that implements this interface
     */
    static MunicipalityDataInterface getMunicipalityDataInstance() {
        return new DataManagerAdapter();
    }

    /**
     * It saves a Municipality with the given username and password corresponding to the given contract code.
     * @param contractCode contract code of the Municipality to add
     * @param username username of the Municipality to add
     * @param password password of the Municipality to add
     * @throws MunicipalityAlreadyPresentException if the username is already present
     */
    void addMunicipality(String contractCode, String username, String password) throws MunicipalityAlreadyPresentException;

    /**
     * It returns the place that contains the city of the Municipality with the given username
     * @param username username of the Municipality to consider
     * @return place that contains the city of the Municipality with the given username
     * @throws MunicipalityNotPresentException if no Municipality has the given username
     */
    Place getMunicipalityArea(String username) throws MunicipalityNotPresentException;

    /**
     * It checks whether the given contract code has been registered
     * @param code contract code to check whether it has been registered
     * @return whether the given contract code has been registered
     */
    boolean checkContractCode(String code);

    /**
     * It returns the Municipality with the given username and if the given password is correct.
     * @param username username of the Municipality to get
     * @param password password of the Municipality to get
     * @return the Municipality with the given username and if the given password is correct.
     * @throws WrongPasswordException if the given password is not correct
     * @throws MunicipalityNotPresentException if no Municipality has the given username
     */
    Municipality getMunicipality(String username, String password) throws WrongPasswordException, MunicipalityNotPresentException;
}
