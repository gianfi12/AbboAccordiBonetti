package com.SafeStreets;

import com.SafeStreets.exceptions.ElaborationException;
import com.SafeStreets.model.UserReport;

/**
 * This is the class that implements the method of the elaboration manager
 */
public class ElaborationManager implements ElaborationManagerInterface {

    /**
     * This method is called to elaborate a report sent by a User
     * @param userReport Is the user's report
     * @throws ElaborationException Is thrown if some errors occurs during the elaboration
     */
    @Override
    public void elaborate(UserReport userReport) throws ElaborationException {
        //TODO implement elaboration
    }
}
