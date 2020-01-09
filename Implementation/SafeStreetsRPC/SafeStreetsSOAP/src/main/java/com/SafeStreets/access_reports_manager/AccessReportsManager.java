package com.SafeStreets.access_reports_manager;

import com.SafeStreets.DispatcherInterface;

import java.util.List;

/**
 * This class implements the Access Reports interface, and it is used by the municipality in order to get the reports sent by the user ot the system
 */
public class AccessReportsManager implements AccessReportsInterface {

    /**
     * This is the method exposed by the access report manager that can be remotely invoked by a municipality in order to get the reports made by the users in their competence area
     * @param username Is the username of the municipality that tries to access the reports
     * @param password Is the password of the municipality that tries to access the reports
     * @param from This date indicates that the Municipality is interested in the reports sent after this date
     * @param until This date indicates that the Municipality is interested in the reports sent after this date
     * @return Is a list that contains users reports serialized as string
     */
    @Override
    public List<String> accessReports(String username, String password, String from, String until) {
        DispatcherInterface dispatcherInterface=DispatcherInterface.getInstance();

        return dispatcherInterface.accessReports(username, password, from.toString(), until.toString());
    }
}
