package guijavafx;

import java.awt.Toolkit;
import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import offlineads.Controller;
import offlineads.Model;

public class View extends Application {
	
	Model model = new Model();
	
	private ImageView imgVwCenterImage;
	private ImageViewPane imgVwPane;
	private ObservableList<Button> btnThumbnails;
	
	public static void main(String[] args) {
		
		System.out.println("Starting JavaFX");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// create sidebar
		int intButtonCounter = 0;
		btnThumbnails = FXCollections.observableArrayList();
		for (File imageFile : model.getNewOfflineAds()) {
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
		imgVwCenterImage = new ImageView(new Image("file:" + model.getNewOfflineAds()[0].toString()));	

		imgVwCenterImage.setPreserveRatio(true);
		imgVwCenterImage.setFitHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 64);
		imgVwCenterImage.setFitWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 192);
		
		imgVwPane = new ImageViewPane(imgVwCenterImage);
		
		// create a new border pane and the panels
		BorderPane mainPane = new BorderPane();
		mainPane.setLeft(vBoxSideBar);
		mainPane.setCenter(imgVwPane);
		
		// create the scene
		Scene scene = new Scene(mainPane);
		
		// finalise and show the stage
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.setTitle("My First JavaFX Application");
		primaryStage.show();
		
	}
	
}
