package offlineads;

import guijavafx.App;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class JobSelectController {
	
	private boolean isCreated;
	@FXML private Text txtTitleText;
	App app;
	
	/**
	 * Notify observers that creation is finished.
	 */
	public void onCreated() {
		isCreated = true;
	}
	
	@FXML protected void btnCutAdsButton_Click() {
		app.getPrimaryStage().setScene(app.getPublicationSelectScene());
	}
	
	@FXML protected void btnEnterAdsButton_Click() {
		app.getPrimaryStage().setScene(app.getJobSelectScene());
	}
	
	@FXML protected void btnEditAdsButton_Click() {
		app.getPrimaryStage().setScene(app.getJobSelectScene());
	}
	
	@FXML protected void btnTestButton_Click() {
		app.getPrimaryStage().setScene(app.getTestScene());
	}
	
	public boolean getIsCreated() {
		return isCreated;
	}
	
	public void setTxtTitleText(String newText) {
		this.txtTitleText.setText(newText);
	}
	
	public void setApp(App app) {
		this.app = app;
	}
	
}
