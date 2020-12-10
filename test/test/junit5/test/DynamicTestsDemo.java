package test.junit5.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.ThrowingConsumer;

public class DynamicTestsDemo {
	
	@TestFactory
	List<String> dynamicTestsWithInvalidReturnType() {
			//org.junit.platform.commons.JUnitException: @TestFactory method [
			//java.util.List<java.lang.String> test.junit5.DynamicTestsDemo.dynamicTestsWithInvalidReturnType()] 
			// must return a single org.junit.jupiter.api.DynamicNode or a Stream, Collection, Iterable, Iterator, 
			// or array of org.junit.jupiter.api.DynamicNode.
			return Arrays.asList("Hello");
	}
		
	@TestFactory
	Collection<DynamicTest> dynamicTestsFromCollection() {
			return Arrays.asList(
					dynamicTest("1st dynamic test", () -> assertTrue(true)),
					dynamicTest("2nd dynamic test", () -> assertEquals(4, 2 * 2))
			);
	}
	
	@TestFactory
	Iterable<DynamicTest> dynamicTestsFromIterable() {
			return Arrays.asList(
					dynamicTest("3rd dynamic test", () -> assertTrue(true)),
					dynamicTest("4th dynamic test", () -> assertEquals(4, 2 * 2))
			);
	}
	
	@TestFactory
	Iterator<DynamicTest> dynamicTestsFromIterator() {
			return Arrays.asList(
					dynamicTest("5th dynamic test", () -> assertTrue(true)),
					dynamicTest("6th dynamic test", () -> assertEquals(4, 2 * 2)))
					.iterator();
	}
	
	@TestFactory
	DynamicTest[] dynamicTestsFromArray() {
			return new DynamicTest[] {
					dynamicTest("7th dynamic test", () -> assertTrue(true)),
					dynamicTest("8th dynamic test", () -> assertEquals(4, 2 * 2))
			};
	}
	
	@TestFactory
	Stream<DynamicTest> dynamicTestsFromStream() {
			return Stream.of(1, 3, 5, 7)
					.map(i -> dynamicTest(String.valueOf(i), () -> assertTrue(i % 2 == 1)));
	}
	
	@TestFactory
	Stream<DynamicTest> dynamicTestsFromIntStream() {
			// Generates tests for the first 10 even integers.
			return IntStream.iterate(0, n -> n + 2).limit(10)
					.mapToObj(n -> dynamicTest("test" + n, () -> assertTrue(n % 2 == 0)));
	}
	
	@TestFactory
	Stream<DynamicTest> generateRandomNumberOfTestsFromIterator() {
			// Generates random positive integers between 0 and 100 until
			// a number evenly divisible by 7 is encountered.
			Iterator<Integer> inputGenerator = new Iterator<Integer>() {
				
					Random random = new Random();
					int current;
				
					@Override
					public boolean hasNext() {
							current = random.nextInt(100);
			                return current % 7 != 0;
					}
	
					@Override
					public Integer next() {
							return current;
					}
			};
			
			// Generates display names like: input:5, input:37, input:85, etc.
			Function<Integer, String> displayNameGenerator = (input) -> "input:" + input;
			
			// Executes tests based on the current input value.
			ThrowingConsumer<Integer> testExecutor = (input) -> assertTrue(input % 7 != 0);
			
			// Returns a stream of dynamic tests.
			return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
	}
	
	@TestFactory
	Stream<DynamicTest> dynamicTestsFromStreamFactoryMethod() {
			// Stream of palindromes to check
        	Stream<String> inputStream = Stream.of("Simon", "Kevin", "Bob", "Bill");
        	
        	// Generates display names like: racecar is a palindrome
        	Function<String, String> displayNameGenerator = name -> name + " is a boy";
            
            // Executes tests based on the current input value.
            ThrowingConsumer<String> testExecutor = name -> assertTrue(true);
            
            // Returns a stream of dynamic tests.
            //return DynamicTest.stream(inputStream, displayNameGenerator, testExecutor);
            return DynamicTest.stream(inputStream.iterator(), displayNameGenerator, testExecutor);
	}
	
	@TestFactory
	Stream<DynamicNode> dynamicTestsWithContainers() {
			return Stream.of("A", "B", "C")
					.map(input -> dynamicContainer("Container " + input, Stream.of(
							dynamicTest("not null", () -> assertNotNull(input)),
							dynamicContainer("properties", Stream.of(
									dynamicTest("length > 0", () -> assertTrue(input.length() > 0)),
									dynamicTest("not empty", () -> assertFalse(input.isEmpty()))
									))
							)));
	}
	
	@TestFactory
	DynamicNode dynamicNodeSingleTest() {//generates a single DynamicTest instead of a stream,
			return dynamicTest("'Bill' is a boy", () -> assertTrue(true));
	}
	
	@TestFactory
	DynamicNode dynamicNodeSingleContainer() {//generates a nested hierarchy of dynamic tests utilizing DynamicContainer.
			return dynamicContainer("boys", Stream.of("Simon", "Kevin", "Bob", "Bill")
					.map(text -> dynamicTest(text, ()  -> 
							assertTrue(true)
					)));
	}
}
