
package org.ferris.anw.legacy.attendance;

import static java.lang.String.format;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.ferris.anw.legacy.sql.ConnectionToRepository;

/**
 *
 * @author Michael
 */
public class AttendanceRepository {
    
    private Connection conn;
    
    public AttendanceRepository(ConnectionToRepository conn) {
        this.conn = conn.get();
    }
    
    
    public int load(List<Attendance> attendances) 
    {
        try (
            PreparedStatement stmt = updateStatement();
        ){
            final int[] cnt = new int[]{0};
            attendances.forEach(a -> {
                int x = 0;
                try {
                    // is_attendance_planned
                    if (a.getPlanned() == null) {
                        stmt.setNull(++x, Types.VARCHAR);
                    } else {
                        stmt.setString(++x, a.getPlanned());
                    }

                    // is_registered
                    if (a.getRegistered()== null) {
                        stmt.setNull(++x, Types.VARCHAR);
                    } else {
                        stmt.setString(++x, a.getRegistered());
                    }

                    // id
                    stmt.setLong(++x, a.getId());
                
                    int m = stmt.executeUpdate();
                    if (m != 1) {
                        throw new SQLException(
                            format("Attendence information updated %d rows instead of 1 for %s", m, a)
                        );
                    }
                   cnt[0] = cnt[0] + m;
                } catch (SQLException e) {
                    throw new RuntimeException("ERROR updating attendence information", e);
                }
            });
            return cnt[0];
        } catch (Exception e) {
            throw new RuntimeException("ERROR updating attendance information", e);
        }
    }
    private PreparedStatement updateStatement() throws Exception {
        PreparedStatement stmt = conn.prepareStatement("""
            UPDATE 
                competitions
            SET 
                  is_attendance_planned = ?
                , is_registered = ?
            WHERE
                  id = ?
        """);
        
        return stmt;
    }
    
}
