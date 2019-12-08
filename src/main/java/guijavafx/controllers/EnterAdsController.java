package guijavafx.controllers;

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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import offlineads.FileHandler;
import offlineads.ImageProcessor;
import offlineads.PDFHandler;
import offlineads.TextProcessor;

public class EnterAdsController extends Controller implements Initializable {
	
	private boolean isCreated;
	private int cutAdsSceneID;
	@FXML private BorderPane root;
	BorderPane leftBorderPane;
	BorderPane rightBorderPane;
	private ScrollPane spAdScroller;
	private VBox vBoxAdImages;
	private ObservableList<ImageView> oladImages = FXCollections.observableArrayList();
	private int currentlySelectedAd;
	private App app;
	
	File imageFile;
	ZoomerPane zoomerPane;
	
	TextField txtFldHeadline;
	TextArea txtAreaAdText;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// left bar
		
		leftBorderPane = new BorderPane();
		
		Text title = new Text("Enter Ads");
		leftBorderPane.setTop(title);
		
		spAdScroller = new ScrollPane();
		spAdScroller.setFitToHeight(true);
		spAdScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		spAdScroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		vBoxAdImages = new VBox(16);
	    vBoxAdImages.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
		vBoxAdImages.setAlignment(Pos.CENTER);
				
		Button btnBackButton = new Button("Back");
		btnBackButton.setMinSize(128, 64);
		btnBackButton.setOnAction(e -> btnBackButton_Click());
		
		leftBorderPane.setBottom(btnBackButton);
		
		// right bar
		
		rightBorderPane = new BorderPane();
		
		GridPane rgp = new GridPane();
		ColumnConstraints cc = new ColumnConstraints();
		cc.setHalignment(HPos.CENTER);
		
		Label lblHeadline = new Label("Headline");
		txtFldHeadline = new TextField();
		txtFldHeadline.setPromptText("Enter the headline");
		
		Label lblAdText = new Label("Ad Text");
		txtAreaAdText = new TextArea();
		txtAreaAdText.setPromptText("Enter all of the ad text");
		
		rgp.add(lblHeadline, 0, 0);
		rgp.add(txtFldHeadline, 0, 1);
		rgp.add(lblAdText, 0, 2);
		rgp.add(txtAreaAdText, 0, 3);
		
		rightBorderPane.setCenter(rgp);

		Button btnCutSelectedAdsButton = new Button("Enter Current Ad");
		btnCutSelectedAdsButton.setMinSize(128, 64);
		btnCutSelectedAdsButton.setOnAction(e -> btnCutSelectedAds_Click());
		
		rightBorderPane.setBottom(btnCutSelectedAdsButton);
		
		root.setRight(rightBorderPane);
	}
	
	public void createAdPreviews() {
		File[] ads = app.getModel().getFilesNewAdsImages();
		for (int i = 0; i < ads.length - 1; i++) {
			int imgVwAdImageNum = i;
			ImageView imgVwAdImage = new ImageView(new Image("file:" + ads[i].toString(), 128, 128, true, false));
			imgVwAdImage.setOnMouseClicked(e -> imgVwadImage_Clicked(e, imgVwAdImageNum));
			oladImages.add(imgVwAdImage);
			vBoxAdImages.getChildren().add(imgVwAdImage);
			imgVwAdImage = new ImageView();
		}
		spAdScroller.setContent(vBoxAdImages);
		leftBorderPane.setCenter(spAdScroller);
		root.setLeft(leftBorderPane);
	}
	
	public void setCenterAd(int adNum) {
		imageFile = app.getModel().getFilesNewAdsImages()[adNum];
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
	
	public void imgVwadImage_Clicked(MouseEvent e, int adNum) {
		currentlySelectedAd = adNum;
		setCenterAd(adNum);
	}
	
	@FXML protected void btnBackButton_Click() {
		app.getPrimaryStage().setScene(app.getJobSelectScene());
		for (File scratchFile : app.getModel().getDirScratch().listFiles()) {
			scratchFile.delete();
		}
	}
	
	public void btnCutSelectedAds_Click() {
		zoomerPane.normaliseSelections();
		// do ocr on scratch files
		StringBuilder sb = new StringBuilder();
		for (File snippet : app.getModel().getDirScratch().listFiles()) {
			sb.append(TextProcessor.doTess4JOCR(snippet) + "\n\n");
		}
		txtAreaAdText.appendText(sb.toString());
	}
	
	public void setSelections(ArrayList<Rectangle> selections) {
		for (Rectangle selection: selections) {
			File imageFile = app.getModel().getFilesNewAdsImages()[currentlySelectedAd];
			System.out.println(imageFile.toString());
			BufferedImage bi = PDFHandler.imageFileToBufferedImage(imageFile);
			String[] outDirStrings = {app.getModel().getDirScratch().toString(), app.getModel().getFilesNewAdsImages()[currentlySelectedAd].getName()};
			File outFile = new File(FileHandler.makeDirFromStrings(outDirStrings) + UUID.randomUUID());
			ImageProcessor.writeSubImage(bi, selection.getBoundsInLocal(), "jpeg", outFile);
		}
	}
}
