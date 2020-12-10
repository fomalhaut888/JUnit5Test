package test.junit5.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public interface TestInterface {
	
	@BeforeAll
	default void initAll() {
			System.out.println("********** Start to test the TestInterface object...");
	}
	
	@BeforeEach
	default void init(TestInfo testInfo) {
			System.out.println(">>>>> Start to test the method '" 
					+ testInfo.getTestMethod().get().getName() 
					+ "' of the TestInterface object...");
	}
	
	@Test
	default void test1() {
			assertEquals(true, true);
	}
	
	@Test
	default void test2() {
			assertEquals(false, false);
	}
	
	@AfterEach
	default void tearDown(TestInfo testInfo) {
			System.out.println(">>>>> Finish testing the method '" 
					+ testInfo.getTestMethod().get().getName() 
					+ "' of the TestInterface object...");
	}

	@AfterAll
	default void tearDownAll() {
			System.out.println("********** Finish testing the TestInterface object!!!");
	}
}
