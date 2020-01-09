package com.SafeStreets.access_reports_manager;

import com.SafeStreets.model.UserReport;

import java.util.Date;
import java.util.List;

/**
 * This Interface represents the AccessReport
 */
public interface AccessReportsInterface {

    /**
     * This is the method exposed by the access report manager that can be remotely invoked by a municipality in order to get the reports made by the users in their competence area
     * @param username Is the username of the municipality that tries to access the reports
     * @param password Is the password of the municipality that tries to access the reports
     * @param from This date indicates that the Municipality is interested in the reports sent after this date
     * @param until This date indicates that the Municipality is interested in the reports sent after this date
     * @return Is a list that contains users reports serialized as string
     */
    List<String> accessReports(String username, String password, String from, String until);
}
