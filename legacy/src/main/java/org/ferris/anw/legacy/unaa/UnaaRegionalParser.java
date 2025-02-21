package org.ferris.anw.legacy.unaa;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.ferris.anw.legacy.gym.GymRepository;

/**
 *
 * @author Michael
 */
public class UnaaRegionalParser extends UnaaParser {

//    private static final String type = "WNA Games";
    
    public UnaaRegionalParser(GymRepository gymRepository) {
        super(gymRepository);
    }
    
    @Override
    public Path getFilePath() {
        return Paths.get("./import/unaa/regional-competitions.txt");
    }
    
    @Override
    public String parseTypeRequired() {
        return "Regional Qualifier";
    }
}
