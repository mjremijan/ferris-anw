
package org.ferris.anw.legacy.gym;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.ferris.anw.legacy.sql.ConnectionToRepository;

/**
 *
 * @author Michael
 */
public class GymRepository {
    
    private Connection conn;
    
    public GymRepository(ConnectionToRepository conn) {
        this.conn = conn.get();
    }
    
    public Gym findGymByNameOrAlias(String nameOrAlias) {
        try (
            PreparedStatement stmt = findGymByNameOrAliasStatement(nameOrAlias);
            ResultSet rs = stmt.executeQuery();
        ){
            if (rs.next()) {
                return new Gym(
                      rs.getLong("id")
                    , rs.getString("gym_name")
                );
            } else {
                return new Gym(nameOrAlias);
            }
        } catch (Exception e) {
            throw new RuntimeException("ERROR finding gym by either name or alias", e);
        }
    }
    private PreparedStatement findGymByNameOrAliasStatement(String name) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("""
            SELECT 
                g.id        AS id, 
                g.gym_name  AS gym_name, 
                a.gym_alias AS gym_alias
            FROM 
                gyms g
            LEFT JOIN 
                gym_aliases a 
            ON 
                g.id = a.gym_id
            where
                g.gym_name = ? OR a.gym_alias = ?
        """);
        stmt.setString(1, name);
        stmt.setString(2, name);
        return stmt;
    }
    public int insert(GymRecord gym) {
        try (
            PreparedStatement stmt = insertStatement(gym);
        ){
            return stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("ERROR inserting gym", e);
        }
    }
    private PreparedStatement insertStatement(GymRecord gym) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("""
            INSERT INTO gyms
            (
                  gym_name
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
            VALUES
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
            )
        """);
        int x=0;        
        // gym_name
        stmt.setString(++x, gym.getName());
        
        // gym_website
        stmt.setString(++x, gym.getWebsite());
        
        // gym_address_google_map
        stmt.setString(++x, "https://www.google.com/maps/dir/2270+Birmingham+Drive,+Belleville,+IL+62221-7996,+USA/"+URLEncoder.encode(gym.getFullAddress(), StandardCharsets.UTF_8));
        
        // gym_drive_hours
        stmt.setInt(++x, gym.getDriveHours());
        
        // gym_drive_minutes
        stmt.setInt(++x, gym.getDriveMinutes());
        
        // gym_address1
        stmt.setString(++x, gym.getAddress());
        
        // gym_city
        stmt.setString(++x, gym.getCity());
        
        // gym_state
        stmt.setString(++x, gym.getState());
        
        // gym_zipcode
        stmt.setString(++x, gym.getZip());
        
        // gym_country         
        stmt.setString(++x, gym.getCountry());
        return stmt;
    }
    
}
