package org.ferris.anw.legacy.time;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Michael
 */
public class ISOLocalDateFormatter {

    public static String now() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String format(Date d) {
        return d.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
