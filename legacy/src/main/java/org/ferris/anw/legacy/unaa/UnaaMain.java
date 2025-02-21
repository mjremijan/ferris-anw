package org.ferris.anw.legacy.unaa;

import java.util.List;
import java.util.stream.Collectors;
import org.ferris.anw.legacy.competition.Competition;
import org.ferris.anw.legacy.competition.CompetitionRepository;
import org.ferris.anw.legacy.competition.CompetitionType;
import org.ferris.anw.legacy.gym.GymRepository;
import org.ferris.anw.legacy.sql.ConnectionToRepository;

/**
 *
 * @author Michael
 */
public class UnaaMain {

    public static void main(String[] args) {
        new UnaaMain().go();
    }
    
    protected CompetitionRepository competitionRepository;
    
    public UnaaMain() {
        competitionRepository = new CompetitionRepository(new ConnectionToRepository());
    }
    
    private void go() {
        banner("Welcome to UNAA Main"); 
        area();
        regional();
        wna();
        banner("Done");
    }
    
    
    private void wna() 
    {    
        banner("UNAA WNA Competitions Processing");
        
        // Create the parser
        UnaaWnaParser parser
            = new UnaaWnaParser(new GymRepository(new ConnectionToRepository()));

        // Parse the data, getting the usable data
        List<Competition> competitions
             = parser.parse();
        
        System.out.printf("%d WNA competitions total%n", competitions.size());
        
        // Find competitions that are ready to load
        List<Competition> competitionsReadyForLoading = competitions.stream()
            .filter(c -> c.isGymFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%d WNA competitions ready to load%n", competitionsReadyForLoading.size());
        
        // Find competitions that have gyms missing from the database
        List<Competition> competitionsWithGymsMissingInTheDatabase = competitions.stream()
            .filter(c -> c.isGymNotFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%d WNA competitions missing gym data%n", competitionsWithGymsMissingInTheDatabase.size());
        
        // Load
        load("UNAA WNA Competitions Loading...", competitionsReadyForLoading);
        
        // Vacuum
        vacuum("UNAA WNA Competitions Vacuuming...", competitionsReadyForLoading.stream()
            .map(c -> c.getCompetitionType())
            .distinct()
            .collect(Collectors.toList())
        );
        
        // Print
        print("UNAA WNS Competitions missing gym database data", competitionsWithGymsMissingInTheDatabase);
    }
    
    
    private void regional() 
    {    
        banner("UNAA Regional Competitions Processing");
        
        // Create the parser
        UnaaRegionalParser parser
            = new UnaaRegionalParser(new GymRepository(new ConnectionToRepository()));

        // Parse the data, getting the usable data
        List<Competition> competitions
             = parser.parse();
        
        System.out.printf("%d regional competitions total%n", competitions.size());
        
        // Find competitions that are ready to load
        List<Competition> competitionsReadyForLoading = competitions.stream()
            .filter(c -> c.isGymFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%d regional competitions ready to load%n", competitionsReadyForLoading.size());
        
        // Find competitions that have gyms missing from the database
        List<Competition> competitionsWithGymsMissingInTheDatabase = competitions.stream()
            .filter(c -> c.isGymNotFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%d regional competitions missing gym data%n", competitionsWithGymsMissingInTheDatabase.size());
        
        // Load
        load("UNAA Regional Competitions Loading...", competitionsReadyForLoading);
        
        // Vacuum
        vacuum("UNAA Regional Competitions Vacuuming...", competitionsReadyForLoading.stream()
            .map(c -> c.getCompetitionType())
            .distinct()
            .collect(Collectors.toList())
        );
        
        // Print
        print("UNAA Regional Competitions missing gym database data", competitionsWithGymsMissingInTheDatabase);
    }
    
    
    private void area() 
    {    
        banner("UNAA Area Competitions Processing");
        
        // Create the parser
        UnaaAreaParser parser
            = new UnaaAreaParser(new GymRepository(new ConnectionToRepository()));

        // Parse the data, getting the usable data
        List<Competition> competitions
             = parser.parse();
        
        System.out.printf("%d area competitions total%n", competitions.size());
        
        // Find competitions that are ready to load
        List<Competition> competitionsReadyForLoading = competitions.stream()
            .filter(c -> c.isGymFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%d area competitions ready to load%n", competitionsReadyForLoading.size());
        
        // Find competitions that have gyms missing from the database
        List<Competition> competitionsWithGymsMissingInTheDatabase = competitions.stream()
            .filter(c -> c.isGymNotFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%d area competitions missing gym data%n", competitionsWithGymsMissingInTheDatabase.size());
        
        // Load
        load("UNAA Area Competitions Loading...", competitionsReadyForLoading);
        
        // Vacuum
        vacuum("UNAA Area Competitions Vacuuming...", competitionsReadyForLoading.stream()
            .map(c -> c.getCompetitionType())
            .distinct()
            .collect(Collectors.toList())
        );
        
        // Print
        print("UNAA Area Competitions missing gym database data", competitionsWithGymsMissingInTheDatabase);
                
    }
    
    private void print(String msg, List<Competition> competitions) {
        banner(msg);
        if (competitions.isEmpty()) {
            System.out.printf("NONE%n");
        } else {
            competitions.stream()
                .forEach(c -> System.out.printf("%s%n", c)
            );
        }
    }
    
    private void load(String msg, List<Competition> competitions) {
        banner(msg);
        competitionRepository.load(competitions);
    }
    
    private void vacuum(String msg, List<CompetitionType> competitionTypes) {
        banner(msg);
        int deleted = competitionRepository.vacuum(competitionTypes);
        System.out.printf("%d competitions have been vacuumed.%n", deleted);
    }
    
    private void banner(String m) {
        System.out.printf("%n%n");
        int l = 6 + m.length();
        System.out.print(String.format("%-"+l+"s%n", "").replace(' ', '#'));
        System.out.printf("## %s ##%n", m);
        System.out.print(String.format("%-"+l+"s%n", "").replace(' ', '#'));
    }
}
