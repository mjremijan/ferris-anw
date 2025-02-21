package org.ferris.anw.legacy.competition;

import java.util.Objects;

/**
 *
 * @author Michael
 */
public class CompetitionType {
 
    protected String league, type;

    public CompetitionType(String league, String type) {
        this.league = league;
        this.type = type;
    }

    public String getLeague() {
        return league;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.league);
        hash = 89 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompetitionType other = (CompetitionType) obj;
        if (!Objects.equals(this.league, other.league)) {
            return false;
        }
        return Objects.equals(this.type, other.type);
    }

    @Override
    public String toString() {
        return "CompetitionType{" + "league=" + league + ", type=" + type + '}';
    }
}
