package application;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Save_Del_Dialog {
	boolean returnVal;
	
	public boolean show(String text) {
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		
		Text label = new Text(text);
		label.setFont(Font.font("System", FontWeight.BOLD, 19));
		label.setTextAlignment(TextAlignment.CENTER);
		label.setLayoutY(40);
		label.setWrappingWidth(350);
		
		Button but = new Button("Vezi");
		but.setId("Save");
		but.setLayoutY(100);
		but.setLayoutX(70);
		but.setPrefWidth(70);
		but.setCursor(Cursor.HAND);
		
		EventHandler<MouseEvent> butEventHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				stage.close();
				returnVal = true;
			}
			
		};
		but.addEventFilter(MouseEvent.MOUSE_CLICKED, butEventHandler);
		
		Button but1 = new Button("Sterge");
		but1.setId("Del");
		but1.setLayoutY(100);
		but1.setLayoutX(200); 
		but1.setPrefWidth(70);
		but1.setCursor(Cursor.HAND);
		
		EventHandler<MouseEvent> but1EventHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				stage.close();
				returnVal = false;
			}
			
		};
		but1.addEventFilter(MouseEvent.MOUSE_CLICKED, but1EventHandler);
		
		AnchorPane layout = new AnchorPane();
		layout.getChildren().addAll(label, but, but1);
		
		Scene scene = new Scene(layout, 350, 150);
		scene.getStylesheets().add(getClass().getResource("/CssFiles/Save_Del_Dialog.css").toExternalForm());
		
		stage.setTitle("Dialog");
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.showAndWait();
		
		return returnVal;
	}
}
