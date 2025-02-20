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
    
    @Test
    public void unaa022() 
    {
        // "January 3 (Kids) - 4th (Adults)";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%1$s 3 (Kids) - 4th (Adults)", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-03", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-04", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%1$s 3 (Kids) - 4th (Adults)", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-03", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-04", i), d.getEnd());
        }
    }
    
    
    @Test
    public void unaa021() 
    {
        // "November 23rd 7U - 11U / November 24th 13U & above";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%1$s 23rd 7U - 11U / %1$s 24th 13U & above", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-23", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-24", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%1$s 23rd 7U - 11U / %1$s 24th 13U & above", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-23", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-24", i), d.getEnd());
        }
    }
    
    
    @Test
    public void unaa020() 
    {
        // "November 9 (Youth) & Nov 10 (15U/ Adults)";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%1$s 9 (Youth) & %1$s 10 (15U/ Adults)", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-09", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-10", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%1$s 9 (Youth) & %1$s 10 (15U/ Adults)", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-09", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-10", i), d.getEnd());
        }
    }
    
    
    @Test
    public void unaa019() 
    {
        // "Sept 21(kids) Sept 22 (Adults";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%1$s 21(kids) %1$s 22 (Adults", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-21", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-22", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%1$s 21(kids) %1$s 22 (Adults", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-21", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-22", i), d.getEnd());
        }
    }
    
    @Test
    public void unaa018() 
    {
        // "March 9 - 23rd (Up to 17-year-olds)";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%1$s 9 - 23rd (Up to 17-year-olds)", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-09", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-23", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%1$s 9 - 23rd (Up to 17-year-olds)", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-09", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-23", i), d.getEnd());
        }
    }
    
    @Test
    public void unaa017() 
    {
        // "March 1 - 2nd (Up to 17-year-olds)";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%1$s 1 - 2nd (Up to 17-year-olds)", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-01", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-02", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%1$s 1 - 2nd (Up to 17-year-olds)", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-01", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-02", i), d.getEnd());
        }
    }
    
    @Test
    public void unaa016() 
    {
        // "March 18 - 19th (Up to 17-year-olds)";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%1$s 18 - 19th (Up to 17-year-olds)", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-18", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-19", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%1$s 18 - 19th (Up to 17-year-olds)", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-18", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-19", i), d.getEnd());
        }
    }
    
    
    @Test
    public void unaa015() 
    {
        // "January 25th 7U - 11U, January 26th 13U & above";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%1$s 25th 7U - 11U, %1$s 26th 13U & above", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-25", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-26", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%1$s 25th 7U - 11U, %1$s 26th 13U & above", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-25", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-26", i), d.getEnd());
        }
    }
    
    @Test
    public void unaa014() 
    {
        // "March 15th (13 & Under)";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 15th (13 & Under)", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-15", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 15th (13 & Under)", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-15", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    
    @Test
    public void unaa013() 
    {
        // "March 16th (Up to 17-year-olds)";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 16th (Up to 17-year-olds)", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-16", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 16th (Up to 17-year-olds)", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-16", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    @Test
    public void unaa012() 
    {
        // "May 17th (LCQ)";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 17th (LCQ)", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-17", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 17th (LCQ)", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-17", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    @Test
    public void unaa011() 
    {
        // "June 7th (LCQ)";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 7th (LCQ)", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-07", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 7th (LCQ)", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-07", i), d.getBegin());
            Assertions.assertNull(d.getEnd());
        }
    }
    
    
    @Test
    public void unaa010() 
    {
        // "November 2 - 3rd";
        
        // Sep - Dec
        for (int i=10; i<=12; i++) {
            CompetitionDate d = parse("%s 2 - 3rd", m1.get(i));
            Assertions.assertEquals(firstHalf("%d-%02d-02", i), d.getBegin());
            Assertions.assertEquals(firstHalf("%d-%02d-03", i), d.getEnd());
        }
        
        // Jan - Aug
        for (int i=1; i<=8; i++) {
            CompetitionDate d = parse("%s 2 - 3rd", m2.get(i));
            Assertions.assertEquals(secondHalf("%d-%02d-02", i), d.getBegin());
            Assertions.assertEquals(secondHalf("%d-%02d-03", i), d.getEnd());
        }
    }
    
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
