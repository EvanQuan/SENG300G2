package main;

/**
 * Represents a .java file. Contains file name, path, and source code.
 * 
 * @author Evan Quan
 * @version 1.0.1
 * @since March 16, 2018
 *
 */
public class JavaFile implements Comparable<JavaFile> {

	/**
	 * File extension of a Java file
	 */
	public static final String EXTENSION = ".java";

	private String name;
	private String path;
	private String source;

	/**
	 * Complete constructor for JavaFile
	 * 
	 * @param name
	 * @param path
	 * @param source
	 */
	public JavaFile(String name, String path, String source) {
		setName(name);
		setPath(path);
		setSource(source);
	}

	/**
	 * Clone constructor for JavaFile
	 * 
	 * @param file
	 *            to clone
	 */
	public JavaFile(JavaFile file) {
		setName(file.getName());
		setPath(file.getPath());
		setSource(file.getSource());
	}

	/**
	 * 
	 * @return name with file extension
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return name without file extension
	 */
	public String getSimpleName() {
		return name.substring(0, name.length() - EXTENSION.length());
	}

	/**
	 * 
	 * @return source code of Java file
	 */
	public String getSource() {
		return source;
	}

	/**
	 * 
	 * @return path of Java file
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 
	 * @param name
	 *            of Java file
	 */
	public void setName(String name) {
		if (!name.endsWith(EXTENSION)) {
			// Add extension if it doesn't have it
			name.concat(EXTENSION);
		}
		this.name = name;
	}

	/**
	 * 
	 * @param path
	 *            to Java file
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 
	 * @param source
	 *            of Java file
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * String representation of JavaFile
	 */
	@Override
	public String toString() {
		return "[class: JavaFile | name: " + name + " | path: " + path + "]\n" + source;
	}

	/**
	 * JavaFile objects are sorted alphabetically by file name. If file names are
	 * the same, sort by file path.
	 */
	@Override
	public int compareTo(JavaFile o) {
		int compareName = this.name.compareTo(o.getName());
		if (compareName == 0) {
			return this.path.compareTo(o.getPath());
		} else {
			return compareName;
		}
	}

}
