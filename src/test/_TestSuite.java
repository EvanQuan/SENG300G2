package test;

import java.io.File;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

/**
 * All Test suites go here.
 */
@Suite.SuiteClasses({ JavaFileTest.class, PassTest.class, TypeVisitorBuiltInTest.class, TypeVisitorInnerFooTest.class, TypeVisitorFooTest.class,
		TypeVisitorPrimitiveTest.class, TypeVisitorFooArrayTest.class, TypeVisitorPackageFooTest.class })

/**
 * Runs all test classes
 *
 * @author Evan Quan
 * @version 2.1.0
 * @since 23 March 2018
 *
 */
public class _TestSuite {
	/**
	 * Base directory is the root of the entire eclipse project (which is the PARENT
	 * of the src folder)
	 */
	public static final String BASEDIR = new File("").getAbsolutePath().concat("/");
	public static final String SOURCE_DIR = BASEDIR.concat("src/");
	public static final String TEST_DIR = SOURCE_DIR.concat("test/");
	public static final String BIN_DIR = BASEDIR.concat("bin/");
	/**
	 * All test directories are contained with test directory TEST_DIR
	 */
	public static final String JAVA_RETRIEVER_TEST_DIR = TEST_DIR.concat("javaRetriever/");
	public static final String JAR_RETRIEVER_TEST_DIR = TEST_DIR.concat("jarRetriever/");
	public static final String TYPE_FINDER_TEST_DIR = TEST_DIR.concat("typeFinderTestPackage/");
	
	// Group class versions
	public static final int TYPE_VISITOR_VERSION = TypeVisitorTest.MAIN;
}