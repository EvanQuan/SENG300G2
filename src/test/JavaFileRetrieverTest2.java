package test;

import java.io.IOException;
import java.util.ArrayList;

import main.JavaFile;
import main.JavaFileRetriever;

public class JavaFileRetrieverTest2 {
	public static void main(String[] args) {
		JavaFileRetriever retriever = JavaFileRetriever.getInstance();

		try {
			ArrayList<JavaFile> javaFiles = retriever.getAllJavaFiles(_TestSuite.JAVA_FILE_RETRIEVER_TEST_DIR);

			for (JavaFile javaFile : javaFiles) {
				System.out.println(javaFile);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
