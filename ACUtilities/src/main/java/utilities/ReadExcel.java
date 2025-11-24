package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
public class ReadExcel {
	
	private Workbook workbook;
    private Sheet sheet;

    // Constructor to load the Excel file and the specific sheet
    public ReadExcel(String filePath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
    }

    // Method to get the data from a specific cell (row and column indices start from 0)
    public String getCellData(int rowNumber, int colNumber) {
        Row row = sheet.getRow(rowNumber);
        if (row == null) return null; // No data in this row

        Cell cell = row.getCell(colNumber);
        if (cell == null) return null; // No data in this cell

        return cell.toString(); // Get data as a string
    }

    // Method to get the number of rows in the sheet
    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    // Method to get the number of columns in the first row
    public int getColumnCount() {
        Row row = sheet.getRow(0);
        return (row != null) ? row.getPhysicalNumberOfCells() : 0;
    }

    // Method to close the workbook (important for freeing resources)
    public void close() throws IOException {
        workbook.close();
    }
	

//    public static void main(String[] args) {
//        String filePath = "D:\\testing\\Utilities CDAC\\Utilities\\src\\test\\resources\\testdata\\ExcelReaderz.xlsx";
//        String sheetName = "IssueList";
//
//        try (FileInputStream fis = new FileInputStream(filePath);
//             Workbook workbook = new XSSFWorkbook(fis)) {
//
//            Sheet sheet = workbook.getSheet(sheetName);
//
//            // Read a specific cell value
//            Row row = sheet.getRow(0); // Row 0 (first row)
//            Cell cell = row.getCell(0); // Cell 0 (first column)
//            String cellValue = cell.getStringCellValue();
//            int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
//            int cellcount=sheet.getRow(1).getLastCellNum();
//            System.out.println("row count" + rowCount);
//            System.out.println("cell count" + cellcount);
//            
//           // System.out.println("Cell Value: " + cellValue);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}