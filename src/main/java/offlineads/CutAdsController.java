package offlineads;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

import guijavafx.App;
import guijavafx.ZoomerPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CutAdsController extends Controller implements Initializable {
	
	private boolean isCreated;
	private int cutAdsSceneID;
	@FXML private BorderPane root;
	BorderPane leftBorderPane;
	BorderPane rightBorderPane;
	private ScrollPane spPageScroller;
	private VBox vBoxPageImages;
	private ObservableList<ImageView> olPageImages = FXCollections.observableArrayList();
	private int currentlySelectedPage;
	private App app;
	
	File imageFile;
	ZoomerPane zoomerPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// left bar
		
		leftBorderPane = new BorderPane();
		
		Text title = new Text("Cut Ads");
		leftBorderPane.setTop(title);
		
		spPageScroller = new ScrollPane();
		spPageScroller.setFitToHeight(true);
		spPageScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		spPageScroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		vBoxPageImages = new VBox(16);
	    vBoxPageImages.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
		vBoxPageImages.setAlignment(Pos.CENTER);
				
		Button btnBackButton = new Button("Back");
		btnBackButton.setMinSize(128, 64);
		btnBackButton.setOnAction(e -> btnBackButton_Click());
		
		leftBorderPane.setBottom(btnBackButton);
		
		// right bar
		
		rightBorderPane = new BorderPane();
		
		Button btnCutSelectedAdsButton = new Button("Cut Selected Ads");
		btnCutSelectedAdsButton.setMinSize(128, 64);
		btnCutSelectedAdsButton.setOnAction(e -> btnCutSelectedAds_Click());
		
		rightBorderPane.setBottom(btnCutSelectedAdsButton);
		
		root.setRight(rightBorderPane);
	}
	
	public void createPagePreviews() {
		File[] pages = app.getModel().getFilesNewPubsImages()[cutAdsSceneID].listFiles();
		for (int i = 0; i < pages.length - 1; i++) {
			int imgVwPageImageNum = i;
			ImageView imgVwPageImage = new ImageView(new Image("file:" + pages[i].toString(), 128, 128, true, false));
			imgVwPageImage.setOnMouseClicked(e -> imgVwPageImage_Clicked(e, imgVwPageImageNum));
			olPageImages.add(imgVwPageImage);
			vBoxPageImages.getChildren().add(imgVwPageImage);
			imgVwPageImage = new ImageView();
		}
		spPageScroller.setContent(vBoxPageImages);
		leftBorderPane.setCenter(spPageScroller);
		root.setLeft(leftBorderPane);
	}
	
	public void setCenterPage(int pageNum) {
		imageFile = app.getModel().getFilesNewPubsImages()[cutAdsSceneID].listFiles()[pageNum];
		zoomerPane = new ZoomerPane(this, cutAdsSceneID, imageFile, app.getPrimaryScreenBounds());
		root.setCenter(zoomerPane);
	}
	
	/**
	 * Notify observers that creation is finished.
	 */
	public void onCreated() {
		isCreated = true;
	}
	
	public void setCutAdsScenesID(int id) {
		this.cutAdsSceneID = id;
	}
	
	public BorderPane getRoot() {
		return root;
	}
	
	public boolean getIsCreated() {
		return isCreated;
	}
	
	public void setApp(App app) {
		this.app = app;
	}
	
	public void imgVwPageImage_Clicked(MouseEvent e, int pageNum) {
		currentlySelectedPage = pageNum;
		setCenterPage(pageNum);
	}
	
	@FXML protected void btnBackButton_Click() {
		app.getPrimaryStage().setScene(app.getPublicationSelectScene());
	}
	
	public void btnCutSelectedAds_Click() {
		zoomerPane.normaliseSelections();
	}
	
	public void setSelections(ArrayList<Rectangle> selections) {
		for (Rectangle selection: selections) {
			File imageFile = app.getModel().getFilesNewPubsImages()[cutAdsSceneID].listFiles()[currentlySelectedPage];
			System.out.println(imageFile.toString());
			BufferedImage bi = PDFHandler.imageFileToBufferedImage(imageFile);
			String[] outDirStrings = {app.getModel().getDirNewAdsImages().toString(), app.getModel().getFilesNewPubsImages()[cutAdsSceneID].listFiles()[currentlySelectedPage].getName()};
			File outFile = new File(FileHandler.makeDirFromStrings(outDirStrings) + UUID.randomUUID());
			ImageProcessor.writeSubImage(bi, selection.getBoundsInLocal(), "jpeg", outFile);
		}
	}
}
