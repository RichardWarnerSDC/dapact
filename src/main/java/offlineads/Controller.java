package offlineads;

import java.util.ArrayList;

import javafx.scene.shape.Rectangle;

public class Controller {
	
	public void setSelections(Controller controller, ArrayList<Rectangle> selections) {
    	if (controller.getClass().equals(CutAdsController.class)) {
    		CutAdsController cutAdsController = (CutAdsController) controller;
    		cutAdsController.setSelections(selections);
    	} else if (controller.getClass().equals(EnterAdsController.class)) {
    		EnterAdsController enterAdsController = (EnterAdsController) controller;
    		enterAdsController.setSelections(selections);
    	}
		
	}
	
}
