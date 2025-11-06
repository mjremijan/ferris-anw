package org.ferris.anw.legacy.gym;

import java.util.List;
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
            = new GymRecordParser(new GymRepository(new ConnectionToRepository()));
        
//        // Create the Repository
//        CompetitionRepository competitionRepository 
//            = new CompetitionRepository(new ConnectionToRepository());
        
        // Parse the data, getting the usable data
        List<GymRecord> gyms 
            = parser.parse();
        
        System.out.printf("%d Gyms  total%n", gyms.size());
        
//        // Find competitions that are ready to load
//        List<Competition> competitionsReadyForLoading = competitions.stream()
//            .filter(c -> c.isGymFoundInTheDatabase())
//            .collect(Collectors.toList())
//        ;
//        System.out.printf("%d FINA competitions ready to load%n", competitionsReadyForLoading.size());
//        
//        
//        // Find competitions that have gyms missing from the database
//        List<Competition> competitionsWithGymsMissingInTheDatabase = competitions.stream()
//            .filter(c -> c.isGymNotFoundInTheDatabase())
//            .collect(Collectors.toList())
//        ;
//        System.out.printf("%d FINA competitions missing gym data%n", competitionsWithGymsMissingInTheDatabase.size());
//        
//        
//        banner("FINA Competitions Loading...");
//        competitionRepository.load(competitionsReadyForLoading);
//        
//        banner("FINA Competitions Vacuuming...");
//        int deleted = competitionRepository.vacuum(competitions.stream()
//            .map(c -> c.getCompetitionType())
//            .distinct()
//            .collect(Collectors.toList())
//        );
//        System.out.printf("%d competitions have been vacuumed.%n", deleted);
//        
//        
//        banner("FINA Competitions missing gym database data");
//        if (competitionsWithGymsMissingInTheDatabase.isEmpty()) {
//            System.out.printf("NONE%n");
//        } else {
//            competitionsWithGymsMissingInTheDatabase.stream()
//                .forEach(c -> System.out.printf("%s%n", c)
//            );
//        }
        
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
