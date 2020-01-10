package com.SafeStreets.dataManagerAdapterPack;

/**
 * Interface of the DataManager component. It allows to get check the password of a user or of a Municipality
 * and to check whether a given user or Municipality is already registered.
 * @author Massimiliano Bonetti
 */
public interface ClientDataInterface {

    /**
     * Returns a new instance of a class that implements this interface.
     *
     * @return a new instance of a class that implements this interface
     */
    static ClientDataInterface getClientDataInstance() {
        return new DataManagerAdapter();
    }

    /**
     * It checks whether the given password is correct for the given username, which refers to a user or
     * a Municipality
     * @param username username of a user or a Municipality
     * @param password password to check
     * @return whether the given password is correct for the given username, which refers to a user or
     * a Municipality
     */
    boolean checkPassword(String username, String password);

    /**
     * It checks whether the given username refers to a user or a Municipality already registered.
     * @param username username of a user or a Municipality
     * @return whether the given username refers to a user or a Municipality already registered.
     */
    boolean exists(String username);
}
