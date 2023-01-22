package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MainLoginSignUpController implements Initializable{

	@FXML
	StackPane StackPaneCenter;
	@FXML
	ImageView Fundal;
	@FXML
	ImageView Logo;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/Login.fxml"));
		try {
			Image back = new Image(getClass().getResource("/Photos/MainLoginSignUpAnchor.jpg").toURI().toString());
			Fundal.setImage(back);
			Image logo = new Image(getClass().getResource("/Photos/Logo.png").toURI().toString());
			Logo.setImage(logo);
			Parent root = loader.load();
			root.getStylesheets().add(getClass().getResource("/CssFiles/Login.css").toExternalForm());
			StackPaneCenter.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}

}
