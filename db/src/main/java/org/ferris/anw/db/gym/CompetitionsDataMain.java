package org.ferris.anw.db.gym;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.ferris.anw.db.sql.SqlHelper;

/**
 *
 * @author Michael
 */
public class CompetitionsDataMain {
 
    private static SqlHelper helper;
    
    public static void main(String[] args) throws Exception 
    {
        helper = new SqlHelper();
        ResultSet competitionsData = getCompetitions();
        while (competitionsData.next()) {
            copy(competitionsData);           
        }
        competitionsData.close();
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
        
        // end_date
        helper.setDate(rs, "end_date", stmt, ++x);
        
        // league
        stmt.setString(++x, rs.getString("league"));
        
        // type
        stmt.setString(++x, rs.getString("type"));
        
        // is_attendance_planned
        helper.setString(rs, "is_attendance_planned", stmt, ++x);

        // is_registered
        helper.setString(rs, "is_registered", stmt, ++x);
        
        // last_found_on_date
        helper.setDate(rs, "last_found_on_date", stmt, ++x);
        
        // INSERT
        System.out.printf("Copying %d%n", rs.getInt("id"));
        if (stmt.executeUpdate() != 1) {
            throw new RuntimeException("ExecuteUpdate not equal to 1");
        } else {
            System.out.printf("Inserted \"%s\"%n", rs.getString("league"));
        }
    }
    
    private static PreparedStatement insert;
    private static PreparedStatement getInsertStatement() throws Exception {
        if (insert == null) {
            insert = helper.sqlite().prepareStatement("""
                insert into competitions
                (
                    id 
                  , gym_name 
                  , begin_date 
                  , end_date 
                  , league 
                  , type 
                  , is_attendance_planned 
                  , is_registered 
                  , last_found_on_date
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
                )                                                   
            """);
        }
        return insert;
    }
    
    private static ResultSet getCompetitions() throws Exception {
        String sql = """
          select * from competitions order by id asc;
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
