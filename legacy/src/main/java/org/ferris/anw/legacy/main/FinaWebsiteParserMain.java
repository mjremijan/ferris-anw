/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.ferris.anw.legacy.main;

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

    private static Path filePath = Paths.get("D:\\Development\\projects\\ferris-anw\\fina-events-from-website.txt");
    private static final int year = 2024;
    
    public static void main(String[] args) throws Exception {
        // Define the path to the file
        

        // Read all lines from the file into a list
        List<String> lines = Files.readAllLines(filePath)
            .stream()
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
            String name, date, type_league, location;
            String begin, end;
            String type, league;

            public Comp(String[] data) {
                date = data[0];
                name = Gyms.find(data[1]);
                type_league = data[2];
                location = (data.length >= 5) ? data[4] : "UNKNOWN_LOCATION";
                setTypeLeague();
                setBeginEnd();
            }
            private void setTypeLeague() {
                // FINA Season VI Ninja vs. Ninja #1
                // FINA Season VI Qualifier #1 - Endurance
                // FINA Season VI SECTIONAL - Endurance
                // FINA Season VI Qualifier #1 - Speed - YA, Amat., Masters
                               
                // Split
                String[] split = type_league.split("-");
                // FINA Season VI Ninja vs. Ninja #1
                // FINA Season VI Qualifier #1
                // FINA Season VI SECTIONAL
                String one = null;
                { 
                    one = split[0].trim();
                }
                // Endurance
                // Speed
                String two = null;
                {
                    if (split.length >= 2) {
                        two = split[1].trim();
                    }
                }
                
                // FINA Season VI
                league = "";
                {
                    String [] s = one.split(" ");
                    league = s[0] + " " + s[1] + " " + s[2];
                }
                
                
                type = "";
                {
                    // Ninja vs. Ninja #1
                    // Qualifier #1
                    // SECTIONAL
                    String [] s = one.split(" ");
                    for (int i =3; i<s.length; i++) {
                        if (!type.isEmpty()) { type += " "; }
                        type += s[i];
                    }
                    league = s[0] + " " + s[1] + " " + s[2];
                    
                    // Endurance
                    // Speed
                    if (two != null) {
                        if (two.startsWith("End.")) {
                            two = "Endurance";
                        }
                        type += " - " + two.trim();
                    }
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
                Pattern p = Pattern.compile("\\b(?!\\d{4})\\d{1,2}\\b");
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
                else
                if (numbers.size() == 2 && numbers.get(0) > numbers.get(1)) {
                    begin = month + "/" + numbers.get(0) + "/" + year;
                    end   = (month + 1) + "/" + numbers.get(1) + "/" + year;
                }
                else {
                    return;
                }
            }
            
            @Override
            public String toString() {
                StringBuilder sp = new StringBuilder();
                sp.append(String.format("[Comp%n"));
                  sp.append(String.format("  name=\"%s\"%n", name));
                  sp.append(String.format("  type_league=\"%s\"%n", type_league));
                  sp.append(String.format("  type=\"%s\"%n", type));
                  sp.append(String.format("  league=\"%s\"%n", league));
                  sp.append(String.format("  location=\"%s\"%n", location));
                  sp.append(String.format("  date=\"%s\"%n", date));
                  sp.append(String.format("  begin=\"%s\"%n", begin));
                  sp.append(String.format("  end=\"%s\"%n", end));
                sp.append(String.format("]%n"));
                return sp.toString();
            }
            public String toStringForAccessImport() {
                String b = begin;
                String e = end;
                if ( b == null && e == null) {
                    b = e = "??" + date;
                } 
                else
                if (e == null) {
                    e = "";
                }
                return String.format("%s\t%s\t%s\t%s\t%s", name,b,e,type,league);
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
        List<String> missing
            = comps.stream()
                .map(c -> c.name)
                .distinct()
                .filter(s -> Gyms.contains(s) == false)
                .collect(Collectors.toList())
        ;
        if (missing.isEmpty() == false) {
            missing.forEach(s -> System.out.printf("%s%n", s ) );
            return;
        } else {
            System.out.printf("None missing!!%n");
        }
        
        
        System.out.printf("%n%n-- FOR ACCESS IMPORT ---------------------------------%n");
        System.out.printf("%s\t%s\t%s\t%s\t%s\t%s%n","gym_name","begin_date","end_date","type","league","is_attendance_planned,is_registered");
        comps.forEach(c -> System.out.printf("%s%n", c.toStringForAccessImport()));
        System.out.printf("%n%n Copy what's above into the \"competitions-to-import.xlsx file.\"%n%n");
    }
}
