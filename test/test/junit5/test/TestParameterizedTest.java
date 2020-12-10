package test.junit5.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class TestParameterizedTest {
	
	@ParameterizedTest
	@ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
	void palindromes(String candidate) {//give the string values in the attribute 'strings' in ValueSource
			//assertTrue(StringUtils.isPalindrome(candidate));
			assertTrue(candidate.charAt(0) == candidate.charAt(candidate.length() - 1));
	}
	
	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3 })
	void testWithValueSource(int argument) {//give the int values in the attribute 'ints' in ValueSource
			assertTrue(argument > 0 && argument < 4);
	}
	
	@ParameterizedTest
	@ValueSource(booleans = { true, false, true })
	void testWithValueSource(boolean argument) {//give the boolean values in the attribute 'booleans' in ValueSource
			assertTrue(Boolean.valueOf(argument) instanceof Boolean);
	}
	
	@ParameterizedTest
	@NullSource
	@EmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStrings(String text) {//give null, "", and then values in ValueSource
			assertTrue(text == null || text.trim().isEmpty());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStrings2(String text) {//give null, "", and then values in ValueSource
	    	assertTrue(text == null || text.trim().isEmpty());
	}
	
	@ParameterizedTest
	@EnumSource
	void testWithEnumSourceWithAutoDetection(Days day) {//give all of the enums
			assertNotNull(day);
	}
	
	@ParameterizedTest
	@EnumSource(names = { "THURSDAY", "SATURDAY" })
	void testWithEnumSourceInclude(Days day) {//give "THURSDAY" and "SATURDAY"
			assertTrue(EnumSet.of(Days.MONDAY, Days.THURSDAY, Days.SATURDAY).contains(day));
	}
	
	@ParameterizedTest
	@EnumSource(mode = EnumSource.Mode.EXCLUDE, names = { "THURSDAY", "SATURDAY" })
	void testWithEnumSourceExclude(Days day) {//give enums excluding "THURSDAY" and "SATURDAY"
			assertFalse(EnumSet.of(Days.THURSDAY, Days.SATURDAY).contains(day));
	}
	
	@ParameterizedTest
	@EnumSource(mode = EnumSource.Mode.MATCH_ALL, names = "^.*DAY$")
	void testWithEnumSourceRegex(Days day) {//regular expression
			assertTrue(day.name().endsWith("DAY"));
	}
	
	@ParameterizedTest
	@MethodSource("stringProvider")
	void testWithExplicitLocalMethodSource(String argument) {//explicit name of the factory method
			assertNotNull(argument);
	}
	
	static Stream<String> stringProvider() {//be always static and with no argument.
			return Stream.of("apple", "banana");
	}
	
	@ParameterizedTest
	@MethodSource
	void testWithDefaultLocalMethodSource(String argument) {
			// JUnit Jupiter will search for a factory method that has the same name 
			// as the current @ParameterizedTest method by convention.
			assertNotNull(argument);
	}
	
	static Stream<String> testWithDefaultLocalMethodSource() {
			return Stream.of("apple", "banana");
	}
	
	@ParameterizedTest
	@MethodSource("range")
	void testWithRangeMethodSource(int argument) {//IntStream from MethodSource
			assertNotEquals(9, argument);
	}
	
	static IntStream range() {
	    	return IntStream.range(0, 20).skip(10);//Returns a stream consisting of the remaining elements of this stream
	    	//after discarding the FIRST 10 elements(0-9 skipped) of the stream.
	}
	
	@ParameterizedTest
	@MethodSource("stringIntAndListProvider")
	void testWithMultiArgMethodSource(String str, int num, List<String> list) {//structure data from MethodSource
			assertEquals(5, str.length());
			assertTrue(num >=1 && num <=2);
			assertEquals(2, list.size());
	}
	
	static Stream<Arguments> stringIntAndListProvider() {
			return Stream.of(
					Arguments.arguments("apple", 1, Arrays.asList("a", "b")),
					Arguments.arguments("lemon", 2, Arrays.asList("x", "y"))
			);
	}
	
	@ParameterizedTest
    @MethodSource(" test.junit5.test.StringsProviders#tinyStrings")
    void testWithExternalMethodSource(String tinyString) {//Only top class used(static class disallowed)
			assertNotNull(tinyString);
    }
	
	@ParameterizedTest
	@CsvSource({
			"apple,         1",
			"banana,        2",
			"'lemon, lime', 0xF1"
	})
	void testWithCsvSource(String fruit, int rank) {
			assertNotNull(fruit);
			assertNotEquals(0, rank);
			System.out.println("rank=" + rank);
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"apple, banana, NIL"
	}, nullValues = "NIL")
	void testWithNullCsvSource(String fruit1, String fruit2, String fruit3) {//Let NIL be null 
			assertNotNull(fruit1);
			assertTrue(fruit2.equals("banana"));
			assertNull(fruit3);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/two-column.csv", numLinesToSkip = 1)
	void testWithCsvFileSourceFromClasspath(String country, int reference) {//classpath
			assertNotNull(country);
			assertNotEquals(0, reference);
	}
	
	//@ParameterizedTest
	//@CsvFileSource(files = "E:/LeadWS10/JUnit5Test/test/two-column.csv", numLinesToSkip = 1)
	//void testWithCsvFileSourceFromFile(String country, int reference) {//5.6 not supported
			//assertNotNull(country);
			//assertNotEquals(0, reference);
	//}
	
	@ParameterizedTest
	@ArgumentsSource(MyArgumentsProvider.class)
	void testWithTopArgumentsSource(String argument) {//top-level ArgumentsProvider class
			assertNotNull(argument);
	}
	
	@ParameterizedTest
	@ArgumentsSource(MyArgumentsProvider2.class)
	void testWithStaticArgumentsSource(int argument) {//static ArgumentsProvider class
			assertTrue(argument > 0);
	}
	
	static class MyArgumentsProvider2 implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
				return Stream.of(1, 2, 3, 4).map(Arguments::of);
		}
	}
}

class MyArgumentsProvider implements ArgumentsProvider {

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
			return Stream.of("apple", "banana").map(Arguments::of);
	}
}

class StringsProviders {

	static Stream<String> tinyStrings() {
			return Stream.of(".", "oo", "OOO");
	}
}

enum Days{
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}
