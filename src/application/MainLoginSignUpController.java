package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class MainLoginSignUpController implements Initializable{

	@FXML
	StackPane StackPaneCenter;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FxmlFiles/Login.fxml"));
		try {
			Parent root = loader.load();
			StackPaneCenter.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
