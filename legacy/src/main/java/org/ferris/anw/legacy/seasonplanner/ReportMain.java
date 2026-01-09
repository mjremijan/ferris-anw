package org.ferris.anw.legacy.seasonplanner;

import java.io.FileOutputStream;

/**
 *
 * @author Michael
 */
public class ReportMain {

    public static void main(String[] args) throws Exception {
        Report report = new Report();
        report.addCom(new Comp());
        report.autoSize();
        report.write(new FileOutputStream("report.xlsx"));
    }

}
