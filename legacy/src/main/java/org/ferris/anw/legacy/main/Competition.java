
package org.ferris.anw.legacy.main;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author Michael
 */
public class Competition {
    
    protected Long id;
    protected Gym gym;
    protected CompetitionDate competitionDate;
    protected String league;
    protected String type;

    private static DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("M/d/yyyy");

//    public Competition(Gym gym, String beginDate, String endDate, String type, String league, String isAttendancePlanned, String isRegistered) {
//        this.gym = gym;
//        this.beginDate = Date.valueOf(LocalDate.parse(beginDate, formatter));
//        this.endDate = endDate == null ? null : endDate.isEmpty() ? null : endDate.isBlank() ? null : Date.valueOf(LocalDate.parse(endDate, formatter));
//        this.type = type;
//        this.league = league;
//    }

    public Competition(Gym gym, CompetitionDate competitionDate, String league, String type) {
        this.gym = gym;
        this.competitionDate = competitionDate;
        this.league = league;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Gym getGym() {
        return gym;
    }

    public CompetitionDate getCompetitionDate() {
        return competitionDate;
    }

    public String getLeague() {
        return league;
    }

    public String getType() {
        return type;
    }

    public boolean isGymNotFoundInTheDatabase() {
        return gym.getId().isEmpty();
    }
    
    public boolean isGymFoundInTheDatabase() {
        return gym.getId().isPresent();
    }

    @Override
    public String toString() {
        return "Competition{" + "id=" + id + ", gym=" + gym + ", competitionDate=" + competitionDate + ", league=" + league + ", type=" + type + '}';
    }

    
    
}