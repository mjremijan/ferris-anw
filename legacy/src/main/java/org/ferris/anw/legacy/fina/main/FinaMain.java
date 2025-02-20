package org.ferris.anw.legacy.fina.main;

import java.util.List;
import java.util.stream.Collectors;
import org.ferris.anw.legacy.competition.CompetitionRepository;
import org.ferris.anw.legacy.main.GymRepository;
import org.ferris.anw.legacy.model.Competition;
import org.ferris.anw.legacy.sql.ConnectionToRepository;

/**
 *
 * @author Michael
 */
public class FinaMain {

    public static void main(String[] args) throws Exception {
        new FinaMain().go();
    }
    
    public void go() throws Exception 
    {
        banner("Welcome to FINA Main"); 
        
        banner("FINA Competitions Processing");
        // Create the parser
        FinaParser parser 
            = new FinaParser(new GymRepository(new ConnectionToRepository()));
        
        // Parse the data, getting the usable data
        List<Competition> competitions 
            = parser.parse();
        
        System.out.printf("%d FINA competitions total%n", competitions.size());
        
        // Find competitions that are ready to load
        List<Competition> competitionsReadyForLoading = competitions.stream()
            .filter(c -> c.isGymFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%d FINA competitions ready to load%n", competitionsReadyForLoading.size());
        
        
        // Find competitions that have gyms missing from the database
        List<Competition> competitionsWithGymsMissingInTheDatabase = competitions.stream()
            .filter(c -> c.isGymNotFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%d FINA competitions missing gym data%n", competitionsWithGymsMissingInTheDatabase.size());
        
        
        banner("FINA Competitions Loading...");
        CompetitionRepository compRepo = new CompetitionRepository(new ConnectionToRepository());
        compRepo.load(competitionsReadyForLoading);
        
        
        banner("FINA Competitions missing gym database data");
        if (competitionsWithGymsMissingInTheDatabase.isEmpty()) {
            System.out.printf("NONE%n");
        } else {
            competitionsWithGymsMissingInTheDatabase.stream()
                .forEach(c -> System.out.printf("%s%n", c)
            );
        }
        
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
