
package org.ferris.anw.legacy.sql;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Michael
 */
public class ConnectionToAnwDb {
  
    String databaseURL 
        = "jdbc:ucanaccess://D://Documents//Databases//Access//ANW-DEV.accdb";

    private Connection conn;
    
    private ConnectionToAnwDb() throws Exception {
        conn = DriverManager.getConnection(databaseURL);
    }
    
    private static ConnectionToAnwDb instance;
    static {
        try {
            instance = new ConnectionToAnwDb();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static ConnectionToAnwDb getInstance() {
        return instance;
    }
    
    public Connection get() { 
        return conn;
    }
}
