package test;

import org.junit.Test;

public class TypeVisitorIsolationTest extends TypeVisitorTest {
	private static final String type = "Foo";

	@Test
	public void test_ReturnStaticField_Dec_0_Ref_1() {
		configureParser("import bar.*; public class Other { Bar bar = Foo.staticField;}", type, 0, 1);
	}
}
