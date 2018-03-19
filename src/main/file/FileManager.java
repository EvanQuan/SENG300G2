package main.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads and retrieves contents of files and directories
 *
 * @author Evan Quan
 * @version 1.0.0
 * @since March 18, 2018
 *
 */
public class FileManager {

	/**
	 * Reads the contents of a file with a given path and returns the contents of
	 * that file as a String.
	 *
	 * @param path
	 *            of file to read
	 * @return contents of file
	 * @throws IOException
	 *             if file is not able to be read
	 */
	public static String getFileContents(String path) throws IOException {
		FileReader fileReader = new FileReader(path);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String lineSeparator = System.getProperty("line.separator");

		// Keep reading line by line (and ending with a line separator)
		// Until no more lines can be read
		// Each line is appended to the output stringBuffer
		try {
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuffer.append(line);
				stringBuffer.append(lineSeparator);
				line = bufferedReader.readLine();
			}
			return stringBuffer.toString();
		} finally {
			bufferedReader.close();
		}
	}

	/**
	 *
	 * @param path
	 * @return true is path is a directory that exists, else false
	 */
	public static boolean isValidDirectory(String path) {
		return new File(path).isDirectory();
	}

	/**
	 *
	 * @param path
	 * @return true if path is a jar file that exists, else false
	 */
	public static boolean isValidJarFile(String path) {
		return new File(path).isFile() && path.endsWith(JavaFile.JAR_EXTENSION);
	}

	/**
	 *
	 * @param path
	 * @return true if path is a jar file that exists, else false
	 */
	public static boolean isValidJavaFile(String path) {
		return new File(path).isFile() && path.endsWith(JavaFile.EXTENSION);
	}
}
