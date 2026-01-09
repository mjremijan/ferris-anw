
package org.ferris.anw.legacy.seasonplanner;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderFormatting;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Michael
 */
public class SeasonPlannerReport {

    protected Workbook workbook;
    protected Sheet sheet;
    protected CreationHelper helper;
    protected int rowIndex;
    
    protected List<String> headers; 
    protected List<Integer> years;
    protected List<String> months; 
    
    protected CellStyle defaultStyle;
    protected CellStyle boldGreyStyle;
    protected CellStyle hyperlinkStyle;
    
    public SeasonPlannerReport() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Sheet1");
        helper = workbook.getCreationHelper();
        
        rowIndex = -1;
        headers = List.of("Date Start", "Date End", "#ID", "Planned", "Registered", "League", "Type", "Gym", "Drive", "Location", "Hotel");
        years = new LinkedList<>();
        months = new LinkedList<>();
        
        defaultStyle();
        boldGreyStyle();
        hyperlinkStyle();
        conditionalFormattingRules();
        addHeader();
    }
    
    protected void defaultStyle() {
        defaultStyle = workbook.createCellStyle();
    }
    
    
    protected void hyperlinkStyle() {
        hyperlinkStyle = workbook.createCellStyle();
        
        Font linkFont = workbook.createFont();
        linkFont.setUnderline(Font.U_SINGLE);
        linkFont.setColor(IndexedColors.BLUE.getIndex());
        hyperlinkStyle.setFont(linkFont);
    }
    
    
    protected void boldGreyStyle() {
        boldGreyStyle = workbook.createCellStyle();
        
        // Bold font
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldGreyStyle.setFont(boldFont);

        // Grey background 191, 191, 191  #BFBFBF
        boldGreyStyle.setFillForegroundColor(new XSSFColor(new byte[] {(byte) 191,(byte) 191,(byte) 191}, null));
        boldGreyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);   
    }
    
    
    protected void conditionalFormattingRules() {
        SheetConditionalFormatting scf 
            = sheet.getSheetConditionalFormatting();

        CellRangeAddress[] regions = {
              CellRangeAddress.valueOf("D4:D500")
            , CellRangeAddress.valueOf("E4:E500")
        };
        
        ConditionalFormattingRule yesRule = scf.createConditionalFormattingRule("D4=\"Yes\"");
        {
            PatternFormatting fill = yesRule.createPatternFormatting();
            fill.setFillBackgroundColor(new XSSFColor(new byte[] {(byte) 146,(byte) 208,(byte) 80}, null));
            fill.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
            BorderFormatting border = yesRule.createBorderFormatting();
            border.setBorderTop(BorderStyle.THIN);
            border.setBorderBottom(BorderStyle.THIN);
            border.setBorderLeft(BorderStyle.THIN);
            border.setBorderRight(BorderStyle.THIN);
        }
        ConditionalFormattingRule noRule = scf.createConditionalFormattingRule("D4=\"No\"");
        {
            PatternFormatting fill = noRule.createPatternFormatting();
            fill.setFillBackgroundColor(new XSSFColor(new byte[] {(byte) 255,(byte) 171,(byte) 171}, null));
            fill.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
            BorderFormatting border = noRule.createBorderFormatting();
            border.setBorderTop(BorderStyle.THIN);
            border.setBorderBottom(BorderStyle.THIN);
            border.setBorderLeft(BorderStyle.THIN);
            border.setBorderRight(BorderStyle.THIN);
        }
        ConditionalFormattingRule maybeRule = scf.createConditionalFormattingRule("D4=\"Maybe\"");
        {
            PatternFormatting fill = maybeRule.createPatternFormatting();
            fill.setFillBackgroundColor(new XSSFColor(new byte[] {(byte) 255,(byte) 255,(byte) 0}, null));
            fill.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
            BorderFormatting border = maybeRule.createBorderFormatting();
            border.setBorderTop(BorderStyle.THIN);
            border.setBorderBottom(BorderStyle.THIN);
            border.setBorderLeft(BorderStyle.THIN);
            border.setBorderRight(BorderStyle.THIN);
        }

        scf.addConditionalFormatting(regions, yesRule);
        scf.addConditionalFormatting(regions, noRule);
        scf.addConditionalFormatting(regions, maybeRule);
    }
    
    
    protected void autoSize() {
        for (int i=0; i<headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    protected void addComp(SeasonPlannerComp comp) {
        addYear(comp.getYear());
        addMonth(comp.getMonth());
        
        Row row = sheet.createRow(nextRowIndex());
        int colIdx = 0;
        // Date start
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getDateStart());
            cell.setCellStyle(defaultStyle);
        }
        // Date end
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getDateEnd());
            cell.setCellStyle(defaultStyle);
        }
        // #ID
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getId());
            cell.setCellStyle(defaultStyle);
        }
        // Planned
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getPlanned());
            cell.setCellStyle(defaultStyle);
        }
        // Registered
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getRegistered());
            cell.setCellStyle(defaultStyle);
        }
        // League
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getLeague());
            cell.setCellStyle(defaultStyle);
        }
        // Type
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getType());
            cell.setCellStyle(defaultStyle);
        }
        // Gym
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getGymName());
            Hyperlink link = helper.createHyperlink(HyperlinkType.URL);
            link.setAddress(comp.getGymLink());
            cell.setHyperlink(link);
            cell.setCellStyle(hyperlinkStyle);
        }
        // Drive
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getDrive());
            cell.setCellStyle(defaultStyle);
        }
        // Location
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getCityState());
            Hyperlink link = helper.createHyperlink(HyperlinkType.URL);
            link.setAddress(comp.getCityStateLink());
            cell.setHyperlink(link);
            cell.setCellStyle(hyperlinkStyle);
        }
        // Hotel
        {
            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(comp.getHotel());
            if (comp.getHotel().isEmpty()) {
                cell.setCellStyle(defaultStyle);
            } else {
                Hyperlink link = helper.createHyperlink(HyperlinkType.URL);
                link.setAddress(comp.getHotelLink());
                cell.setHyperlink(link);
                cell.setCellStyle(hyperlinkStyle);
            }
        }
    }
    
    protected void addMonth(String month) {
        if (months.contains(month)) {
            return;
        }
        months.add(month);
        
        CellStyle copyStyle = workbook.createCellStyle();
        copyStyle.cloneStyleFrom(boldGreyStyle);
        
        Row row = sheet.createRow(nextRowIndex());
        Cell cell = row.createCell(0);
        cell.setCellValue(month);
        cell.setCellStyle(copyStyle);
        // Merge cels from row 3 column 1 to row 3 column 3.
        sheet.addMergedRegion(new CellRangeAddress(getRowIndex(), getRowIndex(), 0, headers.size()-1));
    }
    
    protected void addYear(int year) {
        if (years.contains(year)) {
            return;
        }
        years.add(year);
        
        CellStyle copyStyle = workbook.createCellStyle();
        copyStyle.cloneStyleFrom(boldGreyStyle);
        copyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        
        Row row = sheet.createRow(nextRowIndex());
        // The default row height in Excel is 12.75 points
        row.setHeightInPoints(12.75f * 2.0f);
        Cell cell = row.createCell(0);
        cell.setCellValue(String.valueOf(year));
        cell.setCellStyle(copyStyle);
        // Merge cels from row 3 column 1 to row 3 column 3.
        sheet.addMergedRegion(new CellRangeAddress(getRowIndex(), getRowIndex(), 0, headers.size()-1));
    }
    
    protected void addHeader() {
        Row row = sheet.createRow(nextRowIndex());
        CellStyle copyStyle = workbook.createCellStyle();
        copyStyle.cloneStyleFrom(boldGreyStyle);
        //copyStyle.setBorderTop(BorderStyle.THIN);
        //copyStyle.setBorderBottom(BorderStyle.THIN);
        //copyStyle.setBorderLeft(BorderStyle.THIN);
        copyStyle.setBorderRight(BorderStyle.THIN);
        copyStyle.setAlignment(HorizontalAlignment.CENTER);

        IntStream.range(0, headers.size())
            .forEach(i -> {
                Cell cell = row.createCell(i);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(copyStyle);
            }
        );
    }
    
    protected int getRowIndex() {
        return rowIndex;
    }
    
    protected int nextRowIndex() {
        return ++rowIndex;
    }
    
    protected void write(OutputStream os) throws Exception {
        workbook.write(os);
    }
}
