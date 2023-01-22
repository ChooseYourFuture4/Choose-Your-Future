package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DBUtils{

	public static int id;
	public static String Username = null;
	
	public static void changeScene(ActionEvent e, String File, String css, String username){
		Parent root = null;
		try{
			if(username != null) {
				
				FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(File));
				root = loader.load();
				root.getStylesheets().add(DBUtils.class.getResource("/CssFiles/MainMenu.css").toExternalForm());
				MainMenuController controller = loader.getController();
				if(username != null) {
					controller.setUser(username);
					Username = username;
				}
			}
			else {
				root = FXMLLoader.load(DBUtils.class.getResource(File));
				root.getStylesheets().add(DBUtils.class.getResource(css).toExternalForm());
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public static boolean SignUpUser(ActionEvent e, String Username, String Email, String Pwd, String Salt) {
		
		PreparedStatement CheckUsername = null;
		PreparedStatement SignUp = null;
		ResultSet Resultset = null;
		Connection conn = Main.conn;
		
		try{
			CheckUsername = conn.prepareStatement("SELECT * FROM users WHERE Username = ?");
			CheckUsername.setString(1, Username);
			Resultset = CheckUsername.executeQuery(); 
			if(Resultset.isBeforeFirst())
				return false;
			
			SignUp = conn.prepareStatement("INSERT INTO users(Username, Email, Password, Salt) VALUES (?, ?, ?, ?)");
			SignUp.setString(1, Username);
			SignUp.setString(2, Email);
			SignUp.setString(3, Pwd);
			SignUp.setString(4, Salt);
			SignUp.execute();
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if(Resultset != null) {
				try {
					Resultset.close();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
			if(CheckUsername != null) {
				try {
					CheckUsername.close();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
			if(SignUp != null) {
				try {
					SignUp.close();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		return true;
	}
	
	public static boolean LoginUser(ActionEvent e, String Username, String Pwd) {
		PreparedStatement CheckPwd = null;
		ResultSet Resultset = null;
		Connection conn = Main.conn;
		String SecurePwd = null;
		String Salt = null;
		try {
			CheckPwd = conn.prepareStatement("SELECT Salt, Password, IdUsers FROM users WHERE Username = ?");
			CheckPwd.setString(1, Username.toString());
			Resultset = CheckPwd.executeQuery();
			if(!Resultset.isBeforeFirst())
				return false;
		
			Resultset.next();
			Salt = Resultset.getString(1);
			SecurePwd = Resultset.getString(2);
			
			if(!Encryption.verifiyPwd(Pwd, SecurePwd, Salt)){
				return false;
			}
			id = Resultset.getInt(3);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if(Resultset != null) {
				try {
					Resultset.close();
				}
				catch(SQLException ex){
					ex.printStackTrace();
				}
			}
			if(CheckPwd != null) {
				try {
					CheckPwd.close();
				}
				catch(SQLException ex){
					ex.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public static boolean getSavedFac(VBox box, WebEngine engine, ScrollPane scroll) {
		Button but = (Button)box.getChildren().get(0);
		box.getChildren().clear();
		box.getChildren().add(but);
		PreparedStatement getFac = null;
		ResultSet resultSet = null;
		Connection conn = Main.conn;
		try {
			getFac = conn.prepareStatement("SELECT Facultate, Marker FROM Facultati WHERE IdUser = ?");
			getFac.setInt(1, id);
			resultSet = getFac.executeQuery();
			if(resultSet.isBeforeFirst()) {
				while(resultSet.next()) {
					Text label = new Text();
					label.setText((String)resultSet.getString(1));
					label.setWrappingWidth(230);
					label.setFont(Font.font("System",FontWeight.BOLD, 19));
					label.setTextAlignment(TextAlignment.CENTER);
					label.setId((String)resultSet.getString(2));
					label.setCursor(Cursor.HAND);
					EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent e) {
							Text text = (Text) e.getSource();
							Save_Del_Dialog save_Del_Dialog = new Save_Del_Dialog();
							if(save_Del_Dialog.show(label.getText()) == true) {
								String string = "initMap.toMarker(" + "\"" +(String)text.getId() + "\"" + ")";
								engine.executeScript(string);
								TranslateTransition slide = new TranslateTransition();
								slide.setDuration(Duration.millis(400));
								slide.setNode(scroll);
								
								slide.setToX(250);
								slide.play();
								
								scroll.setTranslateX(0);
								slide.play();
							}
							else {
								PreparedStatement delFac = null;
								try {
									delFac = conn.prepareStatement("DELETE FROM Facultati WHERE Facultate = ?");
									delFac.setString(1, label.getText());
									delFac.execute();
									box.getChildren().remove(label);
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								finally {
									if(delFac != null) {
										try {
											delFac.close();
										}
										catch(SQLException ex) {
											ex.printStackTrace();
										}
									}
								}
							}
						}
						
					};
					label.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
					box.getChildren().add(label);
				}
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if(getFac != null) {
				try {
					getFac.close();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
			if(resultSet != null) {
				try {
					resultSet.close();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public static boolean saveFac(String marker, String facultate) {
		PreparedStatement CheckFac = null;
		PreparedStatement SaveFac = null;
		Connection conn = Main.conn;
		ResultSet resultSet = null;
		try {
			CheckFac = conn.prepareStatement("SELECT Facultate FROM Facultati WHERE IdUser = ?");
			CheckFac.setInt(1, id);
			resultSet = CheckFac.executeQuery();
			if(resultSet.isBeforeFirst()) {
				while(resultSet.next()) {
					if(resultSet.getString(1).equalsIgnoreCase(facultate)) {
						return false;
					}
				}
			}
			SaveFac = conn.prepareStatement("INSERT INTO Facultati VALUES (?, ?, ?)");
			SaveFac.setInt(1, id);
			SaveFac.setString(2, marker);
			SaveFac.setString(3, facultate);
			SaveFac.execute();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if(CheckFac != null)
				try {
					CheckFac.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if(SaveFac != null)
				try {
					SaveFac.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if(resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return true;
	}

}
