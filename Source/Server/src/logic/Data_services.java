package logic;

import db_interface.Db_data;
import db_objects.ParkingBay;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Data_services {
    private final Db_data data = new Db_data();
    
    public void addNewParkingBay(String sensorID, String lotID, String timeStamp) {
        
        data.addNewParkingBayToDB(sensorID,lotID,timeStamp);
    }
    
    public HashMap<String, Integer> getBayUsage() {
        return data.getFromLog("STATE","false");
    }
    
    public void sensorStateChange(String sensorID, boolean state)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        
        data.updateParkingBayFromIdentifier(sensorID, state, timeStamp);
        data.logChange(sensorID, "STATE", String.valueOf(state), timeStamp);
    }
    
    public List<ParkingBay> getAllParkingBaysForUI() {
        try {
            List<ParkingBay> results = new ArrayList();
            
            ResultSet rs = data.get("SELECT * " +
                                    "FROM parking_bay AS p " +
                                    "INNER JOIN parking_bay_ui AS pui ON p.id = pui.parking_bay_id");
            while (rs.next()) {
                ParkingBay tmp = new ParkingBay();
                tmp.id = rs.getInt("id");
                tmp.identifier = rs.getString("identifier");
                tmp.parking_lot_id = rs.getInt("parking_lot_id");
                tmp.state = rs.getBoolean("state");
                tmp.x = rs.getInt("x");
                tmp.y = rs.getInt("y");
                tmp.datetime = rs.getString("time_of_change");
                tmp.rotation = rs.getDouble("rotation");
                results.add(tmp);
            }
            data.closeConn();
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Data_services.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void savePositionForUI(String id, double x, double y) {
        data.updateUIPosition(id,(int) x,(int) y);
    }
    
    public Boolean getTimeStampByID(String id) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date test = new Date();
        String timeStamp = dateFormat.format(test);
        LinkedHashMap results = data.getTimeStampForUI(timeStamp);
        return results.containsKey(id);
    }

    public void deleteParkingBay(String bayId) {
        data.deleteParkingBay(bayId);
    }

    public void addNewParkingBay(String sensorID, String lotID, int x, int y, Double rot, String timeStamp) {
        data.addNewParkingBayToDB(sensorID,lotID,x,y,rot,timeStamp);
    }
}
