package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.TypeFinder;
import main.file.FileManager;

/**
 * JUnit 4 Tests for {@link TypeFinder} class
 *
 * @author Evan Quan
 * @version 2.0.0
 * @since 25 March 2018
 *
 */
public class TypeFinderTest {

	/**
	 * Contents of standard output
	 */
	private static ByteArrayOutputStream outContent;
	private static ByteArrayOutputStream errContent;

	/**
	 * Restore standard output and error to original streams
	 */
	@After
	public void restoreStream() {
		System.setOut(System.out);
		System.setErr(System.err);
	}

	/**
	 * outContent tracks standard output
	 */
	@Before
	public void setUpStream() {
		outContent = new ByteArrayOutputStream();
		errContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	/**
	 * Check that inputting an invalid directory prompts the user with an invalid
	 * path error message to screen
	 */
	@Test
	public void testInvalidDirectory() {
		String invalidDirectory = "";
		String[] args = { invalidDirectory };
		TypeFinder.main(args);
		String expected = TypeFinder.INVALID_PATH_ERROR_MESSAGE + FileManager.lineSeparator;
		String results = errContent.toString();
		assertEquals(expected, results);
	}
	
	/**
	 * Check that inputting an invalid jar prompts the user with an invalid  path error message to screen
	 */
	@Test
	public void testInvalidJar() {
		String invalidJar = _TestSuite.TYPE_FINDER_TEST_DIR.concat("jarThatDoesNotExist.jar");
		String[] args = { invalidJar };
		TypeFinder.main(args);
		String expected = TypeFinder.INVALID_PATH_ERROR_MESSAGE + FileManager.lineSeparator;
		String results = errContent.toString();
		assertEquals(expected, results);
	}

	/**
	 * Check that inputting no command line arguments returns a prompt to the user
	 * explaining how to use the program
	 */
	@Test
	public void test_ArgumentCount_0_InvalidInput() {
		String[] args = {};
		TypeFinder.main(args);
		String expected = TypeFinder.INVALID_ARGUMENT_ERROR_MESSAGE + FileManager.lineSeparator;
		String results = errContent.toString();
		assertEquals(expected, results);
	}

	/**
	 * Check that inputting 2 command line arguments returns a prompt to the user
	 * explaining how to use the program
	 */
	@Test
	public void test_ArgumentCount_2_InvalidInput() {
		String[] args = { "", "" };
		TypeFinder.main(args);
		String expected = TypeFinder.INVALID_ARGUMENT_ERROR_MESSAGE + FileManager.lineSeparator;
		String results = errContent.toString();
		assertEquals(expected, results);
	}

	/**
	 * Check that the correct number of declarations and references can be found
	 * from the test.testPackage directory.
	 */
	@Test
	public void testTestPackageDirectory() {
		fail();
//		String[] args = { TestSuite.TYPE_FINDER_TEST_DIR, "test.typeFinderTestPackage.Foo" };
//		int expectedDec = 1;
//		int expectedRef = 12;
//		String expectedOut = "test.typeFinderTestPackage.Foo. Declarations found: " + expectedDec
//				+ "; references found: " + expectedRef + "." + FileManager.lineSeparator;
//		String expectedErr = "";
//		testOutput(args, expectedOut, expectedErr, expectedDec, expectedRef);
	}
}
