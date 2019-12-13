package com.SafeStreets;

import javax.ejb.Stateless;
import java.sql.*;

@Stateless
public class DataManagerAdapter {

    public boolean getPath() {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/SafeStreetsDB?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT * FROM UserReport";

            // create the java statement
            Statement st = con.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                int id = rs.getInt("id");
                String mainPicturePath = rs.getString("mainPicture");
                Timestamp dateCreated = rs.getTimestamp("reportTimeStamp");

                // print the results
                System.out.format(id+" "+mainPicturePath+" "+dateCreated+"\n");
            }
            st.close();
            return true;
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return false;
        }
    }
}
