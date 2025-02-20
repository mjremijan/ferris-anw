package org.ferris.anw.legacy.attendance;

import java.util.List;
import org.ferris.anw.legacy.sql.ConnectionToRepository;

/**
 *
 * @author Michael
 */
public class AttendanceMain {

    public static void main(String[] args) throws Exception {
        new AttendanceMain().go();
    }
    
    public void go() throws Exception 
    {
        banner("Welcome to Attendance Main"); 
        
        banner("Attendance Processing");
        // Create the repository
        AttendanceRepository attendanceRepository
            = new AttendanceRepository(new ConnectionToRepository());
                
        // Create the parser
        AttendanceParser parser 
            = new AttendanceParser();
        
        // Parse the data, getting the usable data
        List<Attendance> attendances 
            = parser.parse();
        
        System.out.printf("%d attendances total%n", attendances.size());
        System.out.printf("%d attendances ready to load%n", attendances.size());
        
        banner("Attendances Loading...");
        int loaded = attendanceRepository.load(attendances);
        System.out.printf("%d attendances successfully loaded%n", loaded);
        
        banner("Done");
    }
    
    private void banner(String m) {
        System.out.printf("%n%n");
        int l = 6 + m.length();
        System.out.print(String.format("%-"+l+"s%n", "").replace(' ', '#'));
        System.out.printf("## %s ##%n", m);
        System.out.print(String.format("%-"+l+"s%n", "").replace(' ', '#'));
    }    
}
