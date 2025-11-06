package org.ferris.anw.legacy.csna;

import java.util.List;
import java.util.stream.Collectors;
import org.ferris.anw.legacy.competition.Competition;
import org.ferris.anw.legacy.competition.CompetitionRepository;
import org.ferris.anw.legacy.gym.GymRepository;
import org.ferris.anw.legacy.sql.ConnectionToRepository;

/**
 *
 * @author Michael
 */
public class CsnaMain {

    public static void main(String[] args) throws Exception {
        new CsnaMain().go();
    }
    
    public void go() throws Exception 
    {
        banner("Welcome to CSNA Main"); 
        
        banner("CSNA Competitions Processing");
        // Create the parser
        CsnaParser parser 
            = new CsnaParser(new GymRepository(new ConnectionToRepository()));
        
        // Create the Repository
        CompetitionRepository competitionRepository 
            = new CompetitionRepository(new ConnectionToRepository());
        
        // Parse the data, getting the usable data
        List<Competition> competitions 
            = parser.parse();
        
        System.out.printf("%d CSNA competitions total%n", competitions.size());
        
        // Find competitions that are ready to load
        List<Competition> competitionsReadyForLoading = competitions.stream()
            .filter(c -> c.isGymFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%d CSNA competitions ready to load%n", competitionsReadyForLoading.size());
        
        
        // Find competitions that have gyms missing from the database
        List<Competition> competitionsWithGymsMissingInTheDatabase = competitions.stream()
            .filter(c -> c.isGymNotFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%d CSNA competitions missing gym data%n", competitionsWithGymsMissingInTheDatabase.size());
        
        
        banner("CSNA Competitions Loading...");
        competitionRepository.load(competitionsReadyForLoading);
        
        banner("CSNA Competitions Vacuuming...");
        int deleted = competitionRepository.vacuum(competitions.stream()
            .map(c -> c.getCompetitionType())
            .distinct()
            .collect(Collectors.toList())
        );
        System.out.printf("%d competitions have been vacuumed.%n", deleted);
        
        
        banner("CSNA Competitions missing gym database data");
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
