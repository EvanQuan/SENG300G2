package main.io;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Detects Java source files in a directory. Reads source code from Java source
 * files. Retrieves source code from Java source files.
 *
 * @author Evan Quan
 * @version 1.0.0
 * @since March 18, 2018
 */
public class JavaFileRetriever implements JavaRetriever {

	private static JavaFileRetriever instance = new JavaFileRetriever();

	/**
	 *
	 * @return instance of JavaFileRetriever
	 */
	public static JavaFileRetriever getInstance() {
		return instance;
	}

	/**
	 * Cannot be instantiated outside of class
	 */
	private JavaFileRetriever() {
	}

	/**
	 * Recursively finds and all Java files and appends them to javaFiles ArrayList.
	 * Helper method to getAllJavaFiles.
	 *
	 * @param path
	 *            of directory of interest
	 * @param javaFiles
	 *            to append Java files to
	 * @return contents of all Java files as Strings
	 * @throws NotDirectoryException
	 *             if directory cannot be found
	 */
	private void getAllJavaFilesRecursively(String path, ArrayList<JavaFile> javaFiles) throws NotDirectoryException {
		// Get all the files in the directory
		java.io.File directory = new java.io.File(path);
		java.io.File[] files = directory.listFiles();
		// If this pathname does not denote a directory, then listFiles() returns null.
		if (files == null) {
			throw new NotDirectoryException(path);
		}

		// Iterate through all files in directory
		try {
			for (java.io.File file : files) {
				if (file.isDirectory()) {
					// Recursively search through inner directory
					String innerPath = file.getAbsolutePath();
					getAllJavaFilesRecursively(innerPath, javaFiles);
				} else if (file.isFile() && file.getName().endsWith(JavaFile.EXTENSION)) {
					// Only accept Java files
					String javaName = file.getName();
					String javaPath = file.getAbsolutePath();
					String javaSource = FileManager.getFileContents(javaPath);
					JavaFile javaFile = new JavaFile(javaName, javaPath, javaSource);
					javaFiles.add(javaFile);
				} // else ignore file
			}
		} catch (IOException e) {
			e.printStackTrace();
			// This is already dealt with with NotDirectoryException
		}
	}

	/**
	 * Finds all Java files in a given directory and sub-directories and returns
	 * their contents as {@link JavaFile}s
	 *
	 * @param path
	 *            of directory of interest
	 * @return contents of all Java files as Strings, null if path is not valid
	 * @throws NotDirectoryException
	 *             if directory cannot be found
	 */
	@Override
	public ArrayList<File> getJavaContents(String path) {
		ArrayList<JavaFile> javaFiles = new ArrayList<JavaFile>();
		try {
			getAllJavaFilesRecursively(path, javaFiles);
			// Sort Java files alphabetically by name and then path (for predictability)
			// Cast to IJavaFile
			Collections.sort(javaFiles);
			return new ArrayList<File>(javaFiles);
		} catch (NotDirectoryException e) {
			return null;
		}
	}

}
