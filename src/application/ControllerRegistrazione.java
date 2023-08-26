package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ControllerRegistrazione implements Initializable{
	@FXML
	private ChoiceBox<String> cittaDiNascita;
	@FXML
	private ChoiceBox<String> regione;
	@FXML
	private ChoiceBox<String> provincia;
	
	@FXML
	private TextField nome;
	@FXML
	private TextField cognome;
	@FXML
	private DatePicker dataDiNascita;
	@FXML
	private TextField codiceFiscale;
	@FXML
	private TextField email;
	@FXML
	private TextField password;
	
	
	String url = "jdbc:mysql://localhost:3306/questuradb";
	String usernameDB = "root";
	String passwordDB = "";
	
	
	
	
	 ArrayList<String> leCittaNascita = new ArrayList<String>();
	 ArrayList<String> leRegioni = new ArrayList<String>();
	 ArrayList<String> leProvincie = new ArrayList<String>();
	//private String[] food = {"riso","carne macinata"};
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, usernameDB,passwordDB );
			//SELECT anagrafica.CittàNascita, anagrafica.Regione, anagrafica.Provincia FROM `anagrafica` WHERE 1
			
			PreparedStatement statement = connection.prepareStatement("SELECT anagrafica.CittàNascita, anagrafica.Regione, anagrafica.Provincia FROM `anagrafica` WHERE 1");
			//String miaquery = "select * from login  WHERE Email="+username.getText()+"and Password ="+ password.getText();
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				leCittaNascita.add(resultSet.getString(1));
				leRegioni.add(resultSet.getString(2));
				leProvincie.add(resultSet.getString(3));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		cittaDiNascita.getItems().addAll(leCittaNascita);
		cittaDiNascita.setOnAction(this::getOrigini);
		
		regione.getItems().addAll(leRegioni);
		regione.setOnAction(this::getOrigini);
		
		provincia.getItems().addAll(leProvincie);
		provincia.setOnAction(this::getOrigini);
	}
	
	String cittaScelta;
	String regioneScelta;
	String provinciaScelta;
	
	public void getOrigini(ActionEvent event) {
		cittaScelta = cittaDiNascita.getValue();
		regioneScelta = regione.getValue();
		provinciaScelta = provincia.getValue();
	}
	
	public void registrati(ActionEvent event) {
	System.out.println(nome.getText());
	System.out.println(cognome.getText());
	System.out.println(dataDiNascita.getValue());
	System.out.println(codiceFiscale.getText());
	System.out.println(email.getText());
	System.out.println(password.getText());
	System.out.println("cittaScelta = " + cittaScelta);
	System.out.println("regioneScelta = " + regioneScelta);
	System.out.println("provinciaScelta = " + provinciaScelta);

	try {
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection connection = DriverManager.getConnection(url, usernameDB,passwordDB );
	//SELECT anagrafica.CittàNascita, anagrafica.Regione, anagrafica.Provincia FROM `anagrafica` WHERE 1
	
	PreparedStatement statement = connection.prepareStatement("SELECT * FROM `anagrafica` WHERE anagrafica.Nome = ? and anagrafica.Cognome = ? and anagrafica.DataNascita = ?  and anagrafica.CodiceFiscale = ? and anagrafica.CittàNascita = ? and anagrafica.Regione = ? and anagrafica.Provincia = ?");
	//String miaquery = "select * from login  WHERE Email="+username.getText()+"and Password ="+ password.getText();
	statement.setString(1,nome.getText());
	statement.setString(2,cognome.getText());
	statement.setDate(3,java.sql.Date.valueOf(dataDiNascita.getValue()));
	statement.setString(4,codiceFiscale.getText());
	statement.setString(5,cittaScelta);
	statement.setString(6,regioneScelta);
	statement.setString(7,provinciaScelta);
	ResultSet resultSet = statement.executeQuery();
	if(!resultSet.next()) {
		System.out.println("non esisti BRO");
	}else {
		System.out.println("purtroppo esisti");
		
		//SELECT * FROM `utenti` WHERE utenti.Nome = ? and utenti.Cognome = ? and utenti.CittàNascita = ? and utenti.Regione = ? and utenti.Provincia = ? and utenti.DataNascita = ? and utenti.CodiceFiscale = ? and utenti.Email = ?
		statement = connection.prepareStatement("SELECT * FROM `utenti` WHERE utenti.Nome = ? and utenti.Cognome = ? and utenti.CittàNascita = ? and utenti.Regione = ? and utenti.Provincia = ? and utenti.DataNascita = ? and utenti.CodiceFiscale = ? and utenti.Email = ?");
		statement.setString(1,nome.getText());
		statement.setString(2,cognome.getText());
		statement.setString(3,cittaScelta);
		statement.setString(4,regioneScelta);
		statement.setString(5,provinciaScelta);
		statement.setDate(6,java.sql.Date.valueOf(dataDiNascita.getValue()));
		statement.setString(7,codiceFiscale.getText());
		statement.setString(8,email.getText());
		resultSet = statement.executeQuery();
		
		if(!resultSet.next()) {
			
			System.out.println("ti registro subito");
			
			statement = connection.prepareStatement("INSERT INTO `login`(`Email`, `Password`) VALUES (? , ?)");

			statement.setString(1,email.getText());
			statement.setString(2,password.getText());
			//resultSet = statement.executeQuery();
			int row = statement.executeUpdate();

	           // rows affected
	           System.out.println("inserendo in login modificate "+row);
			
			
			statement = connection.prepareStatement("INSERT INTO `utenti`( `Nome`, `Cognome`, `CittàNascita`, `Regione`, `Provincia`, `DataNascita`, `CodiceFiscale`, `Email`) VALUES (?,?,?,?,?,?,?,?)");
			statement.setString(1,nome.getText());
			statement.setString(2,cognome.getText());
			statement.setString(3,cittaScelta);
			statement.setString(4,regioneScelta);
			statement.setString(5,provinciaScelta);
			statement.setDate(6,java.sql.Date.valueOf(dataDiNascita.getValue()));
			statement.setString(7,codiceFiscale.getText());
			statement.setString(8,email.getText());
			
			   row = statement.executeUpdate();

	           // rows affected
	           System.out.println("inserendo in utenti modificate = "+row);
			//resultSet = statement.executeQuery();
			
			
			
			
		}else {
			System.out.println("ti sei gia registrato BABAO");
		}
		
		
	}
	
	
	}catch(Exception e) {
		System.out.println("non va Regiostrazione");
		System.out.println(e);
	}
	
	}
}
