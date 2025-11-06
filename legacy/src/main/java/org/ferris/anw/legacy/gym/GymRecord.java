package org.ferris.anw.legacy.gym;


/**
 *
 * @author Michael
 */
public class GymRecord {
    private String name, website, address, city, state, zip, country;
    int driveHours, driveMinutes;

    public GymRecord(String name, String website, String address, String city, String state, String zip, String country) {
        this.name = name;
        this.website = website;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    public void setDriveTime(int hours, int minutes) {
        this.driveHours = hours;
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
        return driveHours;
    }

    public int getDriveMinutes() {
        return driveMinutes;
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
    