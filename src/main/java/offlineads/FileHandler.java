package offlineads;

import java.io.File;

public class FileHandler {
	
	/**
	 * Removes the file extension from the fileName String.
	 * @param fileName The filename with it's file extension.
	 * @return fileName The fileName without an extension.
	 */
	public static String dropExtension(File fileName) {
		return fileName.toString().split("\\.")[0];
	}
	
	public static boolean hasValidExtension(File file, String extension) {
		if (file.toString().split("\\.")[file.toString().split("\\.").length - 1]
				.equals(extension)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This should be tested to ensure a valid folder name is passed.
	 * @param fileName The name to be given to the corresponding folder.
	 */
	public static void makeDirFromFile(File fileName) {
		// drop the file extension
		String strFileNameNoEx = dropExtension(fileName);
		try {
			// If the folder doesn't already exist, it will be created.
			new File(strFileNameNoEx).mkdir();
		} catch (SecurityException e) {
			System.out.println(e.getMessage());
			System.out.println("Requires security protocol.");
		}
	}
	
}
