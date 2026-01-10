package org.ferris.anw.db.gym;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.ferris.anw.db.sql.SqlHelper;

/**
 *
 * @author Michael
 */
public class HotelsDataMain {
 
    private static SqlHelper helper;
    
    public static void main(String[] args) throws Exception 
    {
        helper = new SqlHelper();
        ResultSet hotelData = getHotels();
        while (hotelData.next()) {
            copy(hotelData);           
        }
        hotelData.close();
        getInsertStatement().close();
        helper.close();
        System.out.printf("DONE%n");
    }
    
    
    private static void copy(ResultSet rs) throws Exception {
        PreparedStatement stmt = getInsertStatement();
        int x=0;
        // gym_name 
        stmt.setString(++x, rs.getString("gym_name"));
        
        // hotel_name
        stmt.setString(++x, rs.getString("hotel_name"));
        
        // hotel_address_google_map 
        helper.setUrl(rs, "hotel_address_google_map", stmt, ++x);        
        
        // INSERT
        if (stmt.executeUpdate() != 1) {
            throw new RuntimeException("ExecuteUpdate not equal to 1");
        } else {
            System.out.printf("Inserted \"%s\"%n", rs.getString("hotel_name"));
        }
    }
    
    private static PreparedStatement insert;
    private static PreparedStatement getInsertStatement() throws Exception {
        if (insert == null) {
            insert = helper.sqlite().prepareStatement("""
                insert into gym_aliases
                (
                    gym_name 
                  , hotel_name
                  , hotel_address_google_map 
                )
                values
                (
                    ? 
                  , ? 
                  , ?
                )                                                   
            """);
        }
        return insert;
    }
    
    private static ResultSet getHotels() throws Exception {
        String sql = """
          select * from hotels;
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
