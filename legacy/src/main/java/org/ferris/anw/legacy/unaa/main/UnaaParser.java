package org.ferris.anw.legacy.unaa.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.ferris.anw.legacy.main.GymRepository;
import org.ferris.anw.legacy.model.Competition;
import org.ferris.anw.legacy.model.CompetitionDate;
import org.ferris.anw.legacy.model.Gym;

/**
 *
 * @author Michael
 */
public abstract class UnaaParser {
 
    private GymRepository gymRepository;
    
    public UnaaParser(GymRepository gymRepository) {
        this.gymRepository = gymRepository;  
    }
    
    /**
     * The string to return is something like "UNAA Season 10".
     * "UNAA Season" will alwyas be the same, but the season
     * number will change. The 2024-2025 years was season 10.
     * Given this as a starting point, calculate what the
     * season number will be for the current season start year.
     * 
     * @return String, formatted as "UNAA Season %d" where the
     * season number for %d is calculated based of the current
     * season's start year.
     */
    public String parseLeagueRequired() {
        // Get this current season's start year
        int seasonStartYear = CompetitionDate.getSeasonStartYear();
        
        // 2024-2025 years were season 10
        // Determine the number of years since 2024
        int delta = seasonStartYear - 2024;
        
        // Calculate current season number by adding the delta
        // to 10
        return "UNAA Season " + (10 + delta);
    }
    
    /**
     * Subclasses must implement;
     */
    abstract String parseTypeRequired();
    
    /**
     * Subclasses must implement
     */
    abstract Path getFilePath();
    
    /**
     * Parse the file
     */
    public void parse()
    {
        try {
            // Read all lines from the file into a list
            List<String> rawData 
                = Files.readAllLines(getFilePath());
            
            // Parse each line of data, filter to only usable data
            rawData.stream()
                .map(l -> parseLine(l))
                .filter(o -> o.isPresent())
                .map(o -> o.get())
                .collect(Collectors.toList())
            ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }   
    
    
    private Optional<Competition> parseLine(String line)
    {
        if (line.isEmpty() || line.isBlank()) {
            return Optional.empty();
        }

        if (line.startsWith("#")) {
            return Optional.empty();
        }

        // Tokenize
        String[] tokens = line.split("\\t");
        
        // League
        String league = parseLeagueRequired();
        
        // Type
        String type = parseTypeRequired(); 
        
        // Gym name & Gym ID
        Gym gym = parseGym(tokens[0]);
        
        return null;
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
