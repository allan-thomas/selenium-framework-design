package javaseleniumlearning.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javaseleniumlearning.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;

	public LandingPage landingpage;

	public WebDriver initializeDriver() throws IOException {

		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\main\\java\\javaseleniumlearning\\resources\\GlobalData.properties");
		properties.load(fis);
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : properties.getProperty("browser") ;
//		properties.getProperty("browser");
		if (browserName.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\550022867\\OneDrive - GE HealthCare\\Documents\\chromedriver-win64\\chromedriver.exe");
//			System.setProperty("webdriver.chrome.driver",
//					"C:\\Users\\allan\\OneDrive\\Documents\\chromedriver-win64\\chromedriver.exe");
//			System.setProperty("webdriver.chrome.driver","C:/Users/allan/OneDrive/Documents/chromedriver-win64/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			if(browserName.contains("headless"))
				options.addArguments("headless");
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440, 900));

		} else if (browserName.equals("firefox")) {
			// for firefox driver initiaztion

		} else if (browserName.equals("edge")) {
//			System.setProperty("webdriver.edge.driver",
//					"C:\\Users\\167557\\Documents\\edgedriver_win64\\msedgedriver.exe");
			System.setProperty("webdriver.chrome.driver","C:\\Users\\allan\\OneDrive\\Documents\\edgedriver_win64\\msedgedriver.exe");

			driver = new EdgeDriver();

		}

		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		return driver;

	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingpage = new LandingPage(driver);
		landingpage.goTo();
		return landingpage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {

		// read json to String
		String jsonContent = FileUtils.readFileToString(new File(filePath),
				StandardCharsets.UTF_8);

		// convert string to HashMap with Jackson databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File (System.getProperty("user.dir") +"//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") +"//reports//" + testCaseName + ".png";
	}
}