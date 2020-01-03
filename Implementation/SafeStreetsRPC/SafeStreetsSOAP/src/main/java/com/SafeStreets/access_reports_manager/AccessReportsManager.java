package com.SafeStreets.access_reports_manager;

import com.SafeStreets.DispatcherInterface;
import com.SafeStreets.model.UserReport;

import java.util.Date;
import java.util.List;

public class AccessReportsManager implements AccessReportsInterface {
    @Override
    public List<String> accessReports(String username, String password, Date from, Date until) {
        DispatcherInterface dispatcherInterface=DispatcherInterface.getInstance();

        return dispatcherInterface.accessReports(username, password, from, until);
    }
}
