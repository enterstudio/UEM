
package server;

import db_interface.Db_connect;
import db_interface.Db_data;

public class Server {
    public static void main(String[] args) {
        // Testing database connection
        db_interface.Db_connect con = new Db_connect();
        con.connect();
        db_interface.Db_data data = new Db_data(con);
        data.getAllParkingBays();
        con.close();
        // End test
    }
    
}
