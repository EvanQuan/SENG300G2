package test;

import java.util.ArrayList;

import main.file.File;
import main.file.JavaRetriever;

/**
 * Checks that JavaRetriever works.
 *
 * @author Evan Quan
 * @version 1.1.0
 * @since 19 March 2018
 *
 */
public class JavaRetrieverTest2 {
	public static void main(String[] args) {
		ArrayList<File> javaFiles = JavaRetriever.getJavaContents(_TestSuite.JAVA_RETRIEVER_TEST_DIR);

		for (File javaFile : javaFiles) {
			System.out.println(javaFile);
		}

	}
}
