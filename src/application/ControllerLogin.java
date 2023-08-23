package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerLogin {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button loginBtn;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Label forErrorLabel;
	
	String url = "jdbc:mysql://localhost:3306/questuradb";
	String usernameDB = "root";
	String passwordDB = "";
	
	public void login(ActionEvent event) {
//		System.out.println(username.getText());
//		System.out.println(password.getText());				
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, usernameDB,passwordDB );
			PreparedStatement statement = connection.prepareStatement("select * from login  WHERE Email= ? and Password = ?");
			//String miaquery = "select * from login  WHERE Email="+username.getText()+"and Password ="+ password.getText();
			statement.setString(1,username.getText());
			statement.setString(2,password.getText());
			ResultSet resultSet = statement.executeQuery();
			
			if(!resultSet.next()) {
				//System.out.println("ERRORE!!");
				forErrorLabel.setText("credenziali errate, riprovare!");
			}else {
				
				root = FXMLLoader.load(getClass().getResource("Loggato	.fxml"));
				stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
				
			}
			
			
			
//			while(resultSet.next()) {
//				System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
//				
////				if(username.getText() == resultSet.getString(1) && password.getText() == resultSet.getString(2)) {
////					System.out.println("sono ugualiiii");
////				}
//			}
			
		}catch(Exception e) {
			System.out.println("non va");
		}
		
		
		
		
		
		
		
		
	}
}
