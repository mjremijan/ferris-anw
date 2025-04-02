package org.ferris.anw.legacy.unaa;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.ferris.anw.legacy.gym.GymRepository;

/**
 *
 * @author Michael
 */
public class UnaaWnaParser extends UnaaParser {

    public UnaaWnaParser(GymRepository gymRepository) {
        super(gymRepository);
    }
    
    @Override
    public Path getFilePath() {
        return Paths.get("./import/unaa/unaa-wna-competitions.txt");
    }
    
    @Override
    public String parseTypeRequired() {
        return "WNA Games";
    }
}
