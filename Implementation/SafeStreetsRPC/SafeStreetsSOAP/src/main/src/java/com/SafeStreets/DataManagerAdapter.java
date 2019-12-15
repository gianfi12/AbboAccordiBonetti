package com.SafeStreets;

import com.SafeStreets.exceptions.WrongPasswordException;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class DataManagerAdapter implements UserDataInterface {
    private final static Logger LOGGER = Logger.getLogger(DataManagerAdapter.class.getName());

    private Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/SafeStreetsDB?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "max", "maxPass1Samp");

        } catch (Exception e) {
            LOGGER.log(Level.INFO,"Error in the connection to the DataBase\n");
            e.printStackTrace();
            return null;
        }
    }

    private boolean getPath() {
        try
        {
            Connection con = getConnection();
            if(con==null)
                return false;

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


    @Override
    public void addUser(User info, String password) {

        String addPlaceOfBirthQuery="INSERT INTO User values";
        String addUserQuery="INSERT INTO User values";


    }

    @Override
    public User getUser(String username, String password) throws WrongPasswordException {
        String query = "SELECT * FROM User as U, Place as pB, Place as pR, Coordinate as C  WHERE username="+"\""+username+"\""+" and " +
                "u.placeOfBirth_id=pB.id and u.placeOfResidence_id=pR.id and pr.Coordinate_id=C.id";

        try {
            Connection con = getConnection();
            if(con==null)
                return null;

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            String foundUsername="";
            while (!foundUsername.equals(username) && rs.next()) {
                foundUsername = rs.getString("username");
            }

            if(foundUsername.equals(username)) {
                String email = rs.getString("email");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                String picturePath = rs.getString("picture");
                String idCardPath = rs.getString("idCard");
                String fiscalCode = rs.getString("fiscalCode");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                String passwordHash = rs.getString("password");
                String salt = rs.getString("salt");

                if(!AuthorizationManager.verifyPassword(passwordHash, salt, password)) {
                    throw new WrongPasswordException();
                }


                String cityOfBirth=rs.getString(14);
                String addressOfBirth=rs.getString(15);
                String houseCodeOfBirth=rs.getString(16);

                String cityOfResidence=rs.getString(19);
                String addressOfResidence=rs.getString(20);
                String houseCodeOfResidence=rs.getString(21);

                Double latitude=rs.getDouble(24);
                Double longitude=rs.getDouble(25);
                Double altitude=rs.getDouble(26);

                Coordinate coordinate=new Coordinate(latitude, longitude, altitude);

                Place placeOfBirth=new Place(cityOfBirth, addressOfBirth, houseCodeOfBirth, null);
                Place placeOfResidence=new Place(cityOfResidence, addressOfResidence, houseCodeOfResidence, coordinate);

                BufferedImage picture = null;
                if(picturePath!=null) {
                    try
                    {
                        picture = ImageIO.read(new File(picturePath));
                    }
                    catch (Exception e)
                    {
                    }
                }



                BufferedImage idCard = null;
                if(idCardPath!=null) {
                    try {
                        idCard = ImageIO.read(new File(idCardPath));
                    } catch (Exception e) {
                    }
                }


                User user = new User(username, email, firstname, lastname, placeOfBirth, placeOfResidence, picture,
                        idCard, fiscalCode, dateOfBirth, password);

                st.close();

                return user;
            }

            st.close();


        } catch(SQLException e) {
            LOGGER.log(Level.INFO,"Error in executing the query");
            e.printStackTrace();
        }
        return null;
    }
}
