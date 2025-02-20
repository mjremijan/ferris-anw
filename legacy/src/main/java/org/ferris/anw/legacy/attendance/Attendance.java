package org.ferris.anw.legacy.attendance;

/**
 *
 * @author Michael
 */
public class Attendance {
    protected Long competitionId;
    protected String planned;
    protected String registered;

    public Attendance(Long competitionId, String planned, String registered) {
        this.competitionId = competitionId;
        this.planned = planned;
        this.registered = registered;
    }

    public Long getId() {
        return competitionId;
    }

    public String getPlanned() {
        return planned;
    }

    public String getRegistered() {
        return registered;
    }

    @Override
    public String toString() {
        return "Attendance{" + "competitionId=" + competitionId + ", planned=" + planned + ", registered=" + registered + '}';
    }
}
