package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SignUpController implements Initializable{
	@FXML
	Button SignUpButton;
	@FXML
	Text ErrorLabel;
	@FXML
	TextField UsernameTextField;
	@FXML
	TextField EmailTextField;
	@FXML
	TextField PwdTextField;
	@FXML
	TextField CnfPwdTextField;
	@FXML
	ImageView ErrorUsername;
	@FXML
	ImageView ErrorEmail;
	@FXML
	ImageView ErrorPwd;
	@FXML
	ImageView ErrorCnfPwd;
	@FXML
	AnchorPane AnchorSignUp;
	@FXML
	private ImageView Fundal;
	@FXML
	private ImageView Title;
	@FXML
	private ImageView CloseImage;
	@FXML
	private Button Close;
	
	private String Username;
	private String Email;
	private String Pwd;
	private String CnfPwd;
	private String RegexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	private String RegexPwd = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16}$";
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Image close = new Image(getClass().getResource("/Photos/CloseIcon.png").toURI().toString());
			CloseImage.setImage(close);
			Image fundal = new Image(getClass().getResource("/Photos/Log-SignUp-Fundal.jpg").toURI().toString());
			Fundal.setImage(fundal);
			Image title = new Image(getClass().getResource("/Photos/SignUpTitle.jpg").toURI().toString());
			Title.setImage(title);			
			Image image = new Image(getClass().getResource("/Photos/WarningIcon.jpg").toURI().toString());
			ErrorUsername.setImage(image);
			ErrorEmail.setImage(image);
			ErrorPwd.setImage(image);
			ErrorCnfPwd.setImage(image);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void SignUp(ActionEvent e) throws IOException{
		
		ErrorUsername.setVisible(false);
		ErrorEmail.setVisible(false);
		ErrorPwd.setVisible(false);
		ErrorCnfPwd.setVisible(false);
		
		Button sender = (Button) e.getSource();
		if(sender.getText().equalsIgnoreCase("Sign Up")) {
			try {
				Username = UsernameTextField.getText();
				Email = EmailTextField.getText();
				Pwd = PwdTextField.getText();
				CnfPwd = CnfPwdTextField.getText();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			if(Username.length() < 6) {
				ErrorLabel.setText("Introduceti un username cu peste 6 caractere!");
				ErrorUsername.setVisible(true);
				return;
			}
			else if(Username.length() > 15) {
				ErrorLabel.setText("Introduceti un username sub 15 de caractere!");
				ErrorUsername.setVisible(true);
				return;
			}
			if(Email.isBlank()){
				ErrorLabel.setText("Introduceti o valoare in fiecare casuta!");
				ErrorEmail.setVisible(true);
				return;
			}
			else {
				Pattern pattern = Pattern.compile(RegexEmail);
				Matcher matcher = pattern.matcher(Email);
				if(!matcher.matches()) {
					ErrorLabel.setText("Introduceti o adresa de email valida!");
					ErrorEmail.setVisible(true);
					return;
				}
			}
			if(Pwd.isBlank()) {
				ErrorLabel.setText("Introduceti o valoare in fiecare casuta!");
				ErrorPwd.setVisible(true);
				return;
			}
			else {
				Pattern pattern = Pattern.compile(RegexPwd);
				Matcher matcher = pattern.matcher(Pwd);
				if(!matcher.matches()) {
					ErrorLabel.setText("Parola trebuie sa contina numere, litere mici si mari. Intre 4 si 16 caractere!");
					ErrorPwd.setVisible(true);
					return;
				}
			}
			if(CnfPwd.isBlank()) {
				ErrorLabel.setText("Introduceti o valoare in fiecare casuta!");
				ErrorCnfPwd.setVisible(true);
				return;
			}
			else if(!CnfPwd.equalsIgnoreCase(Pwd)) {
				ErrorLabel.setText("Parolele nu coincid!");
				ErrorCnfPwd.setVisible(true);
				return;
			}
			String salt = Encryption.getSalt();
			String SecurePwd = Encryption.getPwd(Pwd);
			if(DBUtils.SignUpUser(e, Username, Email, SecurePwd, salt) == false) {
				ErrorLabel.setText("Acest username nu poate fi folosit!");
				ErrorUsername.setVisible(true);
				return;
			}
		}
		
		//Scene Transition
		
		Parent root = FXMLLoader.load(getClass().getResource("/FxmlFiles/Login.fxml"));
		root.getStylesheets().add(getClass().getResource("/CssFiles/Login.css").toExternalForm());
		Scene scene = SignUpButton.getScene();
		root.translateYProperty().set(-(scene.getHeight()));
		BorderPane rootPane = (BorderPane) scene.getRoot();
		StackPane PaneScene = (StackPane) rootPane.getRight();
		PaneScene.getChildren().add(root);
		
		Timeline timeline = new Timeline();
		KeyFrame kf = new KeyFrame(Duration.millis(300), new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN));
		timeline.getKeyFrames().add(kf);
		timeline.setOnFinished(event->{
			PaneScene.getChildren().remove(AnchorSignUp);
		});
		timeline.play();
	}
	public void CloseApp(ActionEvent e) {
		System.exit(0);
	}
}
