package test.junit5.test;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class TestTimeout {
	
	@BeforeEach
	@Timeout(5)
	void setUp() throws InterruptedException {//java.util.concurrent.TimeoutException: setUp() timed out after 5 seconds
			// fails if execution time exceeds 5 seconds
			//Thread.sleep(6 * 1000L);
	}
	
	@Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	void failsIfExecutionTimeExceeds100Milliseconds() throws InterruptedException {//java.util.concurrent.TimeoutException: failsIfExecutionTimeExceeds100Milliseconds() timed out after 100 milliseconds
			// fails if execution time exceeds 100 milliseconds
			//Thread.sleep(1 * 1000L);
	}
	
	//Using @Timeout for Polling Tests
	//Read it later
	
	//Disable @Timeout Globally
	//Read it later
}
