
package org.ferris.anw.legacy.competition;

import java.time.format.DateTimeFormatter;
import org.ferris.anw.legacy.gym.Gym;

/**
 *
 * @author Michael
 */
public class Competition {
    
    protected Gym gym;
    protected CompetitionDate competitionDate;
    protected CompetitionType competitionType;

    private static DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("M/d/yyyy");

    public Competition(Gym gym, CompetitionDate competitionDate, CompetitionType competitionType) {
        this.gym = gym;
        this.competitionDate = competitionDate;
        this.competitionType = competitionType;
    }

    public Gym getGym() {
        return gym;
    }

    public CompetitionDate getCompetitionDate() {
        return competitionDate;
    }

    public String getLeague() {
        return competitionType.getLeague();
    }

    public String getType() {
        return competitionType.getType();
    }
    
    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public boolean isGymNotFoundInTheDatabase() {
        return gym.getId().isEmpty();
    }
    
    public boolean isGymFoundInTheDatabase() {
        return gym.getId().isPresent();
    }

    @Override
    public String toString() {
        return "Competition{" + "gym=" + gym + ", competitionDate=" + competitionDate + ", competitionType=" + competitionType + '}';
    }

    
    
}