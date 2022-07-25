package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

public class Chestionar1Controller implements Initializable{
	
	@FXML
	Text Intrebare;
	@FXML
	Button ButtonDa;
	@FXML
	Button ButtonNu;
	@FXML
	Button Salvare;
	@FXML
	ImageView Fundal;
	@FXML
	Button Incepe;
	
	public LineNumberReader br;
	public LineNumberReader Lr;
	private ArrayList<String> list = new ArrayList<String>();
	
	int line = 0;
	int R=0, I=0 , A=0, S=0, E=0, C=0, max1=-10, max2=-10, ok = 0;
	String prof1, prof2, val, caz1, caz2;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Intrebare.setText("In chestionar veti avea intrebrebari de genul Imi place sa, Pot sa.., la care trebuie sa răspundeți  cu da/nu. Odată început chestionarul trebuie finalizat, iar la final va vor fi recomandate facultăți care este posibil sa va placa. După apăsarea butonului “Salvare”, ele vor apărea in secțiunea de facultăți salvate");
		Image image = new Image(getClass().getResource("/Photos/Chestionar.jpg").toExternalForm());
		Fundal.setImage(image);
}
	
	public void Incepe(ActionEvent e) {
		try {
			ButtonDa.setVisible(true);
			ButtonNu.setVisible(true);
			Incepe.setVisible(false);
			InputStream is = getClass().getResourceAsStream("/Resources/Intrebari.txt");
			InputStreamReader isr = new InputStreamReader(is);
			br = new LineNumberReader(isr);
			Intrebare.setText(br.readLine());
			InputStream is1 = getClass().getResourceAsStream("/Resources/OrdonareFacultati.txt");
			InputStreamReader isr1 = new InputStreamReader(is1);
			Lr = new LineNumberReader(isr1);
		} catch (FileNotFoundException ex) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText(ex.toString());
			ex.printStackTrace();
		} catch (IOException ex) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText(ex.toString());
			ex.printStackTrace();
	}
	}
	
	public void maxi2() {
		if(R>=max2)
		{
			if(R>max1)
			{
			max2=max1;
			max1=R;
			prof2=prof1;
			prof1="R";
			}
			else
			{
				max2=R;
				prof2="R";	
			}
		}
	    if(I>=max2)
		{
			if(I>max1)
			{
				max2=max1;
				max1=I;
				prof2=prof1;
				prof1="I";
			}
			else
			{
				max2=I;
				prof2="I";		
			}	
		}
		if(A>=max2)
		{
			if(A>max1)
			{
				max2=max1;
				max1=A;
				prof2=prof1;
				prof1="A";
			}
			else
			{
				max2=A;
				prof2="A";
			}
		}
	    if(S>=max2)
		{
		 if(S>max1)
		{
			max2=max1;
			max1=S;
			prof2=prof1;
			prof1="S";
		}
		 else
		 {
			 max2=S;
			 prof2="S";
		 }
		}
		 if(E>=max2)
		{
		 if(E>max1)
		{
			max2=max1;
			max1=E;
			prof2=prof1;
			prof1="E";
		}
		 else
		 {
			 max2=E;
			 prof2="E";
		 }
		}
	    if(C>=max2)
		{
		 if(C>max1)
		{
			max2=max1;
			max1=C;
			prof2=prof1;
			prof1="C";
		}
		 else
		 {
			 max2=C;
			 prof2="C";
		 }
		}
	}

	public void ButonDa(ActionEvent e) throws IOException {
		Button sender = (Button)e.getSource();
		if(sender.getText().equalsIgnoreCase("Da")) {
			//Algoritm intrebari
			line=br.getLineNumber()-1;
			if(line % 6 == 0)
				R+=1;
			else if (line % 6 == 1)
				I+=1;
			else if(line %6 ==2)
				A+=1;
			else if(line %6 == 3)
				S+=1;
			else if(line % 6 ==4)
				E+=1;
			else if(line % 6== 5)
				C+=1;
		}
		try {
			Intrebare.setText(br.readLine());
			if((sender.getText().equalsIgnoreCase("Da") || sender.getText().equalsIgnoreCase("Nu") ) && br.getLineNumber()>60)
			{
				ButtonDa.setVisible(false);
				ButtonNu.setVisible(false);
				Salvare.setVisible(true);
				maxi2();
				caz1=prof1+prof2;
				caz2=prof2+prof1;
				Intrebare.setText("");
				if(R != 0 || I != 0 || A != 0 || S != 0 || E != 0 || C != 0) {
					
					while(Lr.getLineNumber()<32)
					{
						if(Lr.getLineNumber()%2==0)
						{
							val=Lr.readLine();
							
							if(val.equalsIgnoreCase(caz1) || val.equalsIgnoreCase(caz2))
							{
								if(Intrebare.getText().equalsIgnoreCase(""))
								{
									String text = Lr.readLine();
									Intrebare.setText(text);
									list.add(text);
									ok = 1;
									Salvare.setText("Salvare");
								}
								else {
									String text = Lr.readLine();
									Intrebare.setText(Intrebare.getText()+"\n"+text);
									list.add(text);
								}
							}
						}
						else
							Lr.readLine();
						
					}
				}
				if(Intrebare.getText().equalsIgnoreCase(""))
				{
					Salvare.setText("Iesire");
					ok = 0;
					Intrebare.setText("Ne pare rau, nu avem momentan o facultate care sa se potriveasca profilului tau. :(");
				}
			
		}

		} 
		catch (IOException e1) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText(e1.toString());
			e1.printStackTrace();
			
		}
	}
	
	public void Save(ActionEvent e) throws IOException {
		if(ok == 1) {
			String str;
			String marker;
			for(String i : list) {
				InputStream is = getClass().getResourceAsStream("/Resources/MarkerFac.txt");
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader brFac = new BufferedReader(isr);
				while((str = brFac.readLine()) != null) {
					if(str.equalsIgnoreCase(i)) {
						marker = brFac.readLine();
						DBUtils.saveFac(marker, i);
						break;
					}
				}
				brFac.close();
			}
			Alert a = new Alert(AlertType.NONE,"", ButtonType.OK);
			a.setHeaderText("Facultati salvate cu success!");
			a.initStyle(StageStyle.UNDECORATED);
			a.setX(780);
			a.setY(200);
			a.showAndWait();
		}
		DBUtils.changeScene(e, "/FxmlFiles/MainMenu.fxml", "/CssFiles/MainMenu.css", DBUtils.Username);
	}
	
}
