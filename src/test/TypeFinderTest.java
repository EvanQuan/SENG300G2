package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
	
	private static String OUTPUT_FILE = "Output.txt";
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
//	@Test
//	public void testInvalidDirectory() {
//		String invalidDirectory = "";
//		String[] args = { invalidDirectory };
//		TypeFinder.main(args);
//		String expected = TypeFinder.INVALID_PATH_ERROR_MESSAGE + FileManager.lineSeparator;
//		String results = errContent.toString();
//		assertEquals(expected, results);
//	}
	
	/**
	 * Check that inputting an invalid jar prompts the user with an invalid  path error message to screen
	 */
//	@Test
//	public void testInvalidJar() {
//		String invalidJar = _TestSuite.TYPE_FINDER_TEST_DIR.concat("jarThatDoesNotExist.jar");
//		String[] args = { invalidJar };
//		TypeFinder.main(args);
//		String expected = TypeFinder.INVALID_PATH_ERROR_MESSAGE + FileManager.lineSeparator;
//		String results = errContent.toString();
//		assertEquals(expected, results);
//	}

	/**
	 * Check that inputting no command line arguments returns a prompt to the user
	 * explaining how to use the program
	 */
//	@Test
//	public void test_ArgumentCount_0_InvalidInput() {
//		String[] args = {};
//		TypeFinder.main(args);
//		String expected = TypeFinder.INVALID_ARGUMENT_ERROR_MESSAGE + FileManager.lineSeparator;
//		String results = errContent.toString();
//		assertEquals(expected, results);
//	}

	/**
	 * Check that inputting 2 command line arguments returns a prompt to the user
	 * explaining how to use the program
	 */
//	@Test
//	public void test_ArgumentCount_2_InvalidInput() {
//		String[] args = { "", "" };
//		TypeFinder.main(args);
//		String expected = TypeFinder.INVALID_ARGUMENT_ERROR_MESSAGE + FileManager.lineSeparator;
//		String results = errContent.toString();
//		assertEquals(expected, results);
//	}

	/**
	 * Check that the correct number of declarations and references can be found
	 * from the test.testPackage directory.
	 */
	@Test
	public void test_Directory_typeFinder_SENG300W18Iter1() {
		String directory = _TestSuite.TYPE_FINDER_TEST_DIR.concat("SENG300W18Iter1/");
		testOutput(directory);
	}
	
	/**
	 * Tests that TypeFinder finds and output the correct declaration and reference counts of the given input path.
	 * Assumes that path exists and is valid.
	 * Output is checked with Output.txt file in the input directory.
	 * @param path of directory or .jar
	 * @throws IOException if path is invalid
	 */
	public void testOutput(String path) {
		String[] args = {path};
		TypeFinder.main(args);
		String expectedErr = "";
		// Check that there is no error
		String actualErr = errContent.toString();
		assertEquals(expectedErr, actualErr);
		// Check standard output matches Output.txt file
		String expectedOut;
		try {
			expectedOut = FileManager.getFileContents(path.concat(OUTPUT_FILE));
			String actualOut = outContent.toString();
			assertEquals(expectedOut, actualOut);
		} catch (IOException e) {
			fail("Invalid path");
		}
	}
}
