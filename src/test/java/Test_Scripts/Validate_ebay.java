package Test_Scripts;

import org.testng.annotations.Test;

import Page_objects.E_Bay_page;

public class Validate_ebay {

	public E_Bay_page ebay = new E_Bay_page();;
	
	@Test(priority=1)
	public void filters() throws InterruptedException {
		
		ebay.filters_validation();
	}
	
	@Test(priority=2)
	public void search() {
		ebay.search_validation();
	}
}
