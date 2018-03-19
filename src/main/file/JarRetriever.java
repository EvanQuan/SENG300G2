package main.file;

import java.util.ArrayList;
import java.util.jar.JarFile;

/**
 * Detects Java .class files in a .jar file. Retrieves Java binary code from
 * .jar files.
 *
 * @author Evan Quan
 * @version 0.0.1
 * @since March 18, 2018
 *
 */
public class JarRetriever implements JavaRetriever {

	private static JarRetriever instance = new JarRetriever();

	/**
	 *
	 * @return instance of JarRetriever
	 */
	public static JarRetriever getInstance() {
		return instance;
	}

	/**
	 * Cannot be instantiated outside of class
	 */
	private JarRetriever() {
	}

	/**
	 * Finds all Java files in a given .jar file and returns its contents as
	 * {@link JarFile}s
	 *
	 * @param path
	 *            of .jar file of interest
	 * @return contents of all .class files
	 */
	@Override
	public ArrayList<File> getJavaContents(String path) {
		return null; // TODO
	}
}
