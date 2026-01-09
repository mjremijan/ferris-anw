package org.ferris.anw.legacy.seasonplanner;

import java.io.FileOutputStream;

/**
 *
 * @author Michael
 */
public class SeasonPlannerMain {

    public static void main(String[] args) throws Exception {
        SeasonPlannerReport report = new SeasonPlannerReport();
        report.addCom(new SeasonPlannerComp());
        report.autoSize();
        report.write(new FileOutputStream("report.xlsx"));
    }

}
