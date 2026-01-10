package org.ferris.anw.db.gym;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.ferris.anw.db.sql.SqlHelper;

/**
 *
 * @author Michael
 */
public class GymsDataMain {
 
    private static SqlHelper helper;
    
    public static void main(String[] args) throws Exception 
    {
        helper = new SqlHelper();
        ResultSet gymData = getGyms();
        while (gymData.next()) {
            copy(gymData);           
        }
        gymData.close();
        getInsertStatement().close();
        helper.close();
        System.out.printf("DONE%n");
    }
    
    
    private static void copy(ResultSet rs) throws Exception {
        PreparedStatement stmt = getInsertStatement();
        int x=0;
        // id 
        stmt.setInt(++x, rs.getInt("id"));
        
        // gym_name 
        stmt.setString(++x, rs.getString("gym_name"));
        
        // gym_website
        helper.setUrl(rs, "gym_website", stmt, ++x);
        
        // gym_address_google_map 
        helper.setUrl(rs, "gym_address_google_map", stmt, ++x);
        
        // gym_drive_hours
        stmt.setInt(++x, rs.getInt("gym_drive_hours"));
        
        // gym_drive_minutes 
        stmt.setInt(++x, rs.getInt("gym_drive_minutes"));
        
        // gym_address1
        stmt.setString(++x, rs.getString("gym_address1"));
        
        // gym_city 
        stmt.setString(++x, rs.getString("gym_city"));
        
        // gym_state
        stmt.setString(++x, rs.getString("gym_state"));
        
        // gym_zipcode
        stmt.setString(++x, rs.getString("gym_zipcode"));
        
        // gym_country
        stmt.setString(++x, rs.getString("gym_country"));
        
        // INSERT
        if (stmt.executeUpdate() != 1) {
            throw new RuntimeException("ExecuteUpdate not equal to 1");
        } else {
            System.out.printf("Inserted \"%s\"%n", rs.getString("gym_name"));
        }
    }
    
    private static PreparedStatement insert;
    private static PreparedStatement getInsertStatement() throws Exception {
        if (insert == null) {
            insert = helper.sqlite().prepareStatement("""
                insert into gyms
                (
                    id 
                  , gym_name 
                  , gym_website
                  , gym_address_google_map 
                  , gym_drive_hours 
                  , gym_drive_minutes 
                  , gym_address1 
                  , gym_city 
                  , gym_state
                  , gym_zipcode
                  , gym_country
                )
                values
                (
                    ? 
                  , ? 
                  , ?
                  , ? 
                  , ? 
                  , ? 
                  , ? 
                  , ? 
                  , ?
                  , ?
                  , ?
                )                                                   
            """);
        }
        return insert;
    }
    
    private static ResultSet getGyms() throws Exception {
        String sql = """
          select * from gyms order by id asc
        """;
        ResultSet rs;
        try (PreparedStatement stmt = helper.access().prepareStatement(sql);           
        ) {
            rs = stmt.executeQuery();            
        } catch (Exception e) {
            throw e;
        }
        return rs;
    }
}
