package main.file;


import java.util.jar.JarInputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.io.*;
import java.util.ArrayList;


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
	 * @return contents of all .java files within the given jar
	 */
	@Override
	public ArrayList<File> getJavaContents(String path) {
		
		ArrayList<File> filesInJar = new ArrayList<File>();
		JarInputStream jarStream;
		JarFile jar;
		JarEntry currFile;
		
		try {
			
			jarStream = new JarInputStream(new FileInputStream(path));
			jar = new JarFile(path);
			
			while((currFile = jarStream.getNextJarEntry()) != null) {
				
				if(currFile.getName().endsWith(".java")){
					
					String fileContents = FileManager.getFileContents(jar, currFile);
					String fileName = currFile.getName();
					String filePath = path;
					JavaFile javaFile = new JavaFile(fileName, filePath, fileContents);
					filesInJar.add(javaFile);
				}
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return filesInJar;	
	}
}
