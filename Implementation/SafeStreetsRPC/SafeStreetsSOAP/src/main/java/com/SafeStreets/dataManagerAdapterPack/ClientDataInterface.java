package com.SafeStreets.dataManagerAdapterPack;

import com.SafeStreets.data_analysis_manager.DataAnalysisInterface;
import com.SafeStreets.data_analysis_manager.DataAnalysisManager;

public interface ClientDataInterface {

    /**
     * Returns a new instance of a class that implements this interface.
     *
     * @return a new instance of a class that implements this interface
     */
    static ClientDataInterface getClientDataInstance() {
        return new DataManagerAdapter();
    }

    boolean checkPassword(String username, String password);

    boolean exists(String username);
}
