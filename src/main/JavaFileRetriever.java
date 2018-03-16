package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Detects Java files in a directory. Reads source code from Java files.
 * Retrieves source code from Java files.
 *
 * @author Evan Quan
 * @version 1.0.0
 * @since March 16, 2018
 */
public class JavaFileRetriever {

	private static JavaFileRetriever retriever = new JavaFileRetriever();

	/**
	 * Cannot be instantiated outside of
	 */
	private JavaFileRetriever() {
	}

	/**
	 * 
	 * @return instance of JavaFileRetriever
	 */
	public static JavaFileRetriever getInstance() {
		return retriever;
	}

	/**
	 * Retrieves all Java files in a given directory and sub-directories and returns
	 * their contents as Strings in an ArrayList
	 *
	 * @param path
	 *            of directory of interest
	 * @return contents of all Java files as Strings
	 * @throws DirectoryNotFoundException
	 *             if directory cannot be found
	 * @throws IOException
	 *             if a Java file in the directory is not able to be read.
	 *             Hypothetically, this exception should never run as Java files
	 *             that do not exist or cannot be accessed are ignored.
	 */
	public ArrayList<JavaFile> getAllJavaFiles(String path) throws DirectoryNotEmptyException, IOException {
		ArrayList<JavaFile> javaFiles = new ArrayList<JavaFile>();
		getAllJavaFilesRecursively(path, javaFiles);
		// Sort Java files alphabetically by name and then path (for predictability)
		Collections.sort(javaFiles);
		return javaFiles;
	}

	/**
	 * Recursively finds and all Java files and appends them to javaFiles ArrayList
	 * 
	 * @param path
	 *            of directory of interest
	 * @param javaFiles
	 *            to append Java files to
	 * @return contents of all Java files as Strings
	 * @throws DirectoryNotFoundException
	 *             if directory cannot be found
	 * @throws IOException
	 *             if a Java file in the directory is not able to be read.
	 *             Hypothetically, this exception should never run as Java files
	 *             that do not exist or cannot be accessed are ignored.
	 */
	private void getAllJavaFilesRecursively(String path, ArrayList<JavaFile> javaFiles)
			throws DirectoryNotEmptyException, IOException {
		// Get all the files in the directory
		File directory = new File(path);
		File[] files = directory.listFiles();
		// If this pathname does not denote a directory, then listFiles() returns null.
		if (files == null) {
			throw new NotDirectoryException(path);
		}

		// Iterate through all files in directory
		for (File file : files) {
			if (file.isDirectory()) {
				// Recursively search through inner directory
				String innerPath = file.getAbsolutePath();
				getAllJavaFilesRecursively(innerPath, javaFiles);
			} else if (file.isFile() && file.getName().endsWith(JavaFile.EXTENSION)) {
				// Only accept Java files
				String javaName = file.getName();
				String javaPath = file.getAbsolutePath();
				String javaSource = getFileContents(javaPath);
				JavaFile javaFile = new JavaFile(javaName, javaPath, javaSource);
				javaFiles.add(javaFile);
			} // else ignore file
		}
	}

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
	private String getFileContents(String path) throws IOException {
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

}
