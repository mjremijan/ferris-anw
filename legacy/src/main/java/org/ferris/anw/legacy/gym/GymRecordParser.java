package org.ferris.anw.legacy.gym;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Michael
 */
public class GymRecordParser {
    
    public Path getFilePath() {
        return Paths.get("./import/gyms/gyms.txt");
    }
    
    public List<GymRecord> parse()
    {
        try {
            // Read all lines from the file into a list
            List<String> rawData 
                = Files.readAllLines(getFilePath());
            
            // Parse the data, filter only data that's usable.
            List<GymRecord> gyms = rawData.stream()
                .map(l -> parseLine(l))
                .filter(o -> o.isPresent())
                .map(o -> o.get())
                .collect(Collectors.toList())
            ;
        
            // Return
            return gyms;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private Optional<GymRecord> parseLine(String line) {
        if (line.isEmpty() || line.isBlank()) {
            return Optional.empty();
        }

        if (line.startsWith("#")) {
            return Optional.empty();
        }

        // Tokenize
        String[] tokens = line.split(",");
        
        if (tokens.length == 0) {
            throw new RuntimeException("Unable to tokenize gym record line: \""+line+"\"");
        }
        if (tokens.length > 7) {
            throw new RuntimeException("Gym record line has more than 7 tokens: \""+line+"\"");
        }
        if (tokens.length < 6) {
            throw new RuntimeException("Gym record line has less than 6 tokens: \""+line+"\"");
        }
        
        // Name
        String name = parseName(tokens);
        
        // Website
        String website = parseWebsite(tokens);
        
        // Address
        String address = parseAddress(tokens);
        
        // City
        String city = parseCity(tokens);
        
        // State
        String state = parseState(tokens);
        
        // Zip
        String zip = parseZip(tokens);
        
        // Country
        String country = parseCountry(tokens);
        
        // Create the object
        GymRecord gym = new GymRecord(
            name, website, address, city, state, zip, country
        );
    
        // Set the drive time
        setDriveTime(gym);
        
        // Finally, return
        return Optional.of(gym);
    }
    
    
    protected String parseCountry(String[] tokens) {
        String retval = "";
        if (tokens.length == 7) {
            retval = tokens[6].trim();
        } 
        return retval;
    }
    
    protected String parseZip(String[] tokens) {
        String retval = tokens[5].trim();
        if (retval.isEmpty()) {
            throw new RuntimeException("Gym zip is missing");
        }
        return retval;
    }
    
    protected String parseState(String[] tokens) {
        String retval = tokens[4].trim();
        if (retval.isEmpty()) {
            throw new RuntimeException("Gym state is missing");
        }
        return retval;
    }
    
    protected String parseCity(String[] tokens) {
        String retval = tokens[3].trim();
        if (retval.isEmpty()) {
            throw new RuntimeException("Gym city is missing");
        }
        return retval;
    }
    
    protected String parseAddress(String[] tokens) {
        String retval = tokens[2].trim();
        if (retval.isEmpty()) {
            throw new RuntimeException("Gym address is missing");
        }
        return retval;
    }
    
    protected String parseWebsite(String[] tokens) {
        String retval = tokens[1].trim();
        if (retval.isEmpty()) {
            throw new RuntimeException("Gym website is missing");
        }
        return retval;
    }
    
    protected String parseName(String[] tokens) {
        String retval = tokens[0].trim();
        if (retval.isEmpty()) {
            throw new RuntimeException("Gym name is missing");
        }
        return retval;
    }

    private void setDriveTime(GymRecord gym) {
        
        Map<String, String> params = Map.of(
            "origins", "2270 Birmingham Dr., Shiloh, IL 62221",
            "destinations", gym.getFullAddress(),
            "key", getApiKey()
        );       
        
        // Encode and join parameters into a query string
        String query = getParamsString(params);

        // Construct the full URL with query string
        String url = "https://api.distancematrix.ai/maps/api/distancematrix/json?" + query;

        // Create the HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create the HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .header("Accept", "application/json")
            .build();

        // Send the request and get the response
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Print the response
            // System.out.println("Status code: " + response.statusCode());
            // System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send request to distancematrix.ai", e);
        }
                
        // Response body: {"destination_addresses":["644 N Broadway Ave, Park Ridge, IL 60068, USA"],"origin_addresses":["2270 Birmingham Dr, Belleville, IL 62221, USA"],"rows":[{"elements":[{"distance":{"text":"476.2 km","value":476225},"duration":{"text":"4 hour 34 mins","value":16444},"origin":"2270 Birmingham Dr., Shiloh, IL 62221","destination":"644 N. Broadway, Park Ridge, IL 60068","status":"OK"}]}],"status":"OK"}
        Pattern p = Pattern.compile("(\\d+)\\s*hour\\s+(\\d+)\\s*mins");
        Matcher m = p.matcher(response.body());
        if (m.find()) {
            gym.setDriveTime(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)));
        } else {
            throw new RuntimeException("Failed to get the hour and minute values from the distancematrix.ai response\n"+response.body());
        }        
    }
    
    protected String getApiKey() {
        try {
            return new String(
                this.getClass().getResourceAsStream("/distancematrix.ai/key").readAllBytes()
                , StandardCharsets.UTF_8
            );  
        } catch (Exception e) {
            throw new RuntimeException("Failed to read distancematrix api key from the classpath", e);
        }
    }
    
    private static String getParamsString(Map<String, String> params) {
        return params.entrySet()
            .stream()
            .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                          URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
            .reduce((a, b) -> a + "&" + b)
            .orElse("");
    }
}
