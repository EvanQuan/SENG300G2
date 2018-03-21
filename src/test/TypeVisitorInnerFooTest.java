package test;

import org.junit.Test;

import main.ast.TypeVisitor;

/**
 * JUnit 4 Tests for {@link TypeVisitor} class. Checks type declaration and
 * reference counts for Foo declared inside another class
 *
 * @author Evan Quan
 * @since 12 March 2018
 *
 */
public class TypeVisitorInnerFooTest extends TypeVisitorTest {

	/**
	 * TODO
	 */
	@Test
	public void test_ClassDeclarationNested_Dec_1_Ref_0() {
		configureParser("public class Other { public class Foo {} }", "Other.Foo", 1, 0);
	}

	@Test
	public void test_ClassDeclarationNested2_Dec_1_Ref_0() {
		configureParser("public class Other { public class Bar { public class Foo{} } }", "Other.Bar.Foo", 1, 0);
	}

	/**
	 * TODO
	 */
	@Test
	public void test_ClassDeclarationLocalToMethod_Dec_1_Ref_0() {
		configureParser("public class Other { public void method() { class Foo{} } }", "Other.method().Foo", 1, 0);
	}
	
	@Test
	public void test_ClassDeclarationAnonymousInMethod_Dec_1_Ref_0() {
		configureParser("public class Other { public void main() { Bar bar = new Bar(); bar.accept(new Foo() { public void fooMethod()}} } ", "", 1, 0);
	}
	
	@Test
	public void test_ClassDeclarationAnonymousFieldDeclaration_Dec_1_Ref_0() {
		configureParser("public class Other { public void main() { Bar bar = new Bar(); bar.accept(new Foo() { public void fooMethod()}} } ", "", 1, 0);
	}
}
