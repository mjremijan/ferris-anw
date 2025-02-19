package org.ferris.anw.legacy.fina.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import org.ferris.anw.legacy.model.Competition;
import org.ferris.anw.legacy.model.CompetitionDate;
import org.ferris.anw.legacy.model.Gym;
import org.ferris.anw.legacy.sql.ConnectionToAnwDb;

/**
 *
 * @author Michael
 */
public class FinaParser {
    
    private ConnectionToAnwDb conn;

    public FinaParser(ConnectionToAnwDb conn) {
        this.conn = conn;
    }

    public Optional<Competition> parseLine(String line) {
        try {
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
                  , league.get()
                  , type
            ));
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("ERROR parsing line of data \"%s\"%n", line)
            );
        }
    }
    
    private CompetitionDate parseCompetitionDateRequired(String token) throws Exception {
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
    
    
    private Gym findGymByNameOrAlias(String nameOrAlias) {
        try (
            PreparedStatement stmt = findGymByNameOrAliasStatement(nameOrAlias);
            ResultSet rs = stmt.executeQuery();
        ){
            if (rs.next()) {
                return new Gym(
                      rs.getLong("id")
                    , rs.getString("gym_name")
                );
            } else {
                return new Gym(nameOrAlias);
            }
        } catch (Exception e) {
            throw new RuntimeException("ERROR finding gym by either name or alias", e);
        }
    }
    private PreparedStatement findGymByNameOrAliasStatement(String name) throws Exception {
        PreparedStatement stmt = conn.get().prepareStatement("""
            SELECT 
                g.id        AS id, 
                g.gym_name  AS gym_name, 
                a.gym_alias AS gym_alias
            FROM 
                gyms g
            LEFT JOIN 
                gym_aliases a 
            ON 
                g.id = a.gym_id
            where
                g.gym_name = ? OR a.gym_alias = ?
        """);
        stmt.setString(1, name);
        stmt.setString(2, name);
        return stmt;
    }
    

    private Gym parseGym(String token) {
        return findGymByNameOrAlias(parseGymNameRequired(token));
    }
    
    private String parseGymNameRequired(String token) {
        if (token.isBlank() || token.isEmpty()) {
            throw new RuntimeException(String.format("Gym name missing in token \"%s\"", token));
        }
        return token;
    }

}
