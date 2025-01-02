/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.ferris.anw.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Michael
 */
public class UnaaWebsiteParserMain {

    private static final String path = 
        "D:\\Development\\projects\\ferris-anw\\unaa-events-from-website.txt";
    private static final int year = 2024; // For the 2024-2025 seasons, this value is 2024
    //private static final String type = "Area Qualifier";
    private static final String type = "WNA Games";
    private static final String league = "UNAA Season 10";
    
    public static void main(String[] args) throws Exception {
        // Define the path to the file
        Path filePath = Paths.get(path);

        // Read all lines from the file into a list
        List<String> lines = Files.readAllLines(filePath)
            .stream()
            .map(l -> l.trim())
            .filter(l -> l.isEmpty() == false)
            .collect(Collectors.toList())
        ;

        // Print each line
        System.out.printf("%n%n-- LINES ---------------------------------%n");
        for (String line : lines) {
            System.out.println(line);
        }
        
        // split by tab
        class Comp {     
            int year;
            String name, state, date;
            String begin, end;

            public Comp(String[] data) {
                name = Gyms.find(data[0]);
                state = data[1];
                System.out.printf("name: %s%n", name);
                date = data[2];
                setBeginEnd();
            }
            @Override
            public String toString() {
                return String.format("[Comp n=\"%s\", s=\"%s\", d=\"%s\", begin=\"%s\", end=\"%s\"]",name,state,date,begin,end);
            }
            public String toStringForAccessImport() {
                String b = begin;
                String e = end;
                if ( b == null && e == null) {
                    b = e = "??" + date;
                } else
                if (e == null) {
                    e = "";
                }
                return String.format("%s\t%s\t%s\t%s\t%s",name,b,e,type,league);
            }
            private void setBeginEnd() {
                
                if (date.equalsIgnoreCase("TBD")) {
                    return;
                }
                
                if (date.equalsIgnoreCase("??November 9 (Youth) & Nov 10 (15U/ Adults)")) {
                    date = "November 9th";
                }
                else
                if (date.equalsIgnoreCase("??November 23rd 7U - 11U / November 24th 13U & above")) {
                    date = "November 23rd";
                }
                else
                if (date.equalsIgnoreCase("??January 11 - 12th (Up to 17-year-olds)")) {
                    date = "January 11th";
                }
                else
                if (date.equalsIgnoreCase("??January 25th 7U - 11U, January 26th 13U & above")) {
                    date = "January 25th";
                }
                else
                if (date.equalsIgnoreCase("??February 8 - 9th (Up to 17-year-olds)")) {
                    date = "February 8th";
                }
                else
                if (date.equalsIgnoreCase("??March 15th (13 & Under)")) {
                    date = "March 15th";
                }
                else
                if (date.equalsIgnoreCase("??March 29th (Up to 17-year-olds)")) {
                    date = "March 29th";
                }
                else
                if (date.equalsIgnoreCase("??March 29 - 30th (Up to 17-year-olds)")) {
                    date = "March 29th";
                }
                else
                if (date.equalsIgnoreCase("??April 5 - 6th (Up to 17-year-olds)")) {
                    date = "April 5th";
                }
                
                // Typically formatted as:
                // March 15th
                // March 1 - 9th
                // March 1st - 9th
                // March 7-9
                
                //
                // MONTH
                //
                int month = -1;                
                switch (date.toLowerCase().substring(0, 3)) {
                    case "sep" -> {month = 9; year = UnaaWebsiteParserMain.year;}                    
                    case "oct" -> {month = 10; year = UnaaWebsiteParserMain.year;}
                    case "nov" -> {month = 11; year = UnaaWebsiteParserMain.year;}
                    case "dec" -> {month = 12; year = UnaaWebsiteParserMain.year;}
                    
                    case "jan" -> {month = 1; year = UnaaWebsiteParserMain.year+1;}                    
                    case "feb" -> {month = 2; year = UnaaWebsiteParserMain.year+1;}
                    case "mar" -> {month = 3; year = UnaaWebsiteParserMain.year+1;}
                    case "apr" -> {month = 4; year = UnaaWebsiteParserMain.year+1;}
                    case "may" -> {month = 5; year = UnaaWebsiteParserMain.year+1;}
                    case "jun" -> {month = 6; year = UnaaWebsiteParserMain.year+1;}
                    case "jul" -> {month = 7; year = UnaaWebsiteParserMain.year+1;}
                    case "aug" -> {month = 8; year = UnaaWebsiteParserMain.year+1;}
                    
                    default -> month = -1;
                }
                if (month == -1) {
                    return;
                }
                
                // 
                // DATE
                //                
                Pattern p = Pattern.compile("(\\d{1,2})");
                Matcher m = p.matcher(date);
                List<Integer> numbers = new LinkedList<>();
                while (m.find()) {
                    numbers.add(Integer.parseInt(m.group()));
                }
                if (numbers.isEmpty()) {
                    return;
                } 
                else 
                if (numbers.size() == 1) {
                    begin = month + "/" + numbers.getFirst() + "/" + year;
                }
                else
                if (numbers.size() == 2) {
                    begin = month + "/" + numbers.get(0) + "/" + year;
                    end   = month + "/" + numbers.get(1) + "/" + year;
                }
                else {
                    return;
                }
            }
        }
        
        
        System.out.printf("%n%n-- OBJECTS ---------------------------------%n");
        List<Comp> comps
            = lines.stream()
                .map(l -> new Comp(l.split("\\t"))) 
                .filter(c -> c.date.equalsIgnoreCase("TBD") == false)
                .collect(Collectors.toList())
            ;
        comps.forEach(c -> System.out.printf("%s%n", c));
        
        
        System.out.printf("%n%n-- MISSING ---------------------------------%n");
        List<Comp> missing
            = comps.stream()
                .filter(c -> Gyms.contains(c.name) == false)
                .collect(Collectors.toList())
        ;
        if (missing.isEmpty() == false) {
            missing.forEach(c -> System.out.printf("%s\t%s%n", c.name, c.state ) );
            return;
        } else {
            System.out.printf("None missing!!%n");
        }
        
        System.out.printf("%n%n-- FOR ACCESS IMPORT ---------------------------------%n");
        System.out.printf("%s\t%s\t%s\t%s\t%s\t%s%n","gym_name","begin_date","end_date","type","league","is_attendance_planned");
        comps.forEach(c -> System.out.printf("%s%n", c.toStringForAccessImport()));
        System.out.printf("%n%n Copy what's above into the \"competitions-to-import.xlsx file.\"%n%n");

    }
}
