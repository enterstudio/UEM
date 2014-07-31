package logic;

import db_interface.Db_data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Data_services {
    private final Db_data data = new Db_data();
    public void sensorStateChange(String sensorID, boolean state)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        
        data.updateParkingBayFromIdentifier(sensorID, state, timeStamp);
    }
}
