package test.junit5.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestParameterizedTestDisplayName {
	
	@DisplayName("Display name of container")
	@ParameterizedTest(name = "{index} ==> the rank of ''{0}'' is {1}")
	@CsvSource({ "apple, 1", "banana, 2", "'lemon, lime', 3" })
	void testWithCustomDisplayNames(String fruit, int rank) {
			assertNotNull(fruit);
			assertTrue(rank > 0);
	}
}
