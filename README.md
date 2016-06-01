# ta-sample-testng-geb-selenium-allure
This is sample test automation project with geb-testng-selenium-allure

# This is small but complete test automation project with
* Maven
* TestNG
* Geb
* Selenium webdriver
* Allure reports

# Prerequisits
Currently project based on Selenium 2.46.0 and supports only Firefox (tested with Firefox 38.0)
To run this project on other version please change corresponding section in pom.xml for selenium and install appropriate Firefox

#Tests
	SortingOnePageTest - performs search for trains from Berlin to Prague and checks that results were ordered by price

	SortingBusResultsTest - performs search for bus from Berlin to Sofia and checks that results were ordered by price

	SortingMultyPageResultTest - performs search for bus from Berlin to Hamburg and checks that results were ordered by price. Results are collected across different pages results.


#Execution
	To execute test project run command:
		mvn test -DxmlFileName=testng 

where testng - filename from src\test\resources\testng folder with preconfigured test suite.

# Build reports
	To build allure reports run command:
		mvn site

# Open reports
	Reports can be found under target folder. Open target/site/allure-maven-plugin/index.html (not worked in Chrome)


# Test-Build-Open Reports
	In order to automate execution pipeline, m.bat from a root file can be used.
