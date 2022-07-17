package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.EventListener;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenuController extends Region implements Initializable{

	@FXML
	BorderPane MainPane;
	@FXML
	WebView Map;
	@FXML
	AnchorPane STANGA;
	@FXML
	ScrollPane DREAPTA;
	@FXML
	VBox Lista;
	@FXML
	Button MenuReturn;
	@FXML
	ImageView ImageReturn;
	@FXML
	Button viewLista;
	
	private String link = getClass().getResource("/MainMapPack/MainMap.html").toExternalForm();
	private WebEngine engine;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Image image = new Image(getClass().getResource("/Photos/CloseIcon.png").toURI().toString());
			ImageReturn.setImage(image);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		STANGA.setTranslateX(-250);
		DREAPTA.setTranslateX(250);
		
		engine = Map.getEngine();
		engine.load(link);
		engine.setCreatePopupHandler((PopupFeatures config) -> null);
		
		EventListener listener = new EventListener() {
			@Override
			public void handleEvent(org.w3c.dom.events.Event evt) {
				TranslateTransition slide = new TranslateTransition();
				slide.setDuration(Duration.millis(400));
				slide.setNode(STANGA);
				
				slide.setToX(0);
				slide.play();
				
				STANGA.setTranslateX(-250);
			}
		};
		
		EventListener contactListener = new EventListener() {

			@Override
			public void handleEvent(org.w3c.dom.events.Event evt) {
				String link = (String)engine.executeScript("returnContact()");
				try {
					Desktop.getDesktop().browse(new URI(link));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		EventListener admitereListener = new EventListener() {

			@Override
			public void handleEvent(org.w3c.dom.events.Event evt) {
				String link = (String)engine.executeScript("returnAdmitere()");
				try {
					Desktop.getDesktop().browse(new URI(link));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		EventListener informatiiListener = new EventListener() {

			@Override
			public void handleEvent(org.w3c.dom.events.Event evt) {
				String link = (String)engine.executeScript("returnInformatii()");
				try {
					Desktop.getDesktop().browse(new URI(link));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		EventListener saveListener = new EventListener() {

			@Override
			public void handleEvent(org.w3c.dom.events.Event evt) {
				String marker = (String)engine.executeScript("returnMarker()");
				String facultate = (String)engine.executeScript("returnFac()");
				if(DBUtils.saveFac(marker, facultate) == false) {
					engine.executeScript("alertError()");
				}
				else {
					engine.executeScript("alertSuccess()");
				}
				
			}
			
		};
		
		engine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
		    if (newState == State.SUCCEEDED) {
		        Document doc = engine.getDocument();
		        Element el = doc.getElementById("menu");
		        ((EventTarget) el).addEventListener("click", listener, false);
		        Element el1 = doc.getElementById("Contact");
		        ((EventTarget) el1).addEventListener("click", contactListener, false);
		        Element el2 = doc.getElementById("Admitere");
		        ((EventTarget) el2).addEventListener("click", admitereListener, false);
		        Element el3 = doc.getElementById("Informatii");
		        ((EventTarget) el3).addEventListener("click", informatiiListener, false);
		        Element el4 = doc.getElementById("Save");
		        ((EventTarget) el4).addEventListener("click", saveListener, false);
		    }
		});

		//engine.getLoadWorker().stateProperty().addListener(
				//new ChangeListener<State>() {
					
					//@Override
					//public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
						//if(newState == State.SUCCEEDED) {
							//JSObject window = (JSObject) engine.executeScript("window");
							//window.setMember("app", comms);
					//	}//
				//	}
					
				//});
	//	JSObject window = (JSObject) engine.executeScript("window");
	//	window.setMember("app", comms);
		//engine.setCreatePopupHandler((PopupFeatures config) -> null);
		
	}
	
	public void ReturnMenu(ActionEvent e) {
		TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.millis(400));
		slide.setNode(STANGA);
		
		slide.setToX(-250);
		slide.play();
		
		STANGA.setTranslateX(0);
	}
	
	public void viewLista(ActionEvent e) {
		TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.millis(400));
		slide.setNode(STANGA);
		
		slide.setToX(-250);
		
		STANGA.setTranslateX(0);
		
		TranslateTransition slide1 = new TranslateTransition();
		slide1.setDuration(Duration.millis(400));
		slide1.setNode(DREAPTA);
		
		slide1.setToX(0);
		slide1.play();
		
		DREAPTA.setTranslateX(250);
		slide.play();
		slide1.play();
	}
	
	public void returnLista(ActionEvent e) {
		TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.millis(400));
		slide.setNode(STANGA);
		
		slide.setToX(0);
		
		STANGA.setTranslateX(-250);
		
		TranslateTransition slide1 = new TranslateTransition();
		slide1.setDuration(Duration.millis(400));
		slide1.setNode(DREAPTA);
		
		slide1.setToX(250);
		slide1.play();
		
		DREAPTA.setTranslateX(0);
		slide.play();
		slide1.play();
	}
}
