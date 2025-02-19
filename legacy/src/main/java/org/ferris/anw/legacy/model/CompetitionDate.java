package org.ferris.anw.legacy.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Michael
 */
public class CompetitionDate {
    
    private static final DateTimeFormatter formatter
        = DateTimeFormatter.ofPattern("M/d/yyyy");

    private Date begin;
    private Date end;
    
    private CompetitionDate(){}

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public String toString() {
        
        return "CompetitionDate{" + "begin=" + formatter.format(begin.toLocalDate()) + ", end=" + (end == null ? "null" : formatter.format(end.toLocalDate())) + '}';
    }
    
    
    
    /**
     * Parse based off the expected patterns
     * "April 6" or "April 11-10"
     * 
     * @param token 
     * @return 
     */
    public static CompetitionDate parse(String token) {
        // Parse the token, determine the competition month        
        int competitionMonth = parseCompetitionMonth(token);
        
        // Using the competition month and todays month and year,
        // determine the competition year for that competition month
        int competitionYear = parseCompetitionYear(competitionMonth);
        
        // Parse the token, looking for competition dates. There
        // should be at least 1 but maybe 2. There should never be
        // zero or more than 2.
        List<Integer> competitionDates = parseCompetitionDates(token);
        
        
        // Create the object to store the start/end dates.
        CompetitionDate retval = new CompetitionDate();
        
        // There should always be at least 1 date which is the start date.
        retval.begin = Date.valueOf(
            LocalDate.parse(
                  competitionMonth + "/" + competitionDates.get(0) + "/" + competitionYear
                , formatter
            )
        );

        
        // If there is a second date, and the value of the first date is smaller
        // than the value of the second date, assume they are in the same month
        // like May 5-8
        if (competitionDates.size() == 2 && (competitionDates.get(0) < competitionDates.get(1)))
        {
            retval.end = Date.valueOf(
                LocalDate.parse(
                      competitionMonth + "/" + competitionDates.get(1) + "/" + competitionYear
                    , formatter
                )
            );
        } 
        // If there is a second date, and the value of the first date is bigger
        // than the value of the second date, assume the value of the second date 
        // actually spans into the next month, like January 31-5 or 
        // January 31 - Feb 5
        else
        if (competitionDates.size() == 2 && competitionDates.get(0) > competitionDates.get(1)) {
            retval.end = Date.valueOf(
                LocalDate.parse(
                      competitionMonth + "/" + competitionDates.get(1) + "/" + competitionYear
                    , formatter
                ).plusMonths(1)
            );
        } 
        
        return retval;
    }
    

    /**
     * Parse one or more competition dates returning an list
     * of integers with values possibly between [1-31] inclusive.
     * 
     * @param token
     * @return 
     */
    private static List<Integer> parseCompetitionDates(String token)
    {
        // This regex pattern will capture just the numbers
        // May 8     // 8 will be captured
        // May 8-10  // 8 and 10 will be captured
        Pattern p = Pattern.compile("\\b(?!\\d{4})\\d{1,2}\\b");
        Matcher m = p.matcher(token);
        List<Integer> numbers = new LinkedList<>();
        while (m.find()) {
            numbers.add(Integer.valueOf(m.group()));
        }
        
        if (numbers.isEmpty()) {
            throw new RuntimeException(String.format("Cannot determine competitionDates from token \"%s\"", token));
        }
        else
        if (numbers.size() > 2) {
            throw new RuntimeException(String.format("Too many competitionDates were determined from token \"%s\"", token));
        }
        
        return numbers;
    }
    
    /**
     * Determine the year of the competion based of the month
     * of the competition and the month/year value for today
     * 
     * @param competitionMonth
     * @return 
     */
    private static int parseCompetitionYear(int competitionMonth) 
    {
        // What is TODAY's date?
        LocalDate today = LocalDate.now();
        
        // Given TODAYS date, what year did the currently active ANW season start?
        int seasonStartYear = switch (today.getMonth().getValue()) 
        {
            // If TODAY is [Jan - Aug] then the current ANW season started __last__ year
            case 1,2,3,4,5,6,7,8 -> today.getYear() - 1;
                
            // if TODAY is [Sep - Dec] the the current ANW season started __this__ year
            case 9,10,11,12 -> today.getYear();
                
            // This should never happen  
            default -> -1;
        };
        if (seasonStartYear == -1) {
            throw new RuntimeException(String.format("Cannot determine seasonStartYear from today's month \"%d\"", today.getMonth().getValue()));
        }
        
        
        // Given the month of the ANW competition, what is it's year
        // Based on the year that the currently active ANW season started?
        int competitionYear = switch(competitionMonth) 
        {
            // If the competition month is [Sep - Dec] then these are in the season start year
            case 9,10,11,12 -> seasonStartYear;
                
            // If the competition month is [Jan - Aug] then these are in +1 to the season start year
            case 1,2,3,4,5,6,7,8 -> seasonStartYear + 1;
            
            // This should never happen 
            default -> -1;
        };
        
        if (competitionYear == -1) {
            throw new RuntimeException(String.format("Cannot determine year for anwMonth=\"%d\"", competitionMonth));
        }
        
        return competitionYear;
    }
    
    /**
     * return month as an integer value [1-12] inclusive
     * 
     * @param token
     * @return 
     */
    private static int parseCompetitionMonth(String token) 
    {
        int competitionMonth = switch (token.toLowerCase().substring(0, 3)) 
        {
            case "sep" -> 9;
            case "oct" -> 10;
            case "nov" -> 11;
            case "dec" -> 12;

            case "jan" -> 1;
            case "feb" -> 2;
            case "mar" -> 3;
            case "apr" -> 4;
            case "may" -> 5;
            case "jun" -> 6;
            case "jul" -> 7;
            case "aug" -> 8;

            default -> -1;
        };
        if (competitionMonth == -1) {
            throw new RuntimeException(String.format("Cannot determine competitionMonth for \"%s\"", token));
        }
        return competitionMonth;
    }
}
