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
	private static final String type = "pack.Foo";

	@Test
	public void test_ReturnStaticMethod_Dec_0_Ref_1() {
		System.out.println("\nSTART 1");
		configureParser("package pack; public class Other { Bar bar = Foo.staticMethod();}", type, 0, 1);
	}

	@Test
	public void test_ReturnStaticMethodQualified_Dec_0_Ref_1() {
		System.out.println("\nSTART 2");
		configureParser("package pack; public class Other { Bar bar = pack2.Foo.staticMethod();}", type, 0, 1);
	}

//	@Test
//	public void test_ReturnMethod_Dec_0_Ref_0() {
//		System.out.println("\nSTART 2");
//		configureParser("package pack; public class Other { Bar bar = otherMethod();}", type, 0, 0);
//	}
//
//	@Test
//	public void test_ReturnMethod_Dec_1_Ref_0() {
//		System.out.println("\nSTART 3");
//		configureParser("package pack; public class Foo { Bar bar = otherMethod();}", type, 1, 0);
//	}

	@Test
	public void test_SetStaticMethod_Dec_0_Ref_1() {
		System.out.println("START 3");
		configureParser("package pack; public class Other { public void method() { Foo.staticMethod(3);} }", type, 0, 1);
	}

	@Test
	public void test_SetMethod_Dec_0_Ref_1() {
		System.out.println("START 4");
		configureParser("package pack; public class Other { public void method() { otherMethod(3);} }", type, 0, 1);
	}
	
}
