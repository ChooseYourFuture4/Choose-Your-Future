package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DBUtils{

	public static int id;
	
	public static void changeScene(ActionEvent e, String File, String username){
		Parent root = null;
		try{
			root = FXMLLoader.load(DBUtils.class.getResource(File));
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
