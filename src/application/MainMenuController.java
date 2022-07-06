package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainMenuController implements Initializable{

	@FXML
	WebView Map;
	@FXML
	Button MERGE;
	@FXML
	AnchorPane STANGA;
	
	private String link = getClass().getResource("/MainMapPack/MainMap.html").toExternalForm();
	private WebEngine engine;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine = Map.getEngine();
		engine.load(link);
		engine.setCreatePopupHandler((PopupFeatures config) -> null);
	}
	
	public void press(ActionEvent e) {
		if(STANGA.isVisible()) {
			STANGA.setVisible(false);
		}
		else {
			STANGA.setVisible(true);
		}
	}
	
	
}
