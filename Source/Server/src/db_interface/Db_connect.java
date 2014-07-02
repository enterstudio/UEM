
package db_interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Db_connect {
    private Connection con = null;
    
    private final String url = "jdbc:mysql://localhost:3306/uem_db";
    private final String user = "root";
    private final String password = "";
    
    public void connect() {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Db_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public void close() {
        try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Db_connect.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
    }
    
    public Connection getConnection() {
        return con;
    }
}
