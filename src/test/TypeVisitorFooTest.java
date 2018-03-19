package test;

import org.junit.Test;

import main.TypeVisitor;

/**
 * JUnit 4 Tests for {@link TypeVisitor} class. Checks type declaration and
 * reference counts for Foo
 *
 * @author Evan Quan
 * @since 14 March 2018
 *
 */
public class TypeVisitorFooTest extends TypeVisitorTest {

	private static final String type = "Foo";

	/**
	 * Check that declaring an variable of another class that references Foo as a
	 * generic parameter counts as a reference
	 */
	@Test
	public void test1ParamterizedType_Dec_0_Ref_1() {
		configureParser("public class Other{ Bar<Foo> bar;}", type, 0, 1);
	}

	/**
	 * Check that declaring an variable of another class that references Foo as a
	 * generic parameter twice counts as 2 references
	 */
	@Test
	public void test2ParameterizedTypes_Dec_0_Ref_2() {
		configureParser("public class Other{ Bar<Foo, Foo> bar;}", type, 0, 2);
	}

	/**
	 * Check that declaring an variable of another class that references Foo as a
	 * generic parameter twice counts as 2 references with another third generic
	 * parameter of another class
	 */
	@Test
	public void test3ParameterizedTypesMixed_Dec_0_Ref_2() {
		configureParser("public class Other{ Bar<Foo, String, Foo> bar;}", type, 0, 2);
	}

	/**
	 * Check if an annotation declaration is counted as a declaration
	 */
	@Test
	public void testAnnotationDeclaration_Dec_1_Ref_0() {
		configureParser("@interface Foo {}", type, 1, 0);
	}

	/**
	 * Check that instantiating an array of Foo counts a 1 reference
	 */
	@Test
	public void testArrayDeclarableVariableAndAllocate_Dec_0_Ref_1() {
		configureParser("public class Other {Bar[] bar = new Foo[1];}", type, 0, 1);
	}

	/**
	 * Check that declaring and instantiating an array of Foo counts as 2 references
	 */
	@Test
	public void testArrayDeclarableVariableAndAllocate_Dec_0_Ref_2() {
		configureParser("public class Other {Foo[] foo = new Foo[1];}", type, 0, 2);
	}

	/**
	 * Check that creating a variable of an array of Foo counts as a reference
	 */
	@Test
	public void testArrayDeclareVariable_Dec_0_Ref_1() {
		configureParser("public class Other {Foo[] foo;}", type, 0, 1);
	}

	/**
	 * Check if declaring a meta class of Foo counts as a reference
	 */
	@Test
	public void testClassClass_Dec_0_Ref_1() {
		configureParser("public class Other{Class<Foo> foo;}", type, 0, 1);
	}

	/**
	 * Check if declaring a meta class of Foo counts as a reference TODO return here
	 */
	@Test
	public void testClassClass_Dec_0_Ref_2() {
		configureParser("public class Other{Class<Foo> foo = Foo.class;}", type, 0, 2);
	}

	/**
	 * Check if a class declaration is counted as a declaration
	 */
	@Test
	public void testClassDeclaration_Dec_1_Ref_0() {
		configureParser("class Foo {}", type, 1, 0);
	}

	/**
	 * Check that declaring an variable inside a method counts as a reference
	 */
	@Test
	public void testDeclareAndInstantiateInsideMethod_Dec_0_Ref_2() {
		configureParser("public class Other{ public void bar() {Foo f = new Foo();}", type, 0, 2);
	}

	/**
	 * Check if a variable declaration and instantiating it with a constructor is
	 * counted as 2 references
	 */
	@Test
	public void testDeclareAndInstantiateVariable_Dec_0_Ref_2() {
		configureParser("public class Other { Foo foo = new Foo();}", type, 0, 2);
	}

	/**
	 * Check that declaring an variable inside a method counts as a reference
	 */
	@Test
	public void testDeclareInsideMethod_Dec_0_Ref_1() {
		configureParser("public class Other{ public void bar() {Foo f;}", type, 0, 1);
	}

	/**
	 * Check if a variable declaration is counted as a reference
	 */
	@Test
	public void testDeclareVariable_Dec_0_Ref_1() {
		configureParser("public class Other{ Foo foo;}", type, 0, 1);
	}

	/**
	 * Check if an enum declaration is counted as a declaration
	 */
	@Test
	public void testEnumDeclaration_Dec_1_Ref_0() {
		configureParser("enum Foo {}", type, 1, 0);
	}

	@Test
	public void testForLoopInitialization_Dec_0_Ref_1() {
		configureParser("public class Other { public void method() { for (Foo f;;){}}}", type, 0, 1);
	}

	/**
	 * Check that illegal Java syntax (cannot be compiled) results in no references
	 * or declarations
	 */
	@Test
	public void testIllegalSyntax_Dec_0_Ref_0() {
		configureParser("This is invalid Java syntax; Foo foo; Foo foo2 = new Foo();", type, 0, 0);
	}

	/**
	 * Check that a reference of Foo instantiated inside a parameter of another
	 * constructor is counted
	 */
	@Test
	public void testInstantiateInsideOtherConstructor_Dec_0_Ref_1() {
		configureParser("public class Other{ Other2 other = new Other2(new Foo());}", type, 0, 1);
	}

	/**
	 * Check that an instantiation of another class inside a Foo constructor counts
	 * the reference
	 */
	@Test
	public void testInstantiateOtherInsideConstructor_Dec_0_Ref_2() {
		configureParser("public class Other{ Foo foo = new Foo(new Other2());}", type, 0, 2);
	}

