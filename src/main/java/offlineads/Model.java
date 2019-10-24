package offlineads;

import java.io.File;
import java.util.ArrayList;

public class Model {
	
	private File newImagesDirectory = new File("res/NewOfflineAds");
	private File[] newImageFiles = newImagesDirectory.listFiles();
	private ArrayList<Integer> sizeTest;
	
	/**
	 * Test main method for quick model testing.
	 * @param args
	 */
	public static void main(String[] args) {
		Model model = new Model();
		System.out.println(model.newImagesDirectory.toString());
		System.out.println(model.newImageFiles[0].toString());
		model.sizeTest = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			model.sizeTest.add(i);
		}
		System.out.println(model.sizeTest.size());

	}
	
	public Model() {
		
	}
	
	public File[] getNewOfflineAds() {
		return newImageFiles;
	}
	
}
