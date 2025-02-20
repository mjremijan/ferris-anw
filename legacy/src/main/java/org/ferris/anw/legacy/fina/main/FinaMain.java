/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
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
        // Create the parser
        FinaParser parser 
            = new FinaParser(new GymRepository(new ConnectionToRepository()));
        
        // Parse the data, getting the usable data
        List<Competition> competitions 
            = parser.parse();
        
        // Find compettions that are ready to load
        List<Competition> competitionsReadyForLoading = competitions.stream()
            .filter(c -> c.isGymFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%n%n#################################################%n");
        System.out.printf("## competitionsReadyForLoading                ##%n");
        System.out.printf("#################################################%n");
        CompetitionRepository compRepo = new CompetitionRepository(new ConnectionToRepository());
        compRepo.load(competitionsReadyForLoading);
        
        // Find competitions that have gyms missing from the database
        List<Competition> competitionsWithGymsMissingInTheDatabase = competitions.stream()
            .filter(c -> c.isGymNotFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%n%n#################################################%n");
        System.out.printf("## competitionsWithGymsMissingInTheDatabase    ##%n");
        System.out.printf("#################################################%n");
        competitionsWithGymsMissingInTheDatabase.stream()
            .forEach(c -> System.out.printf("%s%n", c)
        );
    }
}
