package testcases;

import java.io.IOException;import java.util.EmptyStackException;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import base.BaseClass;
import utilities.ReadExcel;

public class RaiseIssueTC extends BaseClass {
	
	
	@Test
    public void Raisebug() throws InterruptedException {
		String filePath = "G:\\Testing Workspace\\ACUtilities\\src\\test\\resources\\testdata\\ExcelReader.xlsx";
        String sheetName = "IssueList1";
		
		
        try {
            // Initialize the ExcelReader
            ReadExcel excelReader = new ReadExcel(filePath, sheetName);

            // Get row and column count
            int rowCount = excelReader.getRowCount();
            int colCount = excelReader.getColumnCount();
           System.out.println("Total Rows: " + rowCount);
            for (int r = 1; r < rowCount; r++) {  
            	//for (int c = 0; c < colCount; c++) {
            		String desiredValue = excelReader.getCellData(r, 0);
            		String componenent = desiredValue.replaceAll("[\\n\\]]", "").trim();
            	WebElement dropdown = driver.findElement(By.id("component"));
            	Select select = new Select(dropdown);
            	List<WebElement> options = select.getOptions();
            	for (WebElement option : options) {            		
            		String actul = option.getText();            		
            	    if (actul.equals(componenent)) {
            	        select.selectByVisibleText(componenent); // Select the matching option
            	        System.out.println("Option found and selected.");
            	        break;
            	    }
            	}
            		
            		String Severity = excelReader.getCellData(r, 1);
            		System.out.println("Severity" + Severity);
            		WebElement SeverityElement = BaseClass.getDriver().findElement(By.id("bug_severity"));
               	    Select Severitydropdown = new Select(SeverityElement);
               	 Severitydropdown.selectByVisibleText(Severity); 
                  
                    
            		String Category = excelReader.getCellData(r, 2);
            		System.out.println("Category" + Category);
            		WebElement CategoryElement = BaseClass.getDriver().findElement(By.id("cf_defect_category"));
               	    Select Categorydropdown = new Select(CategoryElement);
               	    Categorydropdown.selectByVisibleText(Category);
               	    
               	    try
               	    {
            		String DefectType = excelReader.getCellData(r, 3);
            		System.out.println("DefectType" + DefectType);
            		WebElement DefecttypeElement = BaseClass.getDriver().findElement(By.id("cf_defect_type"));
               	    Select DefecttypeElementdropdown = new Select(DefecttypeElement);
               	    DefecttypeElementdropdown.selectByVisibleText(DefectType); 
               	    }
               	     catch(Exception ex)
               	     {
               	    	 System.out.println("Fields does not contain any data"   + ex);
               	     }
               	    String Summary = excelReader.getCellData(r, 4);           		
            		System.out.println("Summary" + Summary);
            		WebElement SummaryField = BaseClass.getDriver().findElement(By.id("short_desc"));
            		SummaryField.sendKeys(Summary);
            		
            		
            		
            		String IssueDescription = excelReader.getCellData(r, 5);
            		System.out.println("IssueDescription" + IssueDescription);
            		WebElement IssueDescriptionField = BaseClass.getDriver().findElement(By.id("comment"));
            		IssueDescriptionField.sendKeys(IssueDescription);
            		
              //  }
            	Thread.sleep(500);
                 String url = BaseClass.getDriver().getCurrentUrl();
     			driver.switchTo().newWindow(WindowType.TAB);
     			driver.navigate().to(url);
            }
            Set<String> windowHandles = driver.getWindowHandles();
            int tabCount = windowHandles.size();
            System.out.println("Number of bug inserted: " + tabCount);

            // Close the ExcelReader to free resources
            excelReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		

	
	}

}