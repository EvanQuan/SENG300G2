package test;

import org.junit.Test;

import main.ast.TypeVisitor;

/**
 * JUnit 4 Tests for {@link TypeVisitor} class. Checks type declaration and
 * reference counts for Foo declared inside another class
 *
 * @author Evan Quan
 * @since 21 March 2018
 *
 */
public class TypeVisitorPrimitiveTest extends TypeVisitorTest {
	
	@Test
	public void test_intHexidecimal_Dec_0_Ref_1() {
		configureParser("public class Other {int x = 0x1a;}", "int", 0, 1);
	}
	
	@Test
	public void test_doubleWithPackage_Dec_0_Ref_1() {
		configureParser("package pack; public class Other {double x;}", "double", 0, 1);
	}
	
	@Test
	public void test_shortWithImport_Dec_0_Ref_1() {
		configureParser("import pack.short; public class Other {short x;}", "short", 0, 1);
	}
	
	@Test
	public void test_longCast_Dec_0_Ref_2() {
		configureParser("public class Other { short s = 1; long l = (long) s;}", "long", 0, 2);
	}
	
	@Test
	public void test_floatScientificNotation_Dec_0_Ref_2() {
		configureParser("public class Other { float f = 1.23e4; }", "float", 0, 1); 
	}
	
	@Test
	public void test_byteUnderscore_Dec_0_Ref_1() {
		configureParser("public class Other { byte b = 0b0010_0101; }", "byte", 0, 1); 
	}
	
	@Test
	public void test_booleanTrue_Dec_0_Ref_1() {
		configureParser("public class Other { boolean b = true }", "boolean", 0, 1);
	}
	
	@Test
	public void test_char_Dec_0_Ref_1() {
		configureParser("public class Other { char c = 'a'}", "char", 0, 1);
	}
}
