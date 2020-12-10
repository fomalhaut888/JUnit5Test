package test.junit5.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestInterfaceImpl implements TestInterface {

	@Test
	@Override
	public void test2() {
			TestInterface.super.test2();
			assertEquals("A", "a".toUpperCase());
	}
	
	@Test
	public void test3() {
			assertEquals("XYZ", "xyz".toUpperCase());
	}
}
