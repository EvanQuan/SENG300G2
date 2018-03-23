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
	private static final String type = "bar.Foo";

	// METHODS


	@Test
	public void test_ReturnStaticMethodQualified_Dec_0_Ref_1() {
		System.out.println("\nSTART 2");
		configureParser("package bar; public class Other { Bar bar = pack2.Foo.staticMethod();}", type, 0, 1);
	}

	
	
	// FIELDS
	/**
	 * Check that calling a static field which returns and stores a value
	 * counts as a reference
	 */
//	@Test
//	public void test_ReturnStaticField_Dec_0_Ref_1() {
//		System.out.println("START");
//		configureParser("package bar; public class Other { Bar bar = Foo.staticField;}", type, 0, 1);
//	}
//
//	/**
//	 * Check that retrieving and setting a static field counts as a reference
//	 */
//	@Test
//	public void test_SetStaticField_Dec_0_Ref_1() {
//		configureParser("package bar; public class Other { public void method() { Foo.staticField = 3;} }", type, 0, 1);
//	}
	
}
