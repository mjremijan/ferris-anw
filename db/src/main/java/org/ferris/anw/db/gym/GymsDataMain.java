package org.ferris.anw.db.gym;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.ferris.anw.db.sql.Connections;

/**
 *
 * @author Michael
 */
public class GymsDataMain {
 
    public static void main(String[] args) throws Exception 
    {
        String sql;
        Connections conns = new Connections();
        
        sql = """
          select * from gyms order by id asc
        """;
        try (PreparedStatement stmt = conns.access().prepareStatement(sql);
           ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                System.out.printf("%d%n", rs.getInt("id"));            
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
