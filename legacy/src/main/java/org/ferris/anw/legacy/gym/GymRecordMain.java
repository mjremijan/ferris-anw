package org.ferris.anw.legacy.gym;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.ferris.anw.legacy.sql.ConnectionToRepository;

/**
 *
 * @author Michael
 */
public class GymRecordMain {

    public static void main(String[] args) throws Exception {
        new GymRecordMain().go();
    }
    
    public void go() throws Exception 
    {
        banner("Welcome to GymRecord Main"); 
        
        banner("GymRecord Processing");
        
        // Create the parser
        GymRecordParser parser 
            = new GymRecordParser();
        
        // Parse the data, getting the usable data
        List<GymRecord> gyms 
            = parser.parse();        
        System.out.printf("%d gyms loaded total%n", gyms.size());
        
        // Create the repository
        GymRepository repo 
            = new GymRepository(new ConnectionToRepository());
         
        // Loop and save the gym data
        AtomicInteger cnt = new AtomicInteger();
        gyms.stream().forEach(g -> cnt.addAndGet(repo.insert(g)));
        System.out.printf("%d gyms inserted total%n", cnt.get());
    
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
