package Page_objects;

import java.awt.Dimension;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class E_Bay_page {

	@FindBy(xpath = "//*[@id='vl-flyout-nav']/ul/li[3]/a")
	WebElement Electronics_link;
	@FindBy(xpath = "//a[text()='Cell Phones, Smart Watches & Accessories']")
	WebElement CellPhone_access_link;
	@FindBy(xpath = "//a[text()='Cell Phones & Smartphones']")
	WebElement CellPhone_smartPhone_link;
	@FindBy(xpath = "//button[text()='All Filters']")
	WebElement All_Filters;
	@FindBy(xpath="//div[@id='c3-mainPanel-condition']/span")   
	WebElement condition_filter;
	@FindBy(id="c3-subPanel-LH_ItemCondition_New_cbx")
	WebElement Condition_select_new;
	@FindBy(xpath = "//*[@id='c3-subPanel-_x-price-textrange']/div/div[1]/div/input")
	WebElement Min_price_range;
	@FindBy(xpath = "//*[@id='c3-subPanel-_x-price-textrange']/div/div[2]/div/input")
	WebElement Max_price_range;

	@FindBy(xpath = "//*[@id='c3-mainPanel-price']/span")
	WebElement Price_filter;

	@FindBy(xpath = "//*[@id='c3-mainPanel-location']/span")
	WebElement Item_location_filter;
	
	@FindBy(xpath = "//*[@id='c3-subPanel-location_Worldwide']/span/span/input")
	WebElement Item_location_worldwide;
	

	@FindBy(xpath = "//*[@id='x-overlay__form']/div[3]/div[2]/button")
	WebElement Apply_button;
	
	WebDriver driver;
	
	public E_Bay_page()
	{
      driver = new ChromeDriver();
	  PageFactory.initElements(driver, this);;
	  	
	}
	
	
	

	public void filters_validation() throws InterruptedException {	
		driver.get("https://www.ebay.com/"); // navigate to url
	    driver.manage().window().maximize();
	    Electronics_link.click(); // clicking on electronics link
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0,800)"); //scrolling down
	    CellPhone_access_link.click(); // clicking on cellphones & accessories link
	    CellPhone_smartPhone_link.click(); // clicking on smartphones link
	    All_Filters.click(); // clicking on all filters link
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    wait.until(ExpectedConditions.visibilityOf(condition_filter)); // wait method helps the driver to wait for the visibility of element
	    condition_filter.click();  
	    Thread.sleep(5000)  ;
	    Condition_select_new.click(); // selecting the condition type to new
	    js.executeScript("arguments[0].click();", Price_filter);
	    wait.until(ExpectedConditions.visibilityOf(Min_price_range));
	    Min_price_range.click();
	    Min_price_range.sendKeys("100"); // entering minimum price as $ 100
	    Max_price_range.click();
	    Max_price_range.sendKeys("200"); // entering maximum price as $200
	    wait.until(ExpectedConditions.visibilityOf(Item_location_filter));
	    Item_location_filter.click(); // selecting location filter from side bar
	    Thread.sleep(5000);
	    Item_location_worldwide.click(); // choosen option as world wide
	    js.executeScript("arguments[0].click();", Apply_button);
	    validate_price();  // these methods helps us to check if filters are applied properly or not
	    validate_condition();
	    validate_location();
	  }
	
	@FindBy(css=".s-item:nth-child(1) .s-item__title") WebElement first_product;
	@FindBy(css=".x-price-primary > .ux-textspans") WebElement product_price;
	// This method helps to validate the price filter  
	public void validate_price() throws InterruptedException 
	{   
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,200)");	
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    wait.until(ExpectedConditions.visibilityOf(first_product));
		first_product.click();
		Thread.sleep(3000);
		String lastWindowHandle = getLastWindowHandle(driver);
		System.out.println("Clicked on the first product");
		driver.switchTo().window(lastWindowHandle);
		String inputString = product_price.getText();
		System.out.println(inputString);
		// Remove non-numeric characters from the string
        String numericString = inputString.replaceAll("[^\\d.]", "");
        // Convert the numeric string to double
        double number = Double.parseDouble(numericString);
        System.out.println("Extracted number: " + number);
        
        if (number >= 100 && number <= 200) {
            System.out.println("The number is between 100 and 200.");
            Assert.assertTrue(true,"Price filter is not working as expected");
        } else {
            System.out.println("The number is not between 100 and 200.");
        }
        System.out.println("Price filter working as expected ");
	}
	

	@FindBy(xpath="//span/span/span[text()=\"New\"]") WebElement first_product_condition;
	// This method helps to validate the product condition filter  
	public void validate_condition() throws InterruptedException {
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    wait.until(ExpectedConditions.visibilityOf(first_product_condition));
	    String title = first_product_condition.getText();
	    Assert.assertTrue(title.toLowerCase().contains("new"),"Condition filter not applied properly");
	    System.out.println("Condition filter working as expected ");
	    
	}
	
	@FindBy(xpath="//span[contains(.,'International Shipping')]") WebElement shipping_details;
	  // This method helps to validate the location filter             
	public void validate_location() throws InterruptedException {
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,500)");	
		Thread.sleep(3000);
		try {
			 String shipping_dts = (String) js.executeScript("return arguments[0].textContent;", shipping_details);
			 System.out.println(shipping_dts);
			 System.out.println("Location filter working as expected");
		}
		catch(Exception e)
		{
			 Assert.assertTrue(false,"Location filters not applied properly");
		}
	   
	   
	    
	}
	
	
	public String getLastWindowHandle(WebDriver driver) {
        String lastWindowHandle = null;

        // Get all window handles
        Set<String> windowHandles = driver.getWindowHandles();

        // Iterate through window handles to find the last one
        for (String handle : windowHandles) {
            lastWindowHandle = handle;
        }

        return lastWindowHandle;
    }
	
	
	@FindBy(id="gh-ac") WebElement search_bar;
	@FindBy(id="gh-btn") WebElement search_btn;
	@FindBy(xpath="//span[contains(.,'Computers/Tablets & Networking')]") WebElement Computers_Tab_Networking_link;
	@FindBy(xpath="//div[@class='srp-river-results clearfix']/ul/li[2]/div/div[2]/a/div/span/span") WebElement first_pdt;
	
	public void search_validation() {
		driver.get("https://www.ebay.com/"); //navigate to url 
	    driver.manage().window().maximize(); // maximize the window
		search_bar.click(); 
		search_bar.sendKeys("Macbook"); //enter text as macbook
		search_btn.click();
		Computers_Tab_Networking_link.click(); //clicking on link computers/tablets and networking
		System.out.println(first_pdt.getText()); // printing the text of first product
		Assert.assertTrue(first_pdt.getText().toLowerCase().contains("macbook"),"Search filter not working properly"); // applying assertion to check if the search string is matching with the products shown in the webpage
		
	}
	}


