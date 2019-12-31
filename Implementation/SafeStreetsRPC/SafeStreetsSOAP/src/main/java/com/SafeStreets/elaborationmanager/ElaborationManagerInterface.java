package com.SafeStreets.elaborationmanager;

import com.SafeStreets.exceptions.ElaborationException;

import com.SafeStreets.model.UserReport;

/**
 * This interface defines the method that the Elaboration Manager has to implements
 */
public interface ElaborationManagerInterface {
    /**
     * This method returns an instance of an Elaboration Manager
     * @return Is an instance of the Elaboration Manager
     */
    static ElaborationManagerInterface getInstance() {
        return new ElaborationManager();
    }
    /**
     * This method is called to elaborate a report sent by a User
     * @param userReport Is the user's report
     * @throws ElaborationException Is thrown if some errors occurs during the elaboration
     */
    void elaborate(UserReport userReport) throws ElaborationException;
}
