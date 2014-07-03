
package db_interface;

import db_objects.ParkingBay;
import db_objects.ParkingLot;
import db_objects.Personnel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Db_data {
    private final Db_connect conn;

    public Db_data() {
        this.conn = new Db_connect();
    }

    public LinkedHashMap getAllParkingBays() {
        try {
            LinkedHashMap data = new LinkedHashMap();
            conn.connect();
            Connection con = conn.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM parking_bay");
            ResultSet rs = pst.executeQuery();
            ParkingBay tmp = new ParkingBay();
            while (rs.next()) {
                tmp.id = rs.getInt("id");
                tmp.identifier = rs.getString("identifier");
                tmp.parking_lot_id = rs.getInt("parking_lot_id");
                tmp.state = rs.getBoolean("state");
                data.put(tmp.identifier,tmp);
            }
            conn.close();
            return data;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Db_data.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
    
    public LinkedHashMap getAllParkingLots() {
        try {
            LinkedHashMap data = new LinkedHashMap();
            conn.connect();
            Connection con = conn.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM parking_lot");
            ResultSet rs = pst.executeQuery();
            ParkingLot tmp = new ParkingLot();
            while (rs.next()) {
                tmp.id = rs.getInt("id");
                tmp.name = rs.getString("name");
                data.put(tmp.name,tmp);
            }
            conn.close();
            return data;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Db_data.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
    
    public LinkedHashMap getAllPersonnel() {
        try {
            LinkedHashMap data = new LinkedHashMap();
            conn.connect();
            Connection con = conn.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM personnel");
            ResultSet rs = pst.executeQuery();
            Personnel tmp = new Personnel();
            while (rs.next()) {
                tmp.id = rs.getInt("id");
                tmp.username = rs.getString("username");
                tmp.password = rs.getString("password");
                tmp.name = rs.getString("name");
                tmp.surname = rs.getString("surname");
                tmp.tel = rs.getString("tel");
                tmp.email = rs.getString("email");
                tmp.level = rs.getInt("level");
                data.put(tmp.username,tmp);
            }
            conn.close();
            return data;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Db_data.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
    public void updateParkingBayFromIdentifier(String sensorID, boolean state, String timeStamp) {
        try {
            conn.connect();
            Connection con = conn.getConnection();

            String query = "UPDATE parking_bay SET state = ?, time_of_change = ? WHERE identifier = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setBoolean(1, state);
            pst.setString(2, timeStamp);
            pst.setString(3, sensorID);
            
            pst.executeUpdate();
            conn.close();            
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Db_data.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
