package offlineads;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class Model {
	
	private File resources = new File("res");
	private File newPublicationsFilesDirectory = new File(resources + "/NewPublications");
	private File[] newPublicationsFiles = newPublicationsFilesDirectory.listFiles();
	private File processedPublicationsDirectory = new File(resources + "/ProcessedPublications");
	private File[] processedPublicationsFiles = processedPublicationsDirectory.listFiles();
	private File newAdsImagesDirectory = new File(resources + "/NewAdsImages");
	private File[] newAdsImagesFiles = newAdsImagesDirectory.listFiles();
	
	/**
	 * Test main method for quick model testing.
	 * @param args
	 */
	public static void main(String[] args) {
		Model model = new Model();
		model.initialiseResources();
		model.preparePdfs();
	}
	
	public Model() {

	}
	
	/**
	 * If folders don't exist, create them, e.g. first run
	 */
	public void initialiseResources() {
		if (!resources.exists()) {new File("res").mkdir();}
		if (!newPublicationsFilesDirectory.exists()) {FileHandler.makeDirFromFile(newPublicationsFilesDirectory);}
		if (!processedPublicationsDirectory.exists()) {FileHandler.makeDirFromFile(processedPublicationsDirectory);}
		if (!newAdsImagesDirectory.exists()) {FileHandler.makeDirFromFile(newAdsImagesDirectory);}
	}
	
	/**
	 * convert new PDF files to JPEGs and store in ProcessedPublications directory.
	 */
	public synchronized void preparePdfs() {
		for (File file : newPublicationsFiles) {
			PDFHandler.convertDocPagesToJPEG(file, processedPublicationsDirectory);
		}
	}
	
	public File[] getNewPublicationsFiles() {
		return newPublicationsFiles;
	}
	
	public File[] getProcessedPublicationsFiles() {
		return processedPublicationsFiles;
	}
	
	public File[] getNewAdsImages() {
		return newAdsImagesFiles;
	}
	
}
