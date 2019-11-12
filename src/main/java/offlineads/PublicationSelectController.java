package offlineads;

import guijavafx.App;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PublicationSelectController implements Initializable {
		
	private boolean isCreated;
	private Stage primaryStage;
	private Scene jobSelectScene;
	@FXML private BorderPane root;
	private ScrollPane spPubScroller;
	private HBox hBoxPubImages;
	private ObservableList<ImageView> olPubImages = FXCollections.observableArrayList();
	private int currentlySelectedImage;
	ColorAdjust gsImage = new ColorAdjust();
	private App app;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gsImage.setSaturation(-1);
		spPubScroller = new ScrollPane();
		spPubScroller.setFitToHeight(true);
		spPubScroller.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		spPubScroller.setVbarPolicy(ScrollBarPolicy.NEVER);
		hBoxPubImages = new HBox(16);
	    hBoxPubImages.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
		hBoxPubImages.setAlignment(Pos.CENTER);
	}
	
	public void createPublicationPreviews() {
		ObservableList<Image> frontPageImages = PDFHandler.getFrontPageImages(app.getModel().getNewPublicationsFiles());
		for (int i = 0; i < frontPageImages.size(); i++) {
			int imgVwPubImageNum = i;
			ImageView imgVwPubImage = new ImageView(frontPageImages.get(i));
			imgVwPubImage.setId("imgVwPubImage" + Integer.toString(i));
			imgVwPubImage.setEffect(gsImage);
			imgVwPubImage.setOnMouseClicked(e -> imgVwPubImage_Clicked(e, imgVwPubImageNum));
			imgVwPubImage.setPreserveRatio(true);
			imgVwPubImage.setFitWidth(320);
			olPubImages.add(imgVwPubImage);
			hBoxPubImages.getChildren().add(imgVwPubImage);
		}
		spPubScroller.setContent(hBoxPubImages);
		root.setCenter(spPubScroller);
	}
	
	/**
	 * Notify observers that creation is finished.
	 */
	public void onCreated() {
		isCreated = true;
	}
	
	public void imgVwPubImage_Clicked(MouseEvent e, int i) {
		if (e.getClickCount() == 2) {
			// double clicked
			// open?
			e.consume();
		} else {
			// single click
			olPubImages.get(currentlySelectedImage).setEffect(gsImage);
			olPubImages.get(i).setEffect(null);
			currentlySelectedImage = i;
		}
	}
	
	@FXML protected void btnBackButton_Click() {
		primaryStage.setScene(jobSelectScene);
	}
	
	public ObservableList<ImageView> getOlPubImages() {
		return olPubImages;
	}
	
	public BorderPane getRoot() {
		return root;
	}
	
	public void setApp(App app) {
		this.app = app;
	}
	
	public boolean getIsCreated() {
		return isCreated;
	}
	
}
