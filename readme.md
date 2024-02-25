EBay_project : 
Project is developed using maven build automation tool.
TestNg is being used for execution of test cases.
Separate logging mechanism is not implemented in the project, SYSOUT statements were used for printing logs.
PageFactory design is used for developing the script.

Path: /EBay_project/src/test/java/Page_objects
Above path consists of page objects of ebay page.

Path: /EBay_project/src/test/java/Test_scripts
Above path consists of test scripts for execution

path: pom.xml
Above file has details of the libraries being used for developing the script

path: testng.xml
Above file can be used to run the test cases.

Assumptions:
1.The url being used is hardcoded.
2.validation of the location filter is done by means of the field international shipping.(as this field is visible only when the location is choosen as worldwide)
3.validation of the price filter is done by fetching value of price from the webpage and compare the value exists between 100-200 dollars(hardcoded input values for max and min price range).
4.validation of product condition is done by means of field condition in the right side of the product
String used for validating search bar is hardcoded.

// URL being used is hardcoded