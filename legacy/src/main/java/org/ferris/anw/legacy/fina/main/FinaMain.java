/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.ferris.anw.legacy.fina.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.ferris.anw.legacy.competition.CompetitionRepository;
import org.ferris.anw.legacy.main.GymRepository;
import org.ferris.anw.legacy.model.Competition;
import org.ferris.anw.legacy.sql.ConnectionForAnw;

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
        // Define the path to the file
        Path filePath 
            = Paths.get("../fina-events-from-website.txt");

        // Read all lines from the file into a list
        List<String> rawData 
            = Files.readAllLines(filePath);

        
        // Parse each line of data, filter to only usable data
        FinaParser parser = new FinaParser(new GymRepository(new ConnectionForAnw()));
        List<Competition> competitions = rawData.stream()
            .map(l -> parser.parseLine(l))
            .filter(o -> o.isPresent())
            .map(o -> o.get())
            .collect(Collectors.toList())
        ;
        
        // Find compettions that are ready to load
        List<Competition> competitionsReadyForLoading = competitions.stream()
            .filter(c -> c.isGymFoundInTheDatabase())
            .collect(Collectors.toList())
        ;
        System.out.printf("%n%n#################################################%n");
        System.out.printf("## competitionsReadyForLoading                ##%n");
        System.out.printf("#################################################%n");
        CompetitionRepository compRepo = new CompetitionRepository(new ConnectionForAnw());
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
