package org.ferris.anw.legacy.unaa;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.ferris.anw.legacy.gym.GymRepository;

/**
 *
 * @author Michael
 */
public class UnaaAreaParser extends UnaaParser {

    public UnaaAreaParser(GymRepository gymRepository) {
        super(gymRepository);
    }
    
    @Override
    public Path getFilePath() {
        return Paths.get("./import/unaa/unaa-area-competitions.txt");
    }
    
    @Override
    public String parseTypeRequired() {
        return "Area Qualifier";
    }
}
