package org.ferris.anw.legacy.seasonplanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
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
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd EEE");
            DateTimeFormatter sdfYear = DateTimeFormatter.ofPattern("yyyy");
            DateTimeFormatter sdfMonth = DateTimeFormatter.ofPattern("MMMM");
            
            // Build report
            SeasonPlannerReport report = new SeasonPlannerReport();
            while (rs.next()) 
            {
                SeasonPlannerComp comp = new SeasonPlannerComp();
                // Date start
                // Date start year
                // Date start month name
                {
                    LocalDate ldate = LocalDate.parse(rs.getString("begin_date"));
                    comp.dateStart = sdf.format(ldate);
                    comp.year = Integer.parseInt(sdfYear.format(ldate));
                    comp.month = sdfMonth.format(ldate);
                }                
                // Date end
                {
                    String s = rs.getString("end_date");
                    if (rs.wasNull()) {
                        comp.dateEnd = "";
                    } else {
                        comp.dateEnd = sdf.format(LocalDate.parse(s));
                    }
                }
                // #ID
                {
                    comp.id = rs.getInt("id");
                }
                // Planned
                {
                    comp.planned = rs.getString("is_attendance_planned");
                }
                // Registered
                {
                    comp.registered = rs.getString("is_registered");
                }
                // League
                {
                    comp.league = rs.getString("league");
                }
                // Type
                {
                    comp.type = rs.getString("type");
                }
                // Gym
                {
                    String s;
                    s = rs.getString("gym_name");
                    if (rs.wasNull()) {
                        comp.gymName = "";
                    } else {
                        comp.gymName = s;
                    }
                    s = rs.getString("gym_website");
                    if (rs.wasNull()) {
                        comp.gymLink = "";
                    } else {
                        comp.gymLink = s;
                    }
                }
                // Drive
                {
                    comp.drive = String.format("%dh %dm", rs.getInt("gym_drive_hours"), rs.getInt("gym_drive_minutes"));
                }
                // Location
                {
                    String city = Objects.toString(rs.getString("gym_city"), "").trim();
                    String state = Objects.toString(rs.getString("gym_state"), "").trim();
                    String country = Objects.toString(rs.getString("gym_country"), "").trim();
                    
                    if (!city.isEmpty() && !state.isEmpty()) {
                        comp.cityState = city + ", " + state;
                    } 
                    else
                    if (!city.isEmpty()) {
                        comp.cityState = city;
                    }
                    else
                    if (!state.isEmpty()) {
                        comp.cityState = state;
                    }
                    else
                    if (!country.isEmpty()) {
                        comp.cityState = country;
                    }
                    else {
                        comp.cityState = "unknown";
                    }
                    
                    comp.cityStateLink
                        = Objects.toString(rs.getString("gym_address_google_map"), "").trim();
                    
                }
                // Hotel
                {
                    String s;
                    s = rs.getString("hotel_name");
                    if (rs.wasNull()) {
                        comp.hotelName = "";
                    } else {
                        comp.hotelName = s;
                    }
                    s = rs.getString("hotel_address_google_map");
                    if (rs.wasNull()) {
                        comp.hotelLink = "";
                    } else {
                        comp.hotelLink = s;
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
                      competitions.begin_date
                    , competitions.end_date
                    , competitions.is_attendance_planned
                    , competitions.id
                    , competitions.is_registered
                    , competitions.league
                    , competitions.type
                    , gyms.gym_name
                    , gyms.gym_website
                    , gyms.gym_address_google_map
                    , gyms.gym_drive_hours
                    , gyms.gym_drive_minutes
                    , gyms.gym_city
                    , gyms.gym_state
                    , gyms.gym_country
                    , hotels.hotel_name
                    , hotels.hotel_address_google_map
                FROM 
                    gyms INNER JOIN competitions ON gyms.gym_name = competitions.gym_name
                    LEFT JOIN hotels ON gyms.gym_name = hotels.gym_name
                WHERE 
                    gyms.gym_drive_hours <=8
                ORDER BY 
                    competitions.begin_date, gyms.gym_drive_hours, gyms.gym_drive_minutes, competitions.gym_name;
            """);
        return stmt;
    }
}

