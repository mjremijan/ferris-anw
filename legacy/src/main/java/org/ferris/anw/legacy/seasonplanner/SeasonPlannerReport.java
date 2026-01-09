package org.ferris.anw.legacy.seasonplanner;

import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Michael
 */
public class SeasonPlannerReport {

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
            
            // Merge cells
            {
                // Create a row and cell
                Row row = sheet.createRow(3);
                Cell cell = row.createCell(1);
                cell.setCellValue("Merged Cells Cols B,C,D");

                // Merge cels from row 3 column 1 to row 3 column 3.
                sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 3));
            }
            
            // Height of row
            {
                // Create a row and cell
                Row row = sheet.createRow(4);

                // The default row height in Excel is 12.75 points
                row.setHeightInPoints(12.75f * 2.0f);

                // Create cell
                Cell cell = row.createCell(0);
                cell.setCellValue("Row with double height");
                // Create a cell style and set vertical alignment
                CellStyle style = workbook.createCellStyle();
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                cell.setCellStyle(style);
            }
            
            // Auto-size the column based on content
            sheet.autoSizeColumn(0);

            // Write
            try (FileOutputStream fos = new FileOutputStream("example.xlsx")) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
