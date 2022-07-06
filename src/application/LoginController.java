package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LoginController implements Initializable{

	@FXML
	private Button LoginButton;
	@FXML
	private Button SignUpButton;
	@FXML
	private AnchorPane AnchorLogin;
	@FXML
	private TextField UsernameTextField;
	@FXML
	private TextField PasswordTextField;
	@FXML
	private Label ErrorLabel;
	@FXML
	private ImageView ErrorViewName;
	@FXML
	private ImageView ErrorViewPwd;
	
	String salt;
	String securePwd;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Image image = new Image("WarningIcon.jpg");
		//ErrorViewName.setImage(image);
		//ErrorViewPwd.setImage(image);
	}
	
	public void Login(ActionEvent e) {
		String username = null;
		String password = null;
		ErrorViewName.setVisible(false);
		ErrorViewPwd.setVisible(false);
		ErrorLabel.setText(" ");
		
		try{
			username = UsernameTextField.getText();
			password = PasswordTextField.getText();
		}
		catch(Exception ex){
			ex.printStackTrace();
			return;
		}
		if(username.isBlank() == true)
		{
			ErrorLabel.setText("Introduceti o valoare in fiecare casuta!");
			ErrorViewName.setVisible(true);
			return;
		}
		if(password.isBlank() == true)
		{
			ErrorLabel.setText("Introduceti o valoare in fiecare casuta!");
			ErrorViewPwd.setVisible(true);
			return;
		}
		if(DBUtils.LoginUser(e, username, password) == true)
		{
			ErrorLabel.setText("merge");
		}
		else {
			ErrorLabel.setText("Username sau parola incorecte");
			UsernameTextField.setText("");
			PasswordTextField.setText("");
			ErrorViewPwd.setVisible(true);
			ErrorViewName.setVisible(true);
		}
	}
	
	
	public void SignUp(ActionEvent e) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/FxmlFiles/SignUp.fxml"));
		Scene scene = SignUpButton.getScene();
		root.translateYProperty().set(scene.getHeight());
		BorderPane rootPane = (BorderPane) scene.getRoot();
		StackPane PaneScene = (StackPane) rootPane.getRight();
		PaneScene.getChildren().add(root);
		
		Timeline timeline = new Timeline();
		KeyFrame kf = new KeyFrame(Duration.millis(300), new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN));
		timeline.getKeyFrames().add(kf);
		timeline.setOnFinished(event->{
			PaneScene.getChildren().remove(AnchorLogin);
		});
		timeline.play();
		}
	
}
