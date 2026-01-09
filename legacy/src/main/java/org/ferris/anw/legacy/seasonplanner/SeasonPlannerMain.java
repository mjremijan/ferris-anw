package org.ferris.anw.legacy.seasonplanner;

import java.io.FileOutputStream;
import org.ferris.anw.legacy.sql.ConnectionToRepository;

/**
 *
 * @author Michael
 */
public class SeasonPlannerMain {

    public static void main(String[] args) throws Exception {
        new SeasonPlannerMain().go();
    }
    
    public void go() throws Exception
    {
        banner("Welcome to SeasonPlanner Main");
        
        banner("Building Report...");
        SeasonPlannerReport report
            = new SeasonPlannerRepository(new ConnectionToRepository()).getReport();
        
        banner("Saving Report...");
        report.write(new FileOutputStream("D:\\Desktop\\ANW Season Planner.xlsx"));
        
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
