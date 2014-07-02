
package server;

import db_interface.Db_data;
import java.util.LinkedHashMap;

public class Server {
    public static void main(String[] args) {
        // Testing database connection
        Db_data data = new Db_data();
        LinkedHashMap tmp = data.getAllParkingBays();
        // End test
    }
}
