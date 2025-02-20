package org.ferris.anw.legacy.unaa.main;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.ferris.anw.legacy.main.GymRepository;

/**
 *
 * @author Michael
 */
public class UnaaAreaParser extends UnaaParser {

//    private static final String type = "Regional Qualifier";
//    private static final String type = "WNA Games";
    
    public UnaaAreaParser(GymRepository gymRepository) {
        super(gymRepository);
    }
    
    @Override
    public Path getFilePath() {
        return Paths.get("../unaa-area-events-from-website.txt");
    }
    
    @Override
    public String parseTypeRequired() {
        return "Area Qualifier";
    }
       
//    private Optional<Competition> parseLine(String line)
//    {
//        
//        // split by tab
//        class Comp {     
//
//            public Comp(String[] data) {
//                date = data[2];
//                setBeginEnd();
//            }
//            private void setBeginEnd() {
//                
//                if (date.equalsIgnoreCase("TBD")) {
//                    return;
//                }
//                
//                if (date.equalsIgnoreCase("March 1, 4, 6th")) {
//                    date = "March 1st - 6th";
//                }
//                else
//                if (date.equalsIgnoreCase("November 9 (Youth) & Nov 10 (15U/ Adults)")) {
//                    date = "November 9th";
//                }
//                else
//                if (date.equalsIgnoreCase("November 23rd 7U - 11U / November 24th 13U & above")) {
//                    date = "November 23rd";
//                }
//                else
//                if (date.equalsIgnoreCase("January 11 - 12th (Up to 17-year-olds)")) {
//                    date = "January 11th";
//                }
//                else
//                if (date.equalsIgnoreCase("January 25th 7U - 11U, January 26th 13U & above")) {
//                    date = "January 25th";
//                }
//                else
//                if (date.equalsIgnoreCase("February 8 - 9th (Up to 17-year-olds)")) {
//                    date = "February 8th";
//                }
//                else
//                if (date.equalsIgnoreCase("March 15th (13 & Under)")) {
//                    date = "March 15th";
//                }
//                else
//                if (date.equalsIgnoreCase("March 29th (Up to 17-year-olds)")) {
//                    date = "March 29th";
//                }
//                else
//                if (date.equalsIgnoreCase("March 29 - 30th (Up to 17-year-olds)")) {
//                    date = "March 29th";
//                }
//                else
//                if (date.equalsIgnoreCase("April 5 - 6th (Up to 17-year-olds)")) {
//                    date = "April 5th";
//                }
//                else
//                if (date.equalsIgnoreCase("January 3 (Kids) - 4th (Adults)")) {
//                    date = "January 3rd";
//                }
//                else
//                if (date.equalsIgnoreCase("February 16th (Up to 17-year-olds)")) {
//                    date = "February 16th";
//                }
//                else
//                if (date.equalsIgnoreCase("March 22 (Kids) - 23rd (Adults)")) {
//                    date = "March 22";
//                }
//                else
//                if (date.equalsIgnoreCase("April 26 (Kids) - 27th (Adults)")) {
//                    date = "April 26";
//                }
//                else
//                if (date.equalsIgnoreCase("May 17 - 18th (13U and adult divisions)")) {
//                    date = "May 17";
//                }
//                
//                // Typically formatted as:
//                // March 15th
//                // March 1 - 9th
//                // March 1st - 9th
//                // March 7-9
//                
//                //
//                // MONTH
//                //
//                int month = -1;                
//                switch (date.toLowerCase().substring(0, 3)) {
//                    case "sep" -> {month = 9; year = UnaaAreaParser.year;}                    
//                    case "oct" -> {month = 10; year = UnaaAreaParser.year;}
//                    case "nov" -> {month = 11; year = UnaaAreaParser.year;}
//                    case "dec" -> {month = 12; year = UnaaAreaParser.year;}
//                    
//                    case "jan" -> {month = 1; year = UnaaAreaParser.year+1;}                    
//                    case "feb" -> {month = 2; year = UnaaAreaParser.year+1;}
//                    case "mar" -> {month = 3; year = UnaaAreaParser.year+1;}
//                    case "apr" -> {month = 4; year = UnaaAreaParser.year+1;}
//                    case "may" -> {month = 5; year = UnaaAreaParser.year+1;}
//                    case "jun" -> {month = 6; year = UnaaAreaParser.year+1;}
//                    case "jul" -> {month = 7; year = UnaaAreaParser.year+1;}
//                    case "aug" -> {month = 8; year = UnaaAreaParser.year+1;}
//                    
//                    default -> month = -1;
//                }
//                if (month == -1) {
//                    return;
//                }
//                
//                // 
//                // DATE
//                //                
//                Pattern p = Pattern.compile("(\\d{1,2})");
//                Matcher m = p.matcher(date);
//                List<Integer> numbers = new LinkedList<>();
//                while (m.find()) {
//                    numbers.add(Integer.parseInt(m.group()));
//                }
//                if (numbers.isEmpty()) {
//                    return;
//                } 
//                else 
//                if (numbers.size() == 1) {
//                    begin = month + "/" + numbers.getFirst() + "/" + year;
//                }
//                else
//                 if (numbers.size() == 2) {
//                    begin = month + "/" + numbers.get(0) + "/" + year;
//                    end   = month + "/" + numbers.get(1) + "/" + year;
//                }
//                else {
//                    return;
//                }
//            }
//        }
//        
//    }
}
