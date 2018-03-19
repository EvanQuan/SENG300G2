package main.file;

import java.util.ArrayList;

/**
 * Retrieves Java contents from a location.
 *
 * @author Evan Quan
 * @version 1.0.0
 * @since March 18, 2018
 *
 */
public interface JavaRetriever {

	/**
	 * Get Java contents from a path
	 *
	 * @param path
	 *            where Java contents are located
	 * @return Java contents, null if invalid path
	 */
	public ArrayList<File> getJavaContents(String path);

}
