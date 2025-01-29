package org.ferris.anw.db.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Enumeration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michael
 */
public class ConnectionTest {

    @Test
    public void test_driver_information() throws Exception {
        Connection conn = DriverManager.getConnection(
            "jdbc:derby:memory:foo;create=true", "sa", "sa"
        );
        Assertions.assertEquals(
            "10.17.1.0 - (1913217)", conn.getMetaData().getDriverVersion()
        );
        Assertions.assertEquals(
            "Apache Derby Embedded JDBC Driver", conn.getMetaData().getDriverName()
        );
        for (Enumeration<Driver> d = DriverManager.getDrivers(); d.hasMoreElements();) {
            Assertions.assertEquals(
                "org.apache.derby.iapi.jdbc.AutoloadedDriver", d.nextElement().getClass().getName()
            );
        }
    }
           
    @Test
    public void test_embedded_10_17_1_0() throws Exception {
        // ij> connect 'jdbc:derby:D:\Development\projects\ferris-anw\db\target\junit\ConnectionTest\data\derby-10.17.1.0\test.db;create=true;create=true' user 'sa' password 'sa';
        File f = new File("./target/junit/ConnectionTest/data/derby-10.17.1.0/test.db;create=true").getCanonicalFile();
        System.out.printf("%s%n", f.getCanonicalPath());
        Connection conn = DriverManager.getConnection(
            String.format("jdbc:derby:%s", "D:\\Development\\projects\\ferris-anw\\db\\target\\junit\\ConnectionTest\\data\\derby-10.17.1.0\\test.db;create=true"), "sa", "sa"
        );
        ResultSet rs
            = conn.createStatement().executeQuery("values syscs_util.syscs_get_database_property('DataDictionaryVersion')");
        rs.next();
        Assertions.assertEquals(
            "10.17", rs.getString(1)
        );
    }    
}
