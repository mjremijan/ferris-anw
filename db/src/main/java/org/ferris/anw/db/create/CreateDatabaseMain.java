package org.ferris.anw.db.create;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.ferris.anw.db.sql.Connections;

/**
 *
 * @author Michael
 */
public class CreateDatabaseMain {
    
    public static void main(String[] args) throws Exception 
    {
        String sql;
        Connection conn = new Connections().sqlite();
        
        //
        // GYMS
        //
        sql = """
            create table if not exists gyms (
                  id integer primary key autoincrement
                , gym_name text unique not null
                , gym_website text not null
                , gym_address_google_map text not null
                , gym_drive_hours int not null
                , gym_drive_minutes int not null
                , gym_address1 text not null
                , gym_city text not null
                , gym_state text not null
                , gym_zipcode text not null
                , gym_country text               
            );
        """;     
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("The gyms table created.");
        } catch (Exception e) {
            throw e;
        }     
        
        
        //
        // HOTELS
        //
        sql = """
            create table if not exists hotels (                  
                  gym_name text not null references gyms(gym_name)
                , hotel_name text not null
                , hotel_address_google_map text not null
                , UNIQUE(gym_name, hotel_name)              
            );
            """;     
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("The hotels table created.");
        } catch (Exception e) {
            throw e;
        }  
        
        //
        // GYM_ALIASES
        //
        sql = """
            create table if not exists gym_aliases (                  
                  gym_id integer not null references gyms(id)
                , gym_alias text unique not null
            );
            """;     
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("The gym_aliases table created.");
        } catch (Exception e) {
            throw e;
        }         
        
        
        //
        // COMPETITIONS
        //
        // YYYY-MM-DD HH:MM:SS
        sql = """
            create table if not exists competitions (
                  id integer primary key autoincrement
                , gym_name text not null references gyms(gym_name)
                , begin_date varchar(10) not null
                , end_date varchar(10)
                , league text not null
                , type text not null
                , is_attendance_planned text
                , is_registered text
                , last_found_on_date varchar(10) not null
            );
            """;     
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Table created.");
        } catch (Exception e) {
            throw e;
        }
        
        
        
//        sql = """
//            select * from users
//            """;     
//        
//        try (PreparedStatement stmt = conn.prepareStatement(sql);
//                ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                System.out.printf("%s, %s, %s%n", rs.getString("id"), rs.getString("name"), rs.getString("email"));
//            }
//            System.out.println("Table created.");
//        } catch (Exception e) {
//            throw e;
//        }
        System.out.printf("Done!%n");
    }    
}
