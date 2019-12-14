package com.SafeStreets;

import javax.ejb.Stateless;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class DataManagerAdapter {
    private final static Logger LOGGER = Logger.getLogger(DataManagerAdapter.class.getName());


    private boolean getPath() {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/SafeStreetsDB?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");

            String query = "SELECT * FROM UserReport";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                int id = rs.getInt("id");
                String mainPicturePath = rs.getString("mainPicture");
                Timestamp dateCreated = rs.getTimestamp("reportTimeStamp");
            }
            st.close();
            return true;
        }
        catch (Exception e)
        {
            LOGGER.log(Level.INFO,"Error User registration in getPath!");
            return false;
        }
    }
}
