
# ANW Website Parser

This project is used in combination with the UNAA and FINA websites.
UNAA and FINA post competition schedules on their websites. When then do, we 
need to go through them and determine which ones we will attend. Because the
websites are updated with new competitions throughout the season, trying to
keep track of which ones we've seen and haven't is a big job.

My solution is to keep a Microsoft Access database of all the ANW data so that
it is easier to manager. See 'Database' below for details. The Access database
has queries for each month of the season, showing all the competitons for all
the leagues that are within a reasonable driving distance.

The code in this project helps parse the website data. See 'UNAA' and 'FINA'
below for details on getting the data from the website into the Access database. 

# UNAA

Vist the schedule website:

(https://www.ultimateninja.net/schedule)

Select the table of qualifiers. **NOTE** it may be necessary to select the 
table in different chuncks because I found I wasn't able to select the entire
table at once.

Paste into the Excel document: `unaa-events-from-website.xlsx` This is only 
tempoarary and the Excel document will not be directly used. Go back to the
website and copy/paste as many times as needed to get all the competitions.

Paste into the TXT document: `unaa-events-from-website.txt`. When pasted,
the rows will be tab-delimited. This is OK.

Open `UnaaWebsiteParserMain.java`. Update these class properties as needed:

```java
private static final String path = "D:\\Documents\\Databases\\ANW\\anw-website-parser\\unaa-events-from-website.txt";
private static final int year = 2024; // For the 2024-2025 seaons, this value is 2024
private static final String type = "Area Qualifier";
private static final String league = "UNAA Season 10";
```

Run `UnaaWebsiteParserMain.java`.

Look for any missing gyms...

```text
-- MISSING ---------------------------------
Hit Squad Ninja Warriors	Arizona
AirBenders	Oklahoma
```

If there are any, they need to be resolved. To resolve, see the 
**Reslove Missing Gym** heading. Do this untile there are none missing...

```text
-- MISSING ---------------------------------
None missing!!
```

The data to be imported is output. Select all the output including the 1st
line with the column headers.

```text
gym_name	begin_date	end_date	type	leauge
Hit Squad Ninjas	9/21/2024	9/22/2024	Area Qualifier	UNAA Season 10
The Ninja Gym	10/5/2024		Area Qualifier	UNAA Season 10
Sumner Ninja	10/5/2024		Area Qualifier	UNAA Season 10
Big Time Ninja	10/12/2024		Area Qualifier	UNAA Season 10
.
.
.
```


Open Excel document: `competitions-to-import.xlsx`

Delete everything already in there

Paste into the Excel document: `competitions-to-import.xlsx`

Verify all of the `begin_date` and `end_date` values. Some dates may not
have been parsed and will need manual correction.

Open the ANW Access database.

**First Time Importing**

If you have not imported this data before, then just run an import of the
`competitions-to-import.xlsx` data into the `competitions` table.

**Nth Time Importing**

If this is the Nth time importing this data, you have a bit more work to do.

Close all database objects

Open the `competitions` table

**SORT** by `is_attendance_planned` 

Manually add a 'TRUE' to `competitions-to-import.xlsx` spreadsheet

**DELETE** all of the league's data from the `competitions` table. NOTE: Make 
sure you have a backup.

Run an import of the
`competitions-to-import.xlsx` data into the `competitions` table.

# FINA

(https://fina.ninja/events/)

# Resolve Missing Gym

1. Open `Gyms.java`. This file as a list of all the gym names that are in the database
1. Search `Gyms.java` for a potential match for a missing gym
1. If a match is found: 
    1. Edit the `Gyms.find("")` method. 
    1. Add an `if-else` statement to handle the translation between the website data and what's in the database.
1. If a match is **NOT** found, more work needs to be done:
    1. Open the Access database
    1. Open the `gyms` table
    1. Gather all of the information about the gym and add it to the table.
        - Hyperlink value is: `My Display Text#https://the/url#`
    1. Edit `Gyms.java`
    1. Add the gym name to the list of names in the database.



# Database

