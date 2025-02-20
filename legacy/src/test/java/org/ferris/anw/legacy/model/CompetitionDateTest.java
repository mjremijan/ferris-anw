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
January 3 (Kids) - 4th (Adults)
November 23rd 7U - 11U / November 24th 13U & above
November 9 (Youth) & Nov 10 (15U/ Adults)
Sept 21(kids) Sept 22 (Adults
*/
    
    
/*
April 5 - 6th (Up to 17-year-olds)
February 8 - 9th (Up to 17-year-olds)
January 11 - 12th (Up to 17-year-olds)
March 29 - 30th (Up to 17-year-olds)
*/

    
/*
February 16th (Up to 17-year-olds)
January 25th 7U - 11U, January 26th 13U & above
March 15th (13 & Under)
March 29th (Up to 17-year-olds)
May 17th (LCQ)
June 6th (LCQ)
June 7th (LCQ)
*/


/*
April 5 - 6th
December 6 - 7th
February 15 - 16th
February 22 - 23rd
February 8 - 9th
January 10 - 11th
January 18 - 19th
January 24 - 25th
January 3 - 4th
March 15 - 16th
March 29 - 30th
November 1 - 2nd
November 2 - 3rd

*/
    
    @Test
    public void unaa009() 
    {
        // "November 9 - 10th";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 9 - 10th", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-09", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-10", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 9 - 10th", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-09", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-10", i), d.getEnd());
        }
    }

    @Test
    public void unaa008() 
    {
        // "October 12-13th";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 12-13th", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-12", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-13", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 12-13th", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-12", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-13", i), d.getEnd());
        }
    }
    
    @Test
    public void unaa007() 
    {
        // "October 19 - 20th";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 19 - 20th", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-19", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-20", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 19 - 20th", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-19", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-20", i), d.getEnd());
        }
    }

    @Test
    public void unaa006() 
    {
        // "March 1st";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 1st", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-01", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 1st", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-01", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    @Test
    public void unaa005() 
    {
        // "March 22nd";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 22nd", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-22", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 22nd", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-22", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }

    @Test
    public void unaa004() 
    {
        // "November 23rd";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 23rd", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-23", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 23rd", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-23", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    @Test
    public void unaa003() 
    {
        // "November 2nd";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 2nd", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-02", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 2nd", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-02", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    @Test
    public void unaa002() 
    {
        // "October 27th";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 27th", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-27", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 27th", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-27", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    @Test
    public void unaa001() 
    {
        // "October 5th";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 5th", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-05", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 5th", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-05", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    
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
