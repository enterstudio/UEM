
package db_interface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Db_data {
    Db_connect db_con;
    Connection con;

    public Db_data(Db_connect con) {
        this.db_con = con;
        this.con = con.getConnection();
    }
    
    public void getAllParkingBays() {
        try {
            //db_con.connect();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM parking_bay");
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String identifier = rs.getString("identifier");
                int parking_lot_id = rs.getInt("parking_lot_id");
                Boolean state = rs.getBoolean("state");
                
                // Test parkingbay retrieval
                System.out.println("id" + ", " + identifier + ", " + parking_lot_id + ", " + state);
                // End test
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Db_data.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } /*finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Db_data.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        
        }*/
    }
}
