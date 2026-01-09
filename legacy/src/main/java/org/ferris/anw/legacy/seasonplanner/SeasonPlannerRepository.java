package org.ferris.anw.legacy.seasonplanner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.ferris.anw.legacy.competition.*;
import org.ferris.anw.legacy.sql.ConnectionToRepository;

/**
 *
 * @author Michael
 */
public class SeasonPlannerRepository {
    
    protected Connection conn;
    
    public SeasonPlannerRepository(ConnectionToRepository conn) {
        this.conn = conn.get();
    }
    
    public void getReport() {
    }
    
    
    private void insert(Wrapper w) {
        try (PreparedStatement stmt = insertStatement(w.comp);) 
        {
            int i = stmt.executeUpdate();
            if (i == 0) {
                throw new RuntimeException(
                    String.format("Failed to insert! %s", w.comp)
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    SELECT Format([begin_date],"mm/dd ddd") AS [Date Start], Format([end_date],"mm/dd ddd") AS [Date End], competitions.is_attendance_planned AS Planned, competitions.id AS ID, competitions.is_registered AS Registered, gyms.gym_website AS Gym, competitions.league AS League, competitions.type AS Type, gyms.gym_address_google_map AS Location, [gym_drive_hours] & "h " & [gym_drive_minutes] & "m" AS Drive, hotels.hotel_address_google_map AS Hotel
FROM (gyms INNER JOIN competitions ON gyms.gym_name = competitions.gym_name) LEFT JOIN hotels ON gyms.gym_name = hotels.gym_name
WHERE (((gyms.gym_drive_hours)<=8))
ORDER BY competitions.begin_date, gyms.gym_drive_hours, gyms.gym_drive_minutes, competitions.gym_name;
    
    private PreparedStatement insertStatement(Competition comp) 
    throws Exception {
        PreparedStatement stmt
            = conn.prepareStatement(
                  " insert into competitions "
                + " (gym_name, begin_date, end_date, league, type, last_found_on_date) "
                + "   values "
                + " (?, ?, ?, ?, ?, ?) "
            );
        
        int x=0;
        
        // gym_name
        stmt.setString(++x, comp.getGym().getName());
        
        // begin_date
        stmt.setDate(++x, comp.getCompetitionDate().getBegin());
        
        // end_date, 
        Date endDate = comp.getCompetitionDate().getEnd();
        if (endDate == null) {
            stmt.setNull(++x, Types.DATE);
        } else {
            stmt.setDate(++x, endDate);
        }
        
        // league
        stmt.setString(++x, comp.getLeague());
        
        // type
        stmt.setString(++x, comp.getType());
        
        // last_found_on_date
        stmt.setDate(++x, Date.valueOf(LocalDate.now()));
        
        return stmt;
    }
    
    
    private void update(Wrapper w) {
        try (PreparedStatement stmt = updateStatement(w);) 
        {
            int i = stmt.executeUpdate();
            if (i == 0) {
                throw new RuntimeException(
                    String.format("Failed to update! %s", w.comp)
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(w.toString(), e);
        }
    }
    private PreparedStatement updateStatement(Wrapper w) 
    throws Exception {
        PreparedStatement stmt
            = conn.prepareStatement("""                                    
                 update competitions set 
                   end_date = ? 
                 , last_found_on_date = ? 
                 where 
                  id = ? """
            );
        
        // end_date
        Date endDate = w.comp.getCompetitionDate().getEnd();
        if (endDate == null) {
            stmt.setNull(1, Types.DATE);
        } else {
            stmt.setDate(1, endDate);
        }
        
        // last_found_on_date
        stmt.setDate(2, Date.valueOf(LocalDate.now()));
        
        // ID
        stmt.setLong(3, w.id.get());
        
        return stmt;
    }


    
    private Optional<Long> findId(Competition c) {
        try (PreparedStatement stmt = findIdStatement(c); 
             ResultSet rs = stmt.executeQuery()) 
        {
            Optional<Long> id = Optional.empty();
            
            if (rs.next()) 
            {
                // get the ID of the matching row in the database
                id = Optional.of(rs.getLong("ID"));

                // Check if there is more than one match in the DB, which there shouldn't be.
                // If there is, panic!
                if (rs.next()) {
                    throw new RuntimeException(
                        String.format("ERROR Duplicate competition found in the database! %s",c)
                    );
                }
            } 
            
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private PreparedStatement findIdStatement(Competition c) 
    throws Exception {
        PreparedStatement stmt
            = conn.prepareStatement("""                                    
                 select 
                  ID 
                 from 
                  competitions 
                 where 
                  gym_name = ? 
                  and 
                  begin_date = ? 
                  and 
                  league = ? 
                  and 
                  type = ? 
            """);
        stmt.setString(1, c.getGym().getName());
        stmt.setDate(2, c.getCompetitionDate().getBegin());
        stmt.setString(3, c.getLeague());
        stmt.setString(4, c.getType());
        
        return stmt;
    }
    
    public int vacuum(List<CompetitionType> competitionTypes) {
        try (PreparedStatement stmt = vacuumStatement();
        ){
            AtomicInteger cnt 
                = new AtomicInteger(0);

            competitionTypes.forEach(ct -> {
                try {
                    // league
                    stmt.setString(1, ct.getLeague());
                    // type
                    stmt.setString(2, ct.getType());
                    // delete and count
                    cnt.addAndGet(stmt.executeUpdate());
                } catch (SQLException e) {
                    throw new RuntimeException(
                        String.format("ERROR vacuuming for competitionType=%s", ct)
                    );
                }
            });
            return cnt.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private PreparedStatement vacuumStatement() 
    throws Exception {
        PreparedStatement stmt
            = conn.prepareStatement("""
                delete from competitions
                where 
                    league = ?
                    and
                    type = ?
                    and
                    last_found_on_date < ?                                 
            """
            );       
        stmt.setDate(3, Date.valueOf(LocalDate.now()));
        return stmt;
    }

}

