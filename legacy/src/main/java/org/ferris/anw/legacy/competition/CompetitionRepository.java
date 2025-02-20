package org.ferris.anw.legacy.competition;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.ferris.anw.legacy.model.Competition;
import org.ferris.anw.legacy.sql.ConnectionToRepository;

/**
 *
 * @author Michael
 */
public class CompetitionRepository {
    
    protected Connection conn;
    
    class Wrapper {
        Optional<Long> id;
        Competition comp;
        public Wrapper(Optional<Long> id, Competition c) {
            this.id = id;
            this.comp = c;
        }
    }
    
    public CompetitionRepository(ConnectionToRepository conn) {
        this.conn = conn.get();
    }
    
    public void load(List<Competition> toLoad) {
        // Try to find each competion in the database
        List<Wrapper> wrappers = toLoad.stream()
            .map(c -> new Wrapper(findId(c), c))
            .collect(Collectors.toList())
        ;
        
        // Competitions to update
        List<Wrapper> toUpdate = wrappers.stream()
            .filter(w -> w.id.isPresent())
            .collect(Collectors.toList())
        ;
        toUpdate.forEach(w -> update(w));
        System.out.printf("Updated %d competitions%n", toUpdate.size());
        
        // Competitions to insert
        List<Wrapper> toInsert = wrappers.stream()
            .filter(w -> w.id.isEmpty())
            .collect(Collectors.toList())
        ;
        toInsert.forEach(w -> insert(w));
        System.out.printf("Inserted %d competitions%n", toInsert.size());
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
            throw new RuntimeException(e);
        }
    }
    private PreparedStatement updateStatement(Wrapper w) 
    throws Exception {
        PreparedStatement stmt
            = conn.prepareStatement(
                " update competitions set "
                + "   end_date = ? "
                + " , last_found_on_date = ? "
                + " where "
                + "  id = ? "
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
            = conn.prepareStatement(
                " select "
                + "  ID "
                + " from "
                + "  competitions "
                + " where "
                + "  gym_name = ? "
                + "  and "
                + "  begin_date = ? "
                + "  and "
                + "  league = ? "
                + "  and "
                + "  type = ? "
            );
        stmt.setString(1, c.getGym().getName());
        stmt.setDate(2, c.getCompetitionDate().getBegin());
        stmt.setString(3, c.getLeague());
        stmt.setString(4, c.getType());
        
        return stmt;
    }
    
}
