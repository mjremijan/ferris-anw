package org.ferris.anw.legacy.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michael
 */
public class CompetitionDateTest {

    private List<String> m1
        = Arrays.asList("skip", "", "", "", "", "", "", "", "", "September", "October", "November", "December");
    private List<String> m2
        = Arrays.asList("skip", "January", "February", "March", "April", "May", "June", "July", "August", "", "", "", "");
   
    private CompetitionDate parse(String pattern, String month) {
        return CompetitionDate.parse(String.format(pattern, month));
    }
    private Date firstHalf(String pattern, int month) {
        return Date.valueOf(
            LocalDate.parse(
                String.format(pattern, CompetitionDate.getSeasonStartYear(), month)
            )
        );
    }
    private Date secondHalf(String pattern, int month) {
        return Date.valueOf(
            LocalDate.parse(
                String.format(pattern, CompetitionDate.getSeasonStartYear()+1, month)
            )
        );
    }
    
   /*


*/  
    @Test
    public void fina008() 
    {
        // "May 27 - Jun 1";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 27 - xxx 1", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-27", i), d.getBegin());
            if (i<12) {
                Assertions.assertEquals(firstHalf("%d-%02d-01", i+1), d.getEnd());
            } else {
                Assertions.assertEquals(secondHalf("%d-%02d-01", 1), d.getEnd());
            }
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 27 - xxx 1", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-27", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-01", i+1), d.getEnd());
        }
    }
    
    @Test
    public void fina007() 
    {
        // "May 24-25";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 24-25", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-24", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-25", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 24-25", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-24", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-25", i), d.getEnd());
        }
    }
    
    @Test
    public void fina006() 
    {
        // "May 3-4";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 3-4", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-03", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-04", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 3-4", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-03", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-04", i), d.getEnd());
        }
    }
    
    @Test
    public void fina005() 
    {
        // "May 4";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 4", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-04", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 4", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-04", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    @Test
    public void fina004() 
    {
        // "February 15";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 15", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-15", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 15", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-15", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }

    @Test
    public void fina003() 
    {
        // "November 2-3, 2024";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 2-3, 2024", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-02", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-03", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 2-3, 2024", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-02", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-03", i), d.getEnd());
        }
    }
    
    @Test
    public void fina002() 
    {
        // "November 3, 2024";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 3, 2024", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-03", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 3, 2024", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-03", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    @Test
    public void fina001() 
    {
        // "November 15, 2024";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 15, 2024", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-15", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 15, 2024", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-15", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
}
