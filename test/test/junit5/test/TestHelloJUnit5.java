package test.junit5.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

import test.junit5.HelloJUnit5;

class TestHelloJUnit5 {
	
	private String firstName = "小寶";
	private String lastName = "陳";
	private HelloJUnit5 profile = new HelloJUnit5(firstName, lastName);
	
	@BeforeAll
	static void initAll() {
			System.out.println("*********** Start to test the HelloJUnit5 object...");
	}
	
	@BeforeEach
	void init(TestInfo testInfo) {
			System.out.println("$$$$$ Start to test " + testInfo.getTestMethod().get().getName() + " method of the HelloJUnit5 object...");
	}
	
	@Test
	void firstName(TestReporter testReporter) {
			System.out.println("Test firstName...");
			assertEquals(firstName, profile.getFirstName(), "A wrong result of fistName gotten from the HelloJUnit5 object.");
	}
	
	@Test
	void lastName(TestReporter testReporter) {
			System.out.println("Test lastName...");
			assertEquals(lastName, profile.getLastName(), "A wrong result of lastName gotten from the HelloJUnit5 object.");
	}
	
	@Test
	void fullName(TestReporter testReporter) {
			System.out.println("Test fullName...");
			assertEquals("陳 小寶", profile.getFullName(), "A wrong result of fullName gotten from the HelloJUnit5 object.");
	}
	
	@AfterEach
	void tearDown(TestInfo testInfo) {
			System.out.println("$$$$$ Finish testing " + testInfo.getTestMethod().get().getName() + " method of the HelloJUnit5 object!!!");
	}
	
	@AfterAll
	static void tearDownAll() {
			System.out.println("*********** Finish testing the HelloJUnit5 object!!!");
	}
}
