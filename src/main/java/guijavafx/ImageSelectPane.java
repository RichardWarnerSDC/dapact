package guijavafx;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ImageSelectPane extends BorderPane {
	
	private ImageView imgVwCenterImage;
	private ImageViewPane imgVwPane;
	private ObservableList<Button> btnThumbnails;

	public ImageSelectPane() {
		
		// create sidebar
		
		VBox vBoxThumbnails = new VBox(10);
		vBoxThumbnails.setAlignment(Pos.BASELINE_CENTER);
		vBoxThumbnails.getChildren().addAll(btnThumbnails);
		
		ScrollPane sbSideScroller = new ScrollPane(vBoxThumbnails);
		
		Button btnProcessSelectedImage = new Button("Process Selected Image");
		btnProcessSelectedImage.setMinSize(192, 64);
		btnProcessSelectedImage.setOnAction( e -> {/** controller.btnProcessSelectedAd_Click() **/});
		
		VBox vBoxSideBar = new VBox();
		vBoxSideBar.getChildren().addAll(sbSideScroller, btnProcessSelectedImage);
		
		// load the image to be displayed in the center of the main pane
		imgVwCenterImage = new ImageView();

		imgVwPane = new ImageViewPane(imgVwCenterImage);
		
		// create a new border pane and the panels
		this.setLeft(vBoxSideBar);
		this.setCenter(imgVwPane);
		
	}
	
	public void populateThumbnailPane(File[] imageFiles) {
		int intButtonCounter = 0;
		btnThumbnails = FXCollections.observableArrayList();
		for (File imageFile : imageFiles) {
			ImageView imgVwThumbnail = new ImageView(new Image("file:" + imageFile.toString()));
			imgVwThumbnail.setPreserveRatio(true);
			imgVwThumbnail.setFitHeight(64);
			imgVwThumbnail.setFitWidth(192);
			Button btnThumbnail = new Button();
			btnThumbnail.setId(Integer.toString(intButtonCounter));
			btnThumbnail.setMinSize(192, 64);
			btnThumbnail.setMaxSize(192, 64);
			btnThumbnail.setGraphic(imgVwThumbnail);
			btnThumbnail.setOnAction(e -> 
				{
					System.out.println(btnThumbnail.getId());
					imgVwCenterImage.setImage(new Image("file:" + imageFile.toString()));
				}
			);
			btnThumbnails.add(btnThumbnail);
			intButtonCounter++;
		}
	}
	
	public void setImgVwCenterImage(String fileName) {
		imgVwCenterImage = new ImageView(new Image("file:" + fileName));	
	}
	
}
