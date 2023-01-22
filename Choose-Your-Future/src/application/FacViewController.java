package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import javafx.application.HostServices;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class FacViewController implements Initializable{

	@FXML
	WebView Facultate;
	
	private WebEngine engine;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine = Facultate.getEngine();
		
		EventListener listener = new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				String link = (String)engine.executeScript("returnLink()");
				try {
					Desktop.getDesktop().browse(new URI(link));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		engine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) ->{
			if(newState == State.SUCCEEDED) {
				Document doc = engine.getDocument();
				Element el = doc.getElementById("link");
				((EventTarget) el).addEventListener("click", listener, false);
			}
		});
	}
	
	public void ShowFac(String facultate) {
		String link = getClass().getResource("/Facultati/" + facultate + ".html").toExternalForm();
		engine = Facultate.getEngine();
		engine.load(link);
	}
	
}
