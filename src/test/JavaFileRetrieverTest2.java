package test;

import java.util.ArrayList;

import main.io.File;
import main.io.JavaFileRetriever;

/**
 * Checks that JavaFileRetriever works.
 *
 * @author Evan Quan
 * @version 1.0.0
 * @since March 18, 2018
 *
 */
public class JavaFileRetrieverTest2 {
	public static void main(String[] args) {
		JavaFileRetriever retriever = JavaFileRetriever.getInstance();

		ArrayList<File> javaFiles = retriever.getJavaContents(_TestSuite.JAVA_FILE_RETRIEVER_TEST_DIR);

		for (File javaFile : javaFiles) {
			System.out.println(javaFile);
		}

	}
}
