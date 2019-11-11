package offlineads;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class Model {
	
	private File newPdfFilesDirectory = new File("res/NewPublications");
	private File[] newPdfFiles = newPdfFilesDirectory.listFiles();
	private File newImagesDirectory = new File("res/NewOfflineAds");
	private File[] newImageFiles = newImagesDirectory.listFiles();
	
	/**
	 * Test main method for quick model testing.
	 * @param args
	 */
	public static void main(String[] args) {
		Model model = new Model();
		model.preparePdfs();
	}
	
	public Model() {

	}
	
	/**
	 * Load all pdf files and convert to jpegs.
	 */
	public synchronized void preparePdfs() {
		for (File pdfFile : newPdfFiles) {
			// check no project folder exists for current pdf
			if (new File(FileHandler.dropExtension(pdfFile)).exists()) {
				System.out.println("File already converted: " + pdfFile.toString());
				// skip converting already converted pdf
				continue;
			} else {
				PDFHandler.convertDocPagesToJPEG(pdfFile);
			}
		}
	}
	
	public File[] getNewPdfFiles() {
		return newPdfFiles;
	}
	
	public File[] getNewOfflineAds() {
		return newImageFiles;
	}
	
}
