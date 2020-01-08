package com.SafeStreets.access_reports_manager;

import com.SafeStreets.model.UserReport;

import java.util.Date;
import java.util.List;

public interface AccessReportsInterface {

    List<String> accessReports(String username, String password, String from, String until);
}
