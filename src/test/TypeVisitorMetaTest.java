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

//	@Test
//	public void test_ReturnStaticMethodQualified_Dec_0_Ref_1() {
//		System.out.println("\nSTART 2");
//		configureParser("package bar; public class Other { Bar bar = pack2.Foo.staticMethod();}", type, 0, 1);
//	}

	
	// IMPORT FROM DEFAULT PACKAGE
	
	/*
	 * Check that importing Foo from the Default package, which is not valid Java syntax, still counts as a reference to Foo
	 */
//	@Test
//	public void test_ImportFromDefaultPackage_Dec_0_Ref_1() {
//		configureParser("package bar; import Foo; public class Other {}", type, 0, 1);
//	}
	
	/**
	 * Check that importing Foo from the Default package, which is not valid Java syntax, now sets all simple names of Foo as default Package Foo even with a package declared
	 */
	@Test
	public void test_ImportFromDefaultPackageFieldDeclaration_Dec_0_Ref_2() {
		configureParser("package bar; import Foo; public class Other { private Foo foo; }", "Foo", 0, 2);
	}
//	
//	@Test
//	public void test_ImportFromDefaultPackage2MethodDeclaration_Dec_0_Ref_2() {
//		configureParser("package bar; import Foo; public class Other { public void method(Foo foo) {} }", type, 0, 2);
//	}
	
}
