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
public class FinaWebsiteParserMain {

    private static final int year = 2024;
    
    public static void main(String[] args) throws Exception {
        // Define the path to the file
        Path filePath = Paths.get("D:\\Documents\\Databases\\ANW\\fina-events-from-website.txt");

        // Read all lines from the file into a list
        List<String> lines = Files.readAllLines(filePath);

        // Print each line
        System.out.printf("%n%n-- LINES ---------------------------------%n");
        for (String line : lines) {
            System.out.println(line);
        }
        
        // split by tab
        class Comp {     
            int year;
            String name, date, type_league, location;
            String begin, end;
            String type, league;

            public Comp(String[] data) {
                date = data[0];
                
                name = data[1];
                {
                    if (name.equals("Hitsquad Ninja Gym")) {
                        name = "Hit Squad Ninjas";
                    }
                    else
                    if (name.equals("Move Sport Ninja")) {
                        name = "Move Sport Ninja Academy";
                    }
                    else
                    if (name.equals("Ninja Obstacle Academy Huntsville")) {
                        name = "Ninja Obstacle Academy";
                    }
                    else
                    if (name.equals("Ultimate Ninjas Anaheim Hills")) {
                        name = "Ultimate Ninjas- Anaheim Hills";
                    }
                    else
                    if (name.equals("Ninja Core Training (Lost Island Warrior)")) {
                        name = "Lost Island";
                    }
                }
                type_league = data[2];
                location = (data.length >= 4) ? data[3] : "UNKNOWN_LOCATION";
                setTypeLeague();
                setBeginEnd();
            }
            private void setTypeLeague() {
                // FINA Season VI Qualifier #1 - Endurance
                // FINA Season VI SECTIONAL - Endurance                                
                String tokens[] = type_league.split(" ");
                
                // FINA Season VI
                league = tokens[0] + " " + tokens[1] + " " + tokens[2];
                
                // Qualifier #1 - Endurance
                // SECTIONAL - Endurance
                type = "";
                for (int i=3; i<tokens.length; i++) {
                    if (!type.isEmpty()) { type += " "; }
                    type += tokens[i];
                }                
            }
            private void setBeginEnd() {
                                              
                //
                // MONTH
                //
                int month = -1;                
                switch (date.toLowerCase().substring(0, 3)) {
                    case "sep" -> {month = 9; year = FinaWebsiteParserMain.year;}                    
                    case "oct" -> {month = 10; year = FinaWebsiteParserMain.year;}
                    case "nov" -> {month = 11; year = FinaWebsiteParserMain.year;}
                    case "dec" -> {month = 12; year = FinaWebsiteParserMain.year;}
                    
                    case "jan" -> {month = 1; year = FinaWebsiteParserMain.year+1;}                    
                    case "feb" -> {month = 2; year = FinaWebsiteParserMain.year+1;}
                    case "mar" -> {month = 3; year = FinaWebsiteParserMain.year+1;}
                    case "apr" -> {month = 4; year = FinaWebsiteParserMain.year+1;}
                    case "may" -> {month = 5; year = FinaWebsiteParserMain.year+1;}
                    case "jun" -> {month = 6; year = FinaWebsiteParserMain.year+1;}
                    case "jul" -> {month = 7; year = FinaWebsiteParserMain.year+1;}
                    case "aug" -> {month = 8; year = FinaWebsiteParserMain.year+1;}
                    
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
                if (numbers.size() == 2 && (numbers.get(0) == numbers.get(1) - 1)) {
                    begin = month + "/" + numbers.get(0) + "/" + year;
                    end   = month + "/" + numbers.get(1) + "/" + year;
                }
                else {
                    return;
                }
            }
            
            @Override
            public String toString() {
                return String.format("[Comp%n   n=\"%s\"%n   tl=\"%s\"%n   l=\"\"%n   d=\"%s\"%n   b=\"%s\"%n   e=\"%s\"%n   t=\"%s\"%n   l=\"%s\"%n]",name,type_league,location,date,begin,end,type,league);
            }
//            public String toStringForAccessImport() {
//                String b = begin;
//                String e = end;
//                if ( b == null && e == null) {
//                    b = e = "??" + date;
//                } else
//                if (e == null) {
//                    e = "";
//                }
//                return String.format("%s\t%s\t%s\t%s\t%s",name,b,e,type,league);
//            }
            
        }
        
        
        System.out.printf("%n%n-- OBJECTS ---------------------------------%n");
        List<Comp> comps
            = lines.stream()
                .map(l -> new Comp(l.split("\\t"))) 
                .filter(c -> c.date.equalsIgnoreCase("TBD") == false)
                .collect(Collectors.toList())
            ;
        comps.forEach(c -> System.out.printf("%s%n", c));
        
        
        List<Comp> missing
            = comps.stream()
                .filter(c -> Gyms.contains(c.name) == false)
                .collect(Collectors.toList())
        ;
        if (missing.isEmpty() == false) {
            System.out.printf("%n%n-- MISSING ---------------------------------%n");
            missing.forEach(c -> System.out.printf("%s\t%s%n", c.name, c.location ) );
            return;
        }
        
//        System.out.printf("%n%n-- FOR ACCESS IMPORT ---------------------------------%n");
//        System.out.printf("%s\t%s\t%s\t%s\t%s%n","gym_name","begin_date","end_date","type","leauge");
//        comps.forEach(c -> System.out.printf("%s%n", c.toStringForAccessImport()));
//        System.out.printf("%n%n Copy what's above into the \"competitions-to-import.xlsx file.\"%n%n");
    }
}
