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

//	@Test
//	public void test_ClassDeclarationLocalToMethod_Dec_1_Ref_0() {
//		configureParser("public class Other { public void method() { class Foo{} } }", "Other.method().Foo", 1, 0);
//	}
	
//	/**
//	 * TODO Check that calling a static field which returns and stores a value
//	 * counts as a reference
//	 */
//	@Test
//	public void test_ReturnStaticField_Dec_0_Ref_1() {
//		configureParser("public class Other { Bar bar = Foo.staticField;}", type, 0, 1);
//	}
//
//	/**
//	 * TODO Check that calling a static method which returns and stores a value
//	 * counts as a reference
//	 */
//	@Test
//	public void test_ReturnStaticMethod_Dec_0_Ref_1() {
//		configureParser("public class Other { Bar bar = Foo.staticMethod();}", type, 0, 1);
//	}
//
//	/**
//	 * TODO Check that retrieving and setting a static field counts as a reference
//	 */
//	@Test
//	public void test_SetStaticField_Dec_0_Ref_1() {
//		configureParser("public class Other {Foo.staticField = 3;}", type, 0, 1);
//	}

	/**
	 * TODO Check that calling a void static method with a parameter counts as a
	 * reference
	 */
	@Test
	public void test_SetStaticMethod_Dec_0_Ref_1() {
		configureParser("public class Other {Foo.staticMethod(3);}", type, 0, 1);
	}
	
}
