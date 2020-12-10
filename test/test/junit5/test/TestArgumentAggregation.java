package test.junit5.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;

public class TestArgumentAggregation {
	
	@ParameterizedTest
	@CsvSource({
			"Jane, Doe, F, 1990-05-20",
			"John, Doe, M, 1990-10-22"
	})
	void testWithArgumentsAccessor(ArgumentsAccessor arguments) {//ArgumentsAccessor instead of multiple parameters.
			String firstName = arguments.getString(0);
			String lastName = arguments.getString(1);
			//Implicit Conversion
			Gender gender = arguments.get(2, Gender.class);//convert the argument by valueOf(an enum static method)
			LocalDate localDate = arguments.get(3, LocalDate.class);
			if (firstName.equals("Jane")) {
					assertEquals(Gender.F, gender);
			}else {
					assertEquals(Gender.M, gender);
			}
			assertEquals("Doe", lastName);
			assertEquals(1990, localDate.getYear());
	}
	
	@ParameterizedTest
	@CsvSource({
			"Jane, Doe, F, 1990-05-20",
			"John, Doe, M, 1990-10-22"
	})
	void testWithMultipleParameters(String firstName, String lastName, Gender gender, LocalDate localDate) {
			if (firstName.equals("Jane")) {
					assertEquals(Gender.F, gender);
			}else {
					assertEquals(Gender.M, gender);
			}
			assertEquals("Doe", lastName);
			assertEquals(1990, localDate.getYear());
	}
	
	static enum Gender{
			M, F
	}
	
	@ParameterizedTest
	@CsvSource({
			"Jane, Doe, F, 1990-05-20",
			"John, Doe, M, 1990-10-22"
	})
	void testWithArgumentsAggregator(@AggregateWith(StudentAggregator.class) Student student) {//Aggregate arguments to one object
			if (student.getFirstName().equals("Jane")) {
					assertEquals(Gender.F, student.getGender());
			}else {
					assertEquals(Gender.M, student.getGender());
			}
			assertEquals("Doe", student.getLastName());
			assertEquals(1990, student.getLocalDate().getYear());
	}
	
	static class StudentAggregator implements ArgumentsAggregator {
			@Override
			public Student aggregateArguments(ArgumentsAccessor arguments, ParameterContext context)
					throws ArgumentsAggregationException {
					return new Student(arguments.getString(0),
							arguments.getString(1),
							arguments.get(2, Gender.class),
							arguments.get(3, LocalDate.class));
			}
	}
	
	static class Student{
			private String firstName;
			private String lastName;
			private Gender gender;
			private LocalDate localDate;
			
			public Student(String firstName, String lastName, Gender gender, LocalDate localDate) {
					this.firstName = firstName;
					this.lastName = lastName;
					this.gender = gender;
					this.localDate = localDate;
			}
			
			public String getFirstName() {
					return firstName;
			}
			
			public String getLastName() {
					return lastName;
			}
			
			public Gender getGender() {
					return gender;
			}
			
			public LocalDate getLocalDate() {
					return localDate;
			}
	}
	
	@ParameterizedTest
	@CsvSource({
			"Jane, Doe, F, 1990-05-20",
			"John, Doe, M, 1990-10-22"
	})
	void testWithCustomAggregatorAnnotation(@CsvToStudent Student student) {//Aggregate arguments to one object and simplify the code much more.
			if (student.getFirstName().equals("Jane")) {
					assertEquals(Gender.F, student.getGender());
			}else {
					assertEquals(Gender.M, student.getGender());
			}
			assertEquals("Doe", student.getLastName());
			assertEquals(1990, student.getLocalDate().getYear());
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.PARAMETER)
	@AggregateWith(StudentAggregator.class)
	static @interface CsvToStudent {
	}
}
