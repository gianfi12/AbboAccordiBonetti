package com.SafeStreets.access_reports_manager;

import com.SafeStreets.DispatcherInterface;

import java.util.Date;
import java.util.List;

public class AccessReportsManager implements AccessReportsInterface {
    @Override
    public List<String> accessReports(String username, String password, String from, String until) {
        DispatcherInterface dispatcherInterface=DispatcherInterface.getInstance();

        return dispatcherInterface.accessReports(username, password, from.toString(), until.toString());
    }
}
