package org.ferris.anw.legacy.gym;

import java.util.Optional;


/**
 *
 * @author Michael
 */
public class GymRecord {
    private String name, website, address, city, state, zip, country;
    Optional<Integer> driveHours;
    int driveMinutes;

    public GymRecord(String name, String website, String address, String city, String state, String zip, String country, Optional<Integer> driveTimeHours) {
        this.name = name;
        this.website = website;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        driveHours = driveTimeHours;
        driveMinutes = 0;
    }

    public void setDriveTime(int hours, int minutes) {
        this.driveHours = Optional.of(hours);
        this.driveMinutes = minutes;
    }
    
    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public int getDriveHours() {
        return driveHours.get();
    }

    public int getDriveMinutes() {
        return driveMinutes;
    }
    
    public boolean isDriveTimeSet() {
        return driveHours.isPresent();
    }

    public String getFullAddress() {
        //3000 Waketon Rd, Flower Mound, TX 75028        
        return
              getAddress()
            + ", "
            + getCity()
            + ", "
            + getState()
            + " "
            + getZip()
            + ((getCountry().isEmpty()) ? "" : ", " + getCountry() )
        ;
    }
}
    