package org.ferris.anw.db.gym;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.ferris.anw.db.sql.SqlHelper;

/**
 *
 * @author Michael
 */
public class GymAliasesDataMain {
 
    private static SqlHelper helper;
    
    public static void main(String[] args) throws Exception 
    {
        helper = new SqlHelper();
        try(ResultSet gymAliasesData = getGymAliasesData();) {
            while (gymAliasesData.next()) {
                copy(gymAliasesData);           
            }
        } catch (Exception e) {
            throw e;
        }
        getInsertStatement().close();
        helper.close();
        System.out.printf("DONE%n");
    }
    
    
    private static void copy(ResultSet rs) throws Exception {
        PreparedStatement stmt = getInsertStatement();
        int x=0;
        // gym_id 
        stmt.setInt(++x, rs.getInt("gym_id"));
        
        // gym_alias
        stmt.setString(++x, rs.getString("gym_alias"));
        
        // INSERT
        if (stmt.executeUpdate() != 1) {
            throw new RuntimeException("ExecuteUpdate not equal to 1");
        } else {
            System.out.printf("Inserted \"%s\"%n", rs.getString("gym_alias"));
        }
    }
    
    private static PreparedStatement insert;
    private static PreparedStatement getInsertStatement() throws Exception {
        if (insert == null) {
            insert = helper.sqlite().prepareStatement("""
                insert into gym_aliases
                (
                    gym_id 
                  , gym_alias
                )
                values
                (
                    ? 
                  , ? 
                )                                                   
            """);
        }
        return insert;
    }
    
    private static ResultSet getGymAliasesData() throws Exception {
        String sql = """
          select * from gym_aliases order by gym_id asc;
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
