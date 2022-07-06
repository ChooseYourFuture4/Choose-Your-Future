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
					("jdbc:sqlserver://sql8002.site4now.net;databaseName=db_a873a4_cyf;sendStringParametersAsUnicode=false;user=db_a873a4_cyf_admin;password=CyfMain123;encrypt=true;trustServerCertificate=true;");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		try {
			if(conn == null)
				return;
			Parent root = FXMLLoader.load(getClass().getResource("/FxmlFiles/MainMenu.fxml"));
			Scene scene = new Scene(root);
			Image image = new Image(getClass().getResource("/Photos/Icon.jpg").toURI().toString());
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
