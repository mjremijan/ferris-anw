package org.ferris.anw.legacy.attendance;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Michael
 */
public class AttendanceParser {
 
    public Path getFilePath() {
        return Paths.get("./import/attendance/attendance.txt");
    }
    
    public List<Attendance> parse()
    {
        try {
            // Read all lines from the file into a list
            List<String> rawData 
                = Files.readAllLines(getFilePath());
            
            // Parse the data, filter only data that's usable.
            List<Attendance> attendances = rawData.stream()
                .map(l -> parseLine(l))
                .filter(o -> o.isPresent())
                .map(o -> o.get())
                .collect(Collectors.toList())
            ;
        
            // Return
            return attendances;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private Optional<Attendance> parseLine(String line) {
        if (line.isEmpty() || line.isBlank()) {
            return Optional.empty();
        }

        if (line.startsWith("#")) {
            return Optional.empty();
        }

        // Tokenize
        String[] tokens = line.split("\\t", -1);

        // ID
        Long id = Long.valueOf(tokens[0]);
        
        // Planned
        String planned = tokens[1];
        
        // Registered
        String registered = tokens[2];
        
        return Optional.of(
            new Attendance(
                id
              , planned
              , registered
        ));
    }
}
