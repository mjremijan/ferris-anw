package org.ferris.anw.legacy.fina;

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
public class FinaParser {
    
    private GymRepository gymRepository;
            
    public FinaParser(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }
    
    public Path getFilePath() {
        return Paths.get("./import/fina/all-competitions.txt");
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
        Optional<String> league = parseLeagueRequired(tokens[2]);
        if (league.isEmpty()) {
            return Optional.empty();
        }

        // Gym name & Gym ID
        Gym gym = parseGym(tokens[1]);

        // Type
        String type = parseTypeRequired(league.get(), tokens[2]);

        // Begin date & End date
        CompetitionDate compDate = parseCompetitionDateRequired(tokens[0]);

        return Optional.of(
            new Competition(
                gym
              , compDate
              , new CompetitionType(league.get(), type)
        ));
    }
    
    private CompetitionDate parseCompetitionDateRequired(String token) {
        return CompetitionDate.parse(token);
    }
    

    private String parseTypeRequired(String league, String data) {
        //
        // Below are some possible options for the format
        // of the data. The goal is to return the value
        // "FINA Season VI" as the name of the league.
        //
        // FINA Season VI Qualifier #1 - Speed
        // FINA Season VI Qualifier #1 - Endurance
        // FINA Season VI Ninja vs. Ninja #1
        // FINA Season VI Qualifier #1 - Speed - YA, Amat., Masters
        // FINA Season VI Qualifier #1 - Endurance - YA, Amat., Masters
        // FINA Season VI Qualifier #1 - Speed - 7U, 9U, 11U, !3U, TF
        // FINA Season VI Qualifier #1 - End. - 7U, 9U, 11U, 13U, TF
        // FINA Season VI Qualifier #2 - Endurance
        // FINA Season VI Qualifier #2 - Speed
        // FINA Season VI Ninja vs. Ninja - Youth
        // FINA Season VI Ninja vs. Ninja - Adult
        // FINA Season VI Qualifier #1 =- Speed
        // FINA Season VI Qualifier - Speed #2
        // FINA Season VI Qualifier #3 - Endurance(13U, YA, Am, Ma, TF)
        // FINA Season VI Qualifier #2 - Speed (13U, YA, Am., Mas., TF)
        // FINA Season VI Qualifier #3 - Endurance (7U, 9U, 11U)
        // FINA Season VI Qualifier #2 - Speed (7U, 9U, 11U)
        // FINA Season VI Qualifier # 1 - Endurance
        // FINA Season VI Qualifier #3 - Endurance
        // FINA Season VI Qualifier #3 - Speed
        // FINA Season VI SECTIONAL - Speed
        // FINA Season VI SECTIONAL - Endurance
        // FINA Season VI SECTIONAL - Speed Youth (7U-13U)
        // FINA Season VI SECTIONAL - Endurance Youth (7U-13U)
        // FINA Season VI SECTIONAL - Speed - 7U, 9U, 11U, 13U, TF
        // FINA Season VI SECTIONAL - End. 7U, 9U, 11U, 13U, TF
        // FINA Season VI SECTIONAL - Speed - YA, Amat., Masters
        // FINA Season VI SECTIONAL - End. - YA, Amat., Masters
        
        // Remove league name
        String substr = data.substring(league.length()+1);

        // Check if there is anything left
        if (substr.isBlank() || substr.isEmpty()) {
            throw new RuntimeException(String.format("Cannot determind competition type from data \"%s\"", data));
        }
        
        return substr;
    }
    
    
    private Optional<String> parseLeagueRequired(String data) {
        //
        // Below are some possible options for the format
        // of the data. The goal is to return the value
        // "FINA Season VI" as the name of the league.
        //
        // FINA Season VI Qualifier #1 - Speed
        // FINA Season VI Qualifier #1 - Endurance
        // Ninja Grand Prix Games - Speed   ***IGNORE***
        // FINA Season VI Ninja vs. Ninja #1
        // FINA Season VI Qualifier #1 - Speed - YA, Amat., Masters
        // FINA Season VI Qualifier #1 - Endurance - YA, Amat., Masters
        // FINA Season VI Qualifier #1 - Speed - 7U, 9U, 11U, !3U, TF
        // FINA Season VI Qualifier #1 - End. - 7U, 9U, 11U, 13U, TF
        // FINA Season VI Qualifier #2 - Endurance
        // FINA Season VI Qualifier #2 - Speed
        // Rise Up Ninja Fundraiser - Speed   ***IGNORE***
        // FINA Season VI Ninja vs. Ninja - Youth
        // FINA Season VI Ninja vs. Ninja - Adult
        // FINA Season VI Qualifier #1 =- Speed
        // FINA Season VI Qualifier - Speed #2
        // FINA Season VI Qualifier #3 - Endurance(13U, YA, Am, Ma, TF)
        // FINA Season VI Qualifier #2 - Speed (13U, YA, Am., Mas., TF)
        // FINA Season VI Qualifier #3 - Endurance (7U, 9U, 11U)
        // FINA Season VI Qualifier #2 - Speed (7U, 9U, 11U)
        // FINA Season VI Qualifier # 1 - Endurance
        // FINA Season VI Qualifier #3 - Endurance
        // FINA Season VI Qualifier #3 - Speed
        // FINA Season VI SECTIONAL - Speed
        // FINA Season VI SECTIONAL - Endurance
        // FINA Season VI SECTIONAL - Speed Youth (7U-13U)
        // FINA Season VI SECTIONAL - Endurance Youth (7U-13U)
        // FINA Season VI SECTIONAL - Speed - 7U, 9U, 11U, 13U, TF
        // FINA Season VI SECTIONAL - End. 7U, 9U, 11U, 13U, TF
        // FINA Season VI SECTIONAL - Speed - YA, Amat., Masters
        // FINA Season VI SECTIONAL - End. - YA, Amat., Masters

        Optional<String> league;
        if (data.startsWith("FINA Season")) {
            String[] tokens = data.split(" ");
            league = Optional.of(tokens[0] + " " + tokens[1] + " " + tokens[2]);
        } else {
            league = Optional.empty();
        }
        
        return league;
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
