package offlineads;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import guijavafx.App;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CutAdsController implements Initializable {
	
	private boolean isCreated;
	private int cutAdsSceneID;
	@FXML private BorderPane root;
	private ScrollPane spPageScroller;
	private VBox vBoxPageImages;
	private ObservableList<ImageView> olPageImages = FXCollections.observableArrayList();
	private int currentlySelectedPage;
	ColorAdjust gsImage = new ColorAdjust();
	private App app;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		spPageScroller = new ScrollPane();
		spPageScroller.setFitToHeight(true);
		spPageScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		spPageScroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		vBoxPageImages = new VBox(16);
	    vBoxPageImages.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
		vBoxPageImages.setAlignment(Pos.CENTER);
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
		root.setLeft(spPageScroller);
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
	
	public boolean getIsCreated() {
		return isCreated;
	}
	
	public void setApp(App app) {
		this.app = app;
	}
	
	public void imgVwPageImage_Clicked(MouseEvent e, int pageNum) {
		
	}
	
}
