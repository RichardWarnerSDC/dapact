package guijavafx.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import data.publication.Publication;
import data.publication.Publication.Location;
import guijavafx.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class PublicationSelectController extends Controller implements Initializable {
		
	FlowPane flow;
	private int currentlySelectedPublication = 0;
	@FXML private BorderPane root;
	@FXML private FlowPane fpPubInfo;
	@FXML Button btnBackButton;
	private ScrollPane spPubScroller;
	@FXML private HBox hBoxPubImages;
	private boolean isCreated;
	private ObservableList<ImageView> olPubImages = FXCollections.observableArrayList();
	ColorAdjust grayscale = new ColorAdjust();
	private App app;
	
	@FXML private Label lblSelectedFileOut;
	@FXML private ComboBox<String> cbxName;
	@FXML private ComboBox<String> cbxPublisher;
	@FXML private ComboBox<String> cbxLanguage;
	@FXML private ComboBox<String> cbxCountry;
	@FXML private ComboBox<String> cbxCity;
	@FXML private TextField txfFldUrl;
	@FXML private Label lblPagesOut;
	@FXML private ComboBox<String> cbxFormat;
	@FXML private ComboBox<String> cbxIsAlternateFormatAvailable;
	@FXML private Label lblPublishedOnOut;
	@FXML private Label lblTaggedAdsOut;
	@FXML private Label lblCutAdsOut;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		grayscale.setSaturation(-1);
		
		spPubScroller = new ScrollPane();
		spPubScroller.setFitToHeight(true);
		spPubScroller.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		spPubScroller.setVbarPolicy(ScrollBarPolicy.NEVER);
	
	}
	
	/**
	 *  Populate pubInfo with the default selected publication
	 */
	public void populatePubInfo() {
		
		// populate choices
		Publication currentPub = app.getModel().getPubsData()[0];
		lblSelectedFileOut.setText(currentPub.getFileName());
		if (!cbxName.getItems().contains(currentPub.getName())) {
			cbxName.getItems().add(currentPub.getName());}
		if (!cbxPublisher.getItems().contains(currentPub.getPublisher())) {
			cbxPublisher.getItems().add(currentPub.getPublisher());}
		if (!cbxLanguage.getItems().contains(currentPub.getLanguage())) {
			cbxLanguage.getItems().add(currentPub.getLanguage());
		}
		
		Location pubLocation = currentPub.getLocation();
		if (!cbxCountry.getItems().contains(pubLocation.getCountry())) {
			cbxCountry.getItems().add(pubLocation.getCountry());}
		if (!cbxCity.getItems().contains(pubLocation.getCity())) {
			cbxCity.getItems().add(pubLocation.getCity());}
		
		lblPagesOut.setText(Integer.toString(currentPub.getPages()));
		if (!cbxFormat.getItems().contains(currentPub.getFormat())) {
			cbxFormat.getItems().add(currentPub.getFormat());
		}
		if (!cbxIsAlternateFormatAvailable.getItems().contains(currentPub.getIsAlternativeFormatAvailable())) {
			cbxIsAlternateFormatAvailable.getItems().add(currentPub.getIsAlternativeFormatAvailable());}
		lblPublishedOnOut.setText(currentPub.getPublishedOn());
		
		// set default selected values
		cbxName.setValue(cbxName.getItems().get(0));
		cbxPublisher.setValue(cbxPublisher.getItems().get(0));
		cbxLanguage.setValue(cbxLanguage.getItems().get(0));
		cbxCountry.setValue(cbxCountry.getItems().get(0));
		cbxCity.setValue(cbxCity.getItems().get(0));
		cbxFormat.setValue(cbxFormat.getItems().get(0));
		cbxIsAlternateFormatAvailable.setValue(cbxIsAlternateFormatAvailable.getItems().get(0));
		
	}
	
	public void createPublicationPreviews() {
		File[] newPubsImages = app.getModel().getFilesNewPubsImages();
		for (int i = 0; i <= newPubsImages.length - 1; i++) {
			int pubNum = i;
			File convertedPub = newPubsImages[i];
			ImageView imgVwPubImage = new ImageView(new Image("file:" + convertedPub.listFiles()[0], 480, 480, true, false));
			imgVwPubImage.setEffect(grayscale);
			imgVwPubImage.setOnMouseClicked(e -> imgVwPubImage_Clicked(e, pubNum));
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
//			publishSetInfo();
			app.getPrimaryStage().setScene(app.getCutAdsScenes()[i]);
			e.consume();
		} else {
			// single click
			olPubImages.get(currentlySelectedPublication).setEffect(grayscale);
			olPubImages.get(i).setEffect(null);
			currentlySelectedPublication = i;
		}
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
	
	public ScrollPane getSPPubScroller() {
		return spPubScroller;
	}
	
	@FXML protected void btnBackButton_Click() {
		app.getPrimaryStage().setScene(app.getJobSelectScene());
	}
}
