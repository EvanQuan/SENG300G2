package test.iteration1.group7;

import org.junit.Test;

import main.ast.TypeVisitor;

/**
 * JUnit 4 Tests for {@link TypeVisitor} class. Checks type declaration and
 * reference counts for bar.Foo
 *
 * @author Evan Quan
 * @since 12 March 2018
 *
 */
public class TypeVisitor7PackageFooTest extends TypeVisitorTest7 {

	/**
	 * Foo class of package bar
	 */
	private static final String type = "bar.Foo";

	/**
	 * Check that declaring the Foo class in the bar package (correct package)
	 * counts as a declaration
	 */
	@Test
	public void testBarPackageDeclaration_Dec_1_Ref_0() {
		configureParser("package bar; class Foo {}", type, 1, 0);
	}

	/**
	 * Check that calling the Foo constructor in the bar package does count as a
	 * reference
	 */
	@Test
	public void testBarPackageFooConstructor_Dec_0_Ref_1() {
		configureParser("package bar; class Other { Bar bar = new Foo();}", type, 0, 1);
	}

	/**
	 * Check that calling the Foo constructor in a return statement in the bar
	 * package does count as a reference
	 */
	@Test
	public void testBarPackageFooConstructorReturnType_Dec_0_Ref_1() {
		configureParser("package bar; class Other { public Bar method() {return new Foo();}", type, 0, 1);
	}

	/**
	 * Check that calling the Foo constructor separately from the returned instance
	 * in the bar package does count as a reference
	 */
	@Test
	public void testBarPackageFooReturnType_Dec_0_Ref_1() {
		configureParser("package bar; class Other { public Bar method() { Bar foo = new Foo(); return foo;}", type, 0,
				1);
	}

	/**
	 * Check that declaring an instance of Foo in the bar package does count as a
	 * reference
	 */
	@Test
	public void testBarPackageReference_Dec_0_Ref_1() {
		configureParser("package bar; class Other { Foo f; }", type, 0, 1);
	}

	/**
	 * Check that declaring the Foo class in the default package (so not bar), does
	 * not count as a declaration
	 */
	@Test
	public void testDefaultPackageDeclaration_Dec_0_Ref_0() {
		configureParser("class Foo {}", type, 0, 0);
	}

	/**
	 * Check that declaring an instance of Foo in the default package does not count
	 * as a reference
	 */
	@Test
	public void testDefaultPackageReference_Dec_0_Ref_0() {
		configureParser("class Other { Foo f; }", type, 0, 0);
	}

	/**
	 * Check that declaring the Foo class in the other package (so not bar), does
	 * not count as a declaration
	 */
	@Test
	public void testOtherPackageDeclaration_Dec_0_Ref_0() {
		configureParser("package other; class Foo {}", type, 0, 0);
	}

	/**
	 * Check that declaring an instance of Foo in the other package does not count
	 * as a reference
	 */
	@Test
	public void testOtherPackageReference_Dec_0_Ref_0() {
		configureParser("package other; class Other { Foo f; }", type, 0, 0);
	}

	/**
	 * TODO
	 */
	@Test
	public void testImportStatement_Dec_0_Ref_1() {
		configureParser("package other; import bar.Foo; class Other {}", type, 0, 1);
	}

}
