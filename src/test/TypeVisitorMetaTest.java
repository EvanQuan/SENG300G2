package test;

import org.junit.Test;

/**
 * Meta test case.
 * Used to test test cases.
 * 
 * @author Evan Quqn
 * @version 1.0.0
 * @since 21 March 2018
 *
 */
public class TypeVisitorMetaTest extends TypeVisitorTest {
	private static final String type = "Foo";

	@Test
	public void test_ReturnStaticMethod_Dec_0_Ref_1() {
		System.out.println("START 1");
		configureParser("public class Other { Bar bar = Foo.staticMethod();}", type, 0, 1);
	}

	@Test
	public void test_SetStaticMethod_Dec_0_Ref_1() {
		System.out.println("START 2");
		configureParser("public class Other { public void method() { Foo.staticMethod(3);} }", type, 0, 1);
	}
	
	@Test
	public void test_ReturnStaticField_Dec_0_Ref_1() {
		System.out.println("START 3");
		configureParser("public class Other { Bar bar = Foo.staticField;}", type, 0, 1);
	}
	
	@Test
	public void test_SetStaticField_Dec_0_Ref_1() {
		System.out.println("START 4");
		configureParser("public class Other { public void method() { Foo.staticField = 3;} }", type, 0, 1);
	}
}
