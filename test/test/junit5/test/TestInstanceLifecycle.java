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
class TestInstanceLifecycle {
	
	public TestInstanceLifecycle() {
			System.out.println("$$$$$ A TestInstanceLifecycle object created!!!");
	}

	@BeforeAll
	static void initAll() {
			System.out.println("********** Start to test the TestInstanceLifecycle object...");
	}
	
	@BeforeEach
	void init(TestInfo testInfo) {
			System.out.println(">>>>> Start to test the method '" 
					+ testInfo.getTestMethod().get().getName() 
					+ "' of the TestInstanceLifecycle object...");
	}
	
	private int value = 0;
	
	@Test
	void test1() {
			assertEquals(true, true);
			value = 100;
			System.out.println("value=" + this.value);
	}
	
	@Test
	void test2() {
			assertEquals(false, false);
			System.out.println("value=" + this.value);
	}
	
	@Test
	void test3() {
			assertEquals(1, 1);
			System.out.println("value=" + this.value);
	}
	
	@AfterEach
	void tearDown(TestInfo testInfo) {
			System.out.println(">>>>> Finish testing the method '" 
					+ testInfo.getTestMethod().get().getName() 
					+ "' of the TestInstanceLifecycle object...");
	}

	@AfterAll
	static void tearDownAll() {
			System.out.println("********** Finish testing the TestInstanceLifecycle object!!!");
	}
}
