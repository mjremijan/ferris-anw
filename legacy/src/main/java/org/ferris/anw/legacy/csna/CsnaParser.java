package org.ferris.anw.legacy.csna;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.ferris.anw.legacy.competition.Competition;
import org.ferris.anw.legacy.competition.CompetitionDate;
import org.ferris.anw.legacy.competition.CompetitionType;
import org.ferris.anw.legacy.gym.Gym;
import org.ferris.anw.legacy.gym.GymRepository;

/**
 *
 * @author Michael
 */
public class CsnaParser {
    
    private GymRepository gymRepository;
            
    public CsnaParser(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }
    
    public Path getFilePath() {
        return Paths.get("./import/csna/csna-all.txt");
    }
    
    public List<Competition> parse()
    {
        try {
            // Read all lines from the file into a list
            List<String> rawData 
                = Files.readAllLines(getFilePath());
            
            // Parse the data, filter only data that's usable.
            List<Competition> competitions = rawData.stream()
                .map(l -> parseLine(l))
                .filter(o -> o.isPresent())
                .map(o -> o.get())
                .collect(Collectors.toList())
            ;
        
            // Return
            return competitions;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private Optional<Competition> parseLine(String line) {
        if (line.isEmpty() || line.isBlank()) {
            return Optional.empty();
        }

        if (line.startsWith("#")) {
            return Optional.empty();
        }

        // Tokenize
        String[] tokens = line.split("\\t");

        // League
        String league = "CSNA";

        // Gym name & Gym ID
        Gym gym = parseGym(tokens[1]);

        // Type
        String type = tokens[2];

        // Begin date & End date
        CompetitionDate compDate = parseCompetitionDateRequired(tokens[0]);

        return Optional.of(
            new Competition(
                gym
              , compDate
              , new CompetitionType(league, type)
        ));
    }
    
    private CompetitionDate parseCompetitionDateRequired(String token) {
        return CompetitionDate.parse(token);
    }
    

    private Gym parseGym(String token) {
        return gymRepository.findGymByNameOrAlias(parseGymNameRequired(token));
    }
    
    private String parseGymNameRequired(String token) {
        if (token.isBlank() || token.isEmpty()) {
            throw new RuntimeException(String.format("Gym name missing in token \"%s\"", token));
        }
        return token;
    }
}
