package offlineads;

import java.io.File;

public class FileHandler {
	
	/**
	 * Removes the file extension from the fileName String.
	 * @param fileName The filename with it's file extension.
	 * @return fileName The fileName without an extension.
	 */
	public static String dropExtension(File fileName) {
		return fileName.getName().split("\\.")[0];
	}
	
	public static boolean hasValidExtension(File file, String extension) {
		if (file.getName().split("\\.")[file.getName().split("\\.").length - 1]
				.equals(extension)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void makeDirFromFile(File file) {
		try {
			file.mkdir();
			System.out.println("Created directory: " + file.toString());
		} catch (SecurityException e) {
			
		}
	}
	
	/**
	 * This should be tested to ensure a valid folder name is passed.
	 * @param fileName The name to be given to the corresponding folder.
	 */
	public static String makeDirFromStrings(String[] dirStrings) {
		String dirNameString = "";
		try {
			StringBuilder dirString = new StringBuilder();
			for (int i = 0; i < dirStrings.length - 1; i++) {
				if (i == dirStrings.length - 1) {
					dirString.append(dirStrings[i]);
				}
				dirString.append(dirStrings[i] + "/");

			}
			dirNameString = dirString.toString();
			new File(dirNameString).mkdir();
		} catch (SecurityException e) {
			System.out.println(e.getMessage());
			System.out.println("Requires security protocol.");
		}
		return dirNameString;
	}
	
}
