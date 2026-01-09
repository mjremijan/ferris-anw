package org.ferris.anw.legacy.seasonplanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    public SeasonPlannerReport getReport() {
        try (PreparedStatement stmt = selectStatement(); 
             ResultSet rs = stmt.executeQuery()) 
        {
            SeasonPlannerReport report = new SeasonPlannerReport();
            while (rs.next()) 
            {
                SeasonPlannerComp comp = new SeasonPlannerComp();
                // Date start
                {
                    comp.dateStart = rs.getString("Date_Start");
                }
                // Date start year
                {
                    comp.year = rs.getInt("Date_Start_Year");
                }
                // Date start month name
                {
                    comp.month = rs.getString("Date_Start_Month_Name");
                }                
                // Date end
                {
                    comp.dateEnd = rs.getString("Date_End");
                }
                // #ID
                {
                    comp.id = rs.getInt("ID");
                }
                // Planned
                {
                    comp.planned = rs.getString("Planned");
                }
                // Registered
                {
                    comp.registered = rs.getString("Registered");
                }
                // League
                {
                    comp.league = rs.getString("League");
                }
                // Type
                {
                    comp.type = rs.getString("Type");
                }
                // Gym
                {
                    comp.gymName = rs.getString("Gym");
                    //comp.gymLink = rs.getString("Type");
                }
                // Drive
                {
                    comp.drive = rs.getString("Drive");
                }
                // Location
                {
                    comp.cityState = rs.getString("Location");
                    //comp.cityStateLink = rs.getString("Type");
                }
                // Hotel
                {
                    comp.hotel = rs.getString("Hotel");
                    //comp.hotelLink = rs.getString("Type");
                }
                
                report.addComp(comp);
            } 
            report.autoSize();
            return report;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private PreparedStatement selectStatement() 
    throws Exception {
        PreparedStatement stmt
            = conn.prepareStatement("""                                    
                SELECT 
                      Format([begin_date],"mm/dd ddd") AS [Date_Start]
                    , Format([begin_date],"yyyy") AS [Date_Start_Year]
                    , Format([begin_date], "mmmm") AS [Date_Start_Month_Name]
                    , Format([end_date],"mm/dd ddd") AS [Date_End]
                    , competitions.is_attendance_planned AS Planned
                    , competitions.id AS ID
                    , competitions.is_registered AS Registered
                    , gyms.gym_website AS Gym
                    , competitions.league AS League
                    , competitions.type AS Type
                    , gyms.gym_address_google_map AS Location
                    , [gym_drive_hours] & "h " & [gym_drive_minutes] & "m" AS Drive
                    , hotels.hotel_address_google_map AS Hotel
                FROM 
                    (gyms INNER JOIN competitions ON gyms.gym_name = competitions.gym_name) 
                    LEFT JOIN 
                    hotels ON gyms.gym_name = hotels.gym_name
                WHERE 
                    (((gyms.gym_drive_hours)<=8))
                ORDER BY 
                    competitions.begin_date, gyms.gym_drive_hours, gyms.gym_drive_minutes, competitions.gym_name;
            """);
        return stmt;
    }
}

