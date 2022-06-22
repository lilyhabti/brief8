package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import animations.Shaker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DaoImp;
import model.Users;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogIn;

    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField tfpassword;

    @FXML
    private TextField tfusername;
    
    

    @FXML
    void initialize() {
    	
    	btnLogIn.setOnAction(event ->{
    		DaoImp work = new DaoImp();
        	
        	String loginUser = tfusername.getText().trim(); 
        	String loginPwd = tfpassword.getText().trim();
        	
        	Users user= new Users();
        	user.setUsername(loginUser);
        	user.setPassword(loginPwd);
    		
    		ResultSet userRow = work.getUser(user);
    		
    		int counter =0;
    		
    		try {
				while(userRow.next()) {
					counter++;
				}
				if(counter==1) {
					showMain();
				}else {
						Shaker shaker= new Shaker(tfusername);
						shaker.shake();
						Shaker shaker1= new Shaker(tfpassword);
						shaker1.shake();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	});
    	
    	btnSignUp.setOnAction(event ->{
    		//take users to the sign up screen
    		btnSignUp.getScene().getWindow().hide();
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("/view/SignUp.fxml"));
    		try {
				loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		Parent root =  loader.getRoot();
    		Stage stage = new Stage();
    		stage.setScene(new Scene(root));
    		stage.setTitle("2DO!!");
    		stage.showAndWait();
    	});
    }
    
    private void showMain() {
    	//take users to the addItem screen
    	btnLogIn.getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Space.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parent root =  loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("2DO!!");
		stage.showAndWait();
    }
}
