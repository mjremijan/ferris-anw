package org.ferris.anw.legacy.main;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Michael
 */
public class CompetitionsToImportMain {

    class Competition {
        String gymName;
        Date beginDate;
        Date endDate;
        String type;
        String league;
        String isAttendancePlanned;
        String isRegistered;
        Long id;
        
        private static DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("M/d/yyyy");

        public Competition(String gymName, String beginDate, String endDate, String type, String league, String isAttendancePlanned, String isRegistered) {
            this.gymName = gymName;
            this.beginDate = Date.valueOf(LocalDate.parse(beginDate, formatter));
            this.endDate = endDate == null ? null : endDate.isEmpty() ? null : endDate.isBlank() ? null : Date.valueOf(LocalDate.parse(endDate, formatter));
            this.type = type;
            this.league = league;
            this.isAttendancePlanned = isAttendancePlanned.isEmpty() ? null : isAttendancePlanned.isBlank() ? null : isAttendancePlanned;
            this.isRegistered = isRegistered.isEmpty() ? null : isRegistered.isBlank() ? null : isRegistered;
        }

        @Override
        public String toString() {
            return "Competition{" + "gymName=" + gymName + ", beginDate=" + beginDate + ", endDate=" + endDate + ", type=" + type + ", league=" + league + ", isAttendancePlanned=" + isAttendancePlanned + ", isRegistered=" + isRegistered + '}';
        }
    }

    String databaseURL = "jdbc:ucanaccess://D://Documents//Databases//Access//ANW.accdb";
    
    Date now = Date.valueOf(LocalDate.now());
    
    Connection conn;
                
    public static void main(String[] args) throws Exception {
        new CompetitionsToImportMain().go();
    }

    public void go() throws Exception 
    {
        System.out.printf("Starting CompetitionsToImportMain%n%n");
        
        File file = new File("../competitions-to-import.txt");
        LineNumberReader reader 
            = new LineNumberReader(new FileReader(file));
        
        
        List<Competition> comps = new LinkedList<>();
        for (String line=reader.readLine(); line != null; line=reader.readLine()) {
            if (line.startsWith("#")) {
                continue;
            }
            if (line.isBlank()) {
                continue;
            }
            String[] tokens = line.split("\\t",-1);
            String gymName = tokens[0];
            String beginDate = tokens[1];
            String endDate = tokens[2];
            String type = tokens[3];
            String league = tokens[4];
            String isAttendancePlanned = tokens[5];
            String isRegistered = tokens[6];
            comps.add(
                new Competition(gymName, beginDate, endDate, type, league, isAttendancePlanned, isRegistered)
            );
        }
        
        conn = DriverManager.getConnection(databaseURL);
        
        find(comps);
        update(comps.stream().filter(c -> c.id != null).collect(Collectors.toList()));
        insert(comps.stream().filter(c -> c.id == null).collect(Collectors.toList()));        
        
        System.out.printf("%n%nStopping CompetitionsToImportMain%n");
    }
    
    private void update(List<Competition> comps) {
        System.out.printf("FOUND: %d%n", comps.size());
        for (Competition comp : comps) {
            try (PreparedStatement stmt = createUpdateStatement(comp);) 
            {
                int i = stmt.executeUpdate();
                if (i == 0) {
                    throw new RuntimeException(
                        String.format("Failed to update! %s",comp)
                    );
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void insert(List<Competition> comps) {
        System.out.printf("NEW: %d%n", comps.size());
    }
    
    private void find(List<Competition> comps) throws Exception {
        for (Competition comp : comps) {
            try (PreparedStatement stmt = createSelectStatement(comp); 
                 ResultSet rs = stmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                    // get the ID of the matching row in the database
                    comp.id = rs.getLong("ID");
                    
                    // Check if there is more than one match in the DB, which there shouldn't be.
                    if (rs.next()) {
                        throw new RuntimeException(
                            String.format("Duplicate! %s",comp)
                        );
                    }
                } 
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private PreparedStatement createUpdateStatement(Competition comp) 
    throws Exception {
        PreparedStatement stmt
            = conn.prepareStatement(
                " update competitions set "
                + "   end_date = ? "
                + " , is_attendance_planned = ? "
                + " , is_registered = ? "
                + " , last_found_on_date = ? "
                + " where "
                + "  id = ? "
            );
        
        // end_date
        if (comp.endDate == null) {
            stmt.setNull(1, Types.DATE);
        } else {
            stmt.setDate(1, comp.endDate);
        }
        
        // is_attendance_planned
        if (comp.isAttendancePlanned == null) {
            stmt.setNull(2, Types.VARCHAR);
        } else {
            stmt.setString(2, comp.isAttendancePlanned);
        }
        
        // is_registered
        if (comp.isRegistered == null) {
            stmt.setNull(3, Types.VARCHAR);
        } else {
            stmt.setString(3, comp.isRegistered);
        }
        
        // last_found_on_date
        stmt.setDate(4, now);
        
        return stmt;
    }
    
    private PreparedStatement createSelectStatement(Competition comp) 
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
        stmt.setString(1, comp.gymName);
        stmt.setDate(2, comp.beginDate);
        stmt.setString(3, comp.league);
        stmt.setString(4, comp.type);
        
        return stmt;
    }
}
