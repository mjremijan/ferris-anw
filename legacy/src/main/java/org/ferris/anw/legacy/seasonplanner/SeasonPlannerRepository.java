package org.ferris.anw.legacy.seasonplanner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd EEE");
            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM");
            Pattern urlPattern = Pattern.compile("^(.*?)#(.*?)#");
            
            // Build report
            SeasonPlannerReport report = new SeasonPlannerReport();
            while (rs.next()) 
            {
                SeasonPlannerComp comp = new SeasonPlannerComp();
                // Date start
                {
                    comp.dateStart = sdf.format(rs.getDate("Date_Start"));
                }
                // Date start year
                {
                    comp.year = Integer.parseInt(sdfYear.format(rs.getDate("Date_Start")));
                }
                // Date start month name
                {
                    comp.month = sdfMonth.format(rs.getDate("Date_Start"));
                    
                }                
                // Date end
                {
                    comp.dateEnd = rs.getString("Date_End");
                    Date d = rs.getDate("Date_End");
                    if (rs.wasNull()) {
                        comp.dateEnd = "";
                    } else {
                        comp.dateEnd = sdf.format(rs.getDate("Date_End"));
                    }
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
                    String val = rs.getString("Gym");
                    if (rs.wasNull()) {
                        comp.gymName = "";
                        comp.gymLink = "";
                    } else {
                        Matcher matcher = urlPattern.matcher(val);
                        if (matcher.find()) {
                            comp.gymName = matcher.group(1);
                            comp.gymLink = matcher.group(2);
                        } else {
                            throw new RuntimeException("Error! Do not know what this url pattern is: \""+val+"\" ");
                        }
                    }
                }
                // Drive
                {
                    comp.drive = rs.getString("Drive");
                }
                // Location
                {
                    String val = rs.getString("Location");
                    if (rs.wasNull()) {
                        comp.cityState = "";
                        comp.cityStateLink = "";
                    } else {
                        Matcher matcher = urlPattern.matcher(val);
                        if (matcher.find()) {
                            comp.cityState = matcher.group(1);
                            comp.cityStateLink = matcher.group(2);
                        } else {
                            throw new RuntimeException("Error! Do not know what this url pattern is: \""+val+"\" ");
                        }
                    }
                }
                // Hotel
                {
                    String val = rs.getString("Hotel");
                    if (rs.wasNull()) {
                        comp.hotel = "";
                        comp.hotelLink = "";
                    } else {
                        Matcher matcher = urlPattern.matcher(val);
                        if (matcher.find()) {
                            comp.hotel = matcher.group(1);
                            comp.hotelLink = matcher.group(2);
                        } else {
                            throw new RuntimeException("Error! Do not know what this url pattern is: \""+val+"\" ");
                        }
                    }
                }

                // Add to report
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
                      [begin_date] AS [Date_Start]
                    , [end_date] AS [Date_End]    
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

