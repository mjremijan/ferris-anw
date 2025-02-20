
package org.ferris.anw.legacy.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.ferris.anw.legacy.model.Gym;
import org.ferris.anw.legacy.sql.ConnectionForAnw;

/**
 *
 * @author Michael
 */
public class GymRepository {
    
    private Connection conn;
    
    public GymRepository(ConnectionForAnw conn) {
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
    
}
