
package org.ferris.anw.legacy.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Michael
 */
public class ConnectionToRepository {
  
    private static final String DB_URL 
        = "jdbc:ucanaccess://D://Documents//Databases//Access//ANW-DEV.accdb";

    private static Connection conn;
    static {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public ConnectionToRepository() {}
    
    public Connection get() { 
        return conn;
    }
}
