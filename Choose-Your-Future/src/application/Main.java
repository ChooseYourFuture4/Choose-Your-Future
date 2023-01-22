package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	
	public static Connection conn = null;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			conn = DriverManager.getConnection
					("jdbc:sqlserver://sql5104.site4now.net;databaseName=db_a9387f_eduard123456;sendStringParametersAsUnicode=false;user=db_a9387f_eduard123456_admin;password=Rs6QuattroNikon;encrypt=true;trustServerCertificate=true;");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		try {
			if(conn == null)
				return;
			Parent root = FXMLLoader.load(getClass().getResource("/FxmlFiles/MainLoginSignUp.fxml"));
			root.getStylesheets().add(getClass().getResource("/CssFiles/Login.css").toExternalForm());
			Scene scene = new Scene(root);
			Image image = new Image(getClass().getResource("/Photos/Logo4.png").toURI().toString());
			primaryStage.getIcons().add(image);
			primaryStage.setTitle("Choose Your Future");
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
