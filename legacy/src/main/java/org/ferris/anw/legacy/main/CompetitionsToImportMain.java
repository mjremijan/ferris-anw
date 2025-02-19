package org.ferris.anw.legacy.main;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

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
        private static DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("M/d/yyyy");

        public Competition(String gymName, String beginDate, String endDate, String type, String league, String isAttendancePlanned, String isRegistered) {
            this.gymName = gymName;
            this.beginDate = Date.valueOf(LocalDate.parse(beginDate, formatter));
            this.endDate = endDate == null ? null : endDate.isEmpty() ? null : endDate.isBlank() ? null : Date.valueOf(LocalDate.parse(endDate, formatter));
            this.type = type;
            this.league = league;
            this.isAttendancePlanned = isAttendancePlanned;
            this.isRegistered = isRegistered;
        }

        @Override
        public String toString() {
            return "Competition{" + "gymName=" + gymName + ", beginDate=" + beginDate + ", endDate=" + endDate + ", type=" + type + ", league=" + league + ", isAttendancePlanned=" + isAttendancePlanned + ", isRegistered=" + isRegistered + '}';
        }
    }

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
        
        String databaseURL = "jdbc:ucanaccess://D://Documents//Databases//Access//ANW.accdb";
        
        Connection conn = DriverManager.getConnection(databaseURL);
        
        int found = 0, notfound = 0;
        for (Competition comp : comps)
        {
            try (PreparedStatement stmt = createPreparedStatement(conn, comp); 
                 ResultSet rs = stmt.executeQuery()) 
            {
                if (rs.next()) {
                    // FOUND!
                    found++;
                    if (rs.next()) {
                        throw new RuntimeException(
                            String.format("Duplicate! %s",comp)
                        );
                    }
                } else {
                    // NOT FOUND!
                    notfound++;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.printf("FOUND: %d%n",found);
        System.out.printf("NOT FOUND: %d%n",notfound);
        
        System.out.printf("%n%nStopping CompetitionsToImportMain%n");
    }

    private PreparedStatement createPreparedStatement(Connection conn, Competition comp) 
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
