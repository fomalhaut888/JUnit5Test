package test.junit5.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;
import java.util.Arrays;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestArgumentConversion {
	
	/**
	 * 
	 * @param argument
	 * @see https://docs.oracle.com/javase/specs/jls/se8/html/jls-5.html#jls-5.1.2
	 */
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4})
	void intToLong(long argument) {//Widening(使加寬) Primitive Conversion
			assertTrue(argument > 0L);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4})
	void intToFloat(float argument) {//Widening Primitive Conversion
			assertTrue(argument > 0.0F);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4})
	void intToDouble(double argument) {//Widening Primitive Conversion
			assertTrue(argument > 0.0D);
	}
	
	//@ParameterizedTest
	//@ValueSource(ints = {1, 2, 3, 4})
	//void intToByte(byte argument) {//ParameterResolutionException: Error converting parameter at index 0: 
			// No implicit conversion to convert object of type java.lang.Integer to type java.lang.Byte
			//assertTrue(argument > ((byte)0));
	//}
	
	@ParameterizedTest
	@ValueSource(strings = {"true", "true", "true"})
	void stringToBoolean(boolean argument) {//Implicit Conversion
			assertTrue(argument);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Asia/Taipei"})
	void stringToZoneId(ZoneId argument) {//Implicit Conversion
			ZoneId defaultZoneId = ZoneId.systemDefault();
			assertTrue(defaultZoneId.getId().equals(argument.getId()));
			System.out.println("ZoneId=" + argument.getId());
			System.out.println("default ZoneId=" + defaultZoneId.getId());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Simon", "Kevin"})
	void fallbackConversion1(TestObject obj) {//Fallback String-to-Object Conversion with a factory constructor.
			//non-private constructor in the target type & that accepts a single String argument
			// & target type must be declared as either a top-level class or as a static nested class.
			assertTrue(Arrays.asList("Simon", "Kevin", "Bob").contains(obj.getName()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Simon", "Kevin"})
	void fallbackConversion2(TestObject2 obj) {//Fallback String-to-Object Conversion with a factory constructor.
			//non-private constructor in the target type & that accepts a single String argument
			// & target type must be declared as either a top-level class or as a static nested class.
			assertTrue(Arrays.asList("Simon", "Kevin", "Bob").contains(obj.getName()));
	}
	
	static class TestObject2{
		
		private String name;
		
		TestObject2(String name){
				this.name = name;
		}

		public String getName() {
				return name;
		}
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Simon", "Kevin"})
	void fallbackConversion3(TestObject3 obj) {//Fallback String-to-Object Conversion with a factory method.
			//a non-private, static method declared in the target type & that accepts a single String argument
			// & that returns an instance of the target type.
			assertTrue(Arrays.asList("Simon", "Kevin", "Bob").contains(obj.getName()));
	}
	
	static class TestObject3{
		
			private String name;
			
			private TestObject3(String name) {
					this.name = name;
			}
			
			static TestObject3 getInstance(String name) {//The name of the method can be arbitrary and need not follow any particular convention.
					return new TestObject3(name);
			}
			
			String getName() {
					return name;
			}
	}
	
	//Explicit Conversion
	//Read it later.
}

class TestObject{
	
	private String name;
	
	TestObject(String name){
			this.name = name;
	}

	public String getName() {
			return name;
	}
}
