package Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class base {

	public WebDriver driver; // Static means another class can?t modify your
								// static object
	public Properties prop;
	public WebDriverWait wait;
	public SoftAssert softAssertion = new SoftAssert();

	public WebDriver initializerDriver() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\data.properties");
		prop.load(fis);
		String browserName = prop.getProperty("Browser");

		if (browserName.contains("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.contains("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.contains("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		/*
		 * if (browserName.contains("Chrome")) {
		 * System.setProperty("webdriver.chrome.driver",
<<<<<<< HEAD
		 * "C:\\chromedriver\\chromedriver.exe"); driver = new ChromeDriver(); }
		 * else if (browserName.contains("Firefox")) {
		 * 
		 * System.setProperty("webdriver.gecko.driver",
		 * "C:\\geckodriver\\geckodriver.exe"); driver = new FirefoxDriver(); }
		 * else if (browserName.contains("Edge")) {
=======
		 * "C:\\chromedriver\\chromedriver.exe"); driver = new ChromeDriver(); } else if
		 * (browserName.contains("Firefox")) {
		 * 
		 * System.setProperty("webdriver.gecko.driver",
		 * "C:\\geckodriver\\geckodriver.exe"); driver = new FirefoxDriver(); } else if
		 * (browserName.contains("Edge")) {
>>>>>>> aa042e4fb4eb3a4489d15fba512bd48c48ba9f70
		 * 
		 * System.setProperty("webdriver.edge.driver",
		 * "C:\\MicrosoftEdge\\msedgedriver.exe"); driver = new EdgeDriver(); }
		 */

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
		return driver;

	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;

	}

	public ArrayList xls(String Url, String sheetname) throws Exception {
		FileInputStream fis = new FileInputStream(Url);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetname);
		int row_num = sheet.getLastRowNum(); // No of rows.
		int col_num = sheet.getRow(0).getLastCellNum();
		System.out.println(row_num);
		System.out.println(col_num);
		ArrayList datalist = new ArrayList();
		for (int i = 0; i <= row_num; i++) {
			for (int j = 0; j < col_num; j++) {
				Cell cell = sheet.getRow(i).getCell(j);
				String cellval = cell.getStringCellValue();
				datalist.add(cellval);
			}
		}
		return datalist;
	}

	public void assert_message(List<WebElement> li, String[] str) {
		int i = 0;
		for (WebElement Validation_message : li) {
			softAssertion.assertTrue(Validation_message.getText().equals(str[i]), str[i] + " Validation Failed");
			i++;
		}
	}

}
