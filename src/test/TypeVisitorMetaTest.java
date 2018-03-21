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
	public void test_ClassDeclarationLocalToMethod_Dec_1_Ref_0() {
		configureParser("public class Other { public void method() { class Foo{} } }", "Other.method().Foo", 1, 0);
	}
	
}