	/**
	 * Check that instantiating an instance of a Parent of Foo with the constructor
	 * of Foo counts 1 reference
	 */
	@Test
	public void testInstantiateVariableOfParent_Dec_1_Ref_1() {
		configureParser("public class Other{ FooParent foo = new Foo();}", type, 0, 1);
	}

	/**
	 * Check that instantiating an instance of a Child of Foo in a variable of type
	 * Foo counts as 1 reference
	 */
	@Test
	public void testInstantiatingVariableOfChild_Dec_0_Ref_1() {
		configureParser("public class Other{ Foo foo = new FooChild();}", type, 0, 1);
	}

	/**
	 * Check if an interface declaration is counted as a declaration
	 */
	@Test
	public void testInterfaceDeclaration_Dec_1_Ref_0() {
		configureParser("interface Foo {}", type, 1, 0);
	}

	/**
	 * Check if an annotation reference is counted as a reference
	 */
	@Test
	public void testMarkerAnnotationReference_Dec_0_Ref_1() {
		configureParser("public class Other{@Foo public void method() {}}", type, 0, 1);
	}

	/**
	 * Check if a return type reference in a method declaration is counted as a
	 * reference
	 */
	@Test
	public void testMethodReturn_Dec_0_Ref_1() {
		configureParser("public class Other{ public Foo methodName() {}}", type, 0, 1);
	}

	/**
	 * Check if a marker annotation that references Foo as a parameter is counted as
	 * a reference
	 */
	@Test
	public void testNormalAnnotationParameterReference_Dec_0_Ref_1() {
		configureParser("public class Other{@Test(expected = Foo.class) public void test() {}}", type, 0, 1);
	}

	/**
	 * Check if a class declaration of another type does not affect declaration
	 * count
	 */
	@Test
	public void testOtherClassDeclaration_Dec_0_Ref_0() {
		configureParser("class Other {}", type, 0, 0);
	}

	/**
	 * Check that returning an instance directly from constructor counts as a
	 * reference
	 */
	@Test
	public void testReturnConstructor_Dec_0_Ref_2() {
		configureParser("public class Other{public Foo bar() {return new Foo();}", type, 0, 2);
	}

	/**
	 * Check if a a variable declaration and setting as another variable's value is
	 * counted as a reference
	 */
	@Test
	public void testSetVariable_Dec_0_Ref_1() {
		configureParser("public class Other { Foo foo = anotherFoo;}", type, 0, 1);
	}

	/**
	 * TODO Check that declaring an variable of another class that references Foo as
	 * a generic parameter and instantiates it counts as 2 references
	 */
	@Test
	public void test1ParamterizedTypeAndInstantiated_Dec_0_Ref_2() {
		configureParser("public class Other{ Bar<Foo> bar = new Bar<Foo>();}", type, 0, 2);
	}

	/**
	 * TODO Check that declaring an variable of another class that references Foo as
	 * a generic parameter twice and instantiates it counts as 4 references
	 */
	@Test
	public void test2ParameterizedTypesAndInstantiated_Dec_0_Ref_4() {
		configureParser("public class Other{ Bar<Foo, Foo> bar = new Bar<Foo, Foo>();}", type, 0, 4);
	}

	/**
	 * TODO Check that declaring an variable of another class that references Foo as
	 * a generic parameter twice and instantiates it counts as 4 references, with
	 * another third generic parameter of another class
	 */
	@Test
	public void test3ParameterizedTypesAndInstantiatedMixed_dec_0_Ref_4() {
		configureParser("public class Other{ Bar<Foo, String, Foo> bar = new Bar<Foo, String, Foo>();}", type, 0, 4);
	}

	/**
	 * TODO Check if an @link annotation in Javadoc counts as a reference
	 */
	@Test
	public void testLinkAnnotation_Dec_0_Ref_1() {
		configureParser("/**" + ls + " * {@link Foo}" + ls + " */" + ls + "public class Other{}", type, 0, 1);
	}

	/**
	 * TODO Check if a variable declaration within a switch statement counts as a
	 * reference
	 */
	@Test
	public void testSwitchStatementVariableDeclaration_Dec_0_Ref_1() {
		configureParser("public class Other{ public void method() { int x = 1; switch(x){" + "case 1:" + "Foo foo;"
				+ "break;" + "case 2:" + "break;" + "default:" + "} }}", type, 0, 1);
	}

	/**
	 * TODO Check that calling a static field which returns and stores a value
	 * counts as a reference
	 */
	@Test
	public void testReturnStaticField_Dec_0_Ref_1() {
		configureParser("public class Other { Bar bar = Foo.staticField;}", type, 0, 1);
	}

	/**
	 * TODO Check that calling a static method which returns and stores a value
	 * counts as a reference
	 */
	@Test
	public void testReturnStaticMethod_Dec_0_Ref_1() {
		configureParser("public class Other { Bar bar = Foo.staticMethod();}", type, 0, 1);
	}

	/**
	 * TODO Check that retrieving and setting a static field counts as a reference
	 */
	@Test
	public void testSetStaticField_Dec_0_Ref_1() {
		configureParser("public class Other {Foo.staticField = 3;}", type, 0, 1);
	}

	/**
	 * TODO Check that calling a void static method with a parameter counts as a
	 * reference
	 */
	@Test
	public void testSetStaticMethod_Dec_0_Ref_1() {
		configureParser("public class Other {Foo.staticMethod(3);}", type, 0, 1);
	}

}
