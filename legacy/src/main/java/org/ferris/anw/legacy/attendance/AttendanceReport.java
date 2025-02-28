package org.ferris.anw.legacy.attendance;

import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Michael
 */
public class AttendanceReport {

    public static void main(String[] args) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            // Hello world
            {
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                cell.setCellValue("Hello, Excel!");
            }
            
            // Bold text
            {
                // Create a bold font
                Font boldFont = workbook.createFont();
                boldFont.setBold(true);

                // Create a cell style and set the bold font
                CellStyle boldStyle = workbook.createCellStyle();
                boldStyle.setFont(boldFont);

                Row row = sheet.createRow(1);
                Cell cell = row.createCell(0);
                cell.setCellValue("Bold");
                cell.setCellStyle(boldStyle);
            }
            
            // Background color
            {
                // Create a cell style
                CellStyle fillStyle = workbook.createCellStyle();
                // Set the background color
                fillStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex()); // Change to desired color
                fillStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Required for the color to show

                Row row = sheet.createRow(2);
                Cell cell = row.createCell(0);
                cell.setCellValue("Background yellow");
                cell.setCellStyle(fillStyle);
            }
            
            // Write
            try (FileOutputStream fos = new FileOutputStream("example.xlsx")) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
