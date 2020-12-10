package test.junit5.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class TestRepeatedTest {
	
	private Random random = new Random();
	
	@RepeatedTest(10)
	void repeatedTest() {
			assertEquals(true, random.nextDouble() > 0.5D);
	}
	
	@RepeatedTest(5)
    void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
			assertEquals(5, repetitionInfo.getTotalRepetitions());
    }
	
	@RepeatedTest(value = 1, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Repeat!")
    void customDisplayName(TestInfo testInfo) {
			assertEquals("Repeat! 1/1", testInfo.getDisplayName());
    }
	
	@RepeatedTest(value = 1, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Details...")
    void customDisplayNameWithLongPattern(TestInfo testInfo) {
			assertEquals("Details... :: repetition 1 of 1", testInfo.getDisplayName());
    }
	
	@RepeatedTest(value = 5, name = "Wiederholung {currentRepetition} von {totalRepetitions}")
    void repeatedTestInGerman() {
			assertEquals(true, true);
    }
}
