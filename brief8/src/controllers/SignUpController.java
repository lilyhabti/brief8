package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DaoImp;
import service.Users;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogIn;

    @FXML
	public Button btnSignUp;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfSecondName;

    @FXML
    private TextField tfUsername;
    

    @FXML
    void initialize() {
    	
    	btnSignUp.setOnAction(event ->{
    		
    		if(createUser()) {
    			changeToLogin(event);
    		}
    		
    	});
    	
    	btnLogIn.setOnAction(event ->{
    		changeToLogin(event);
    	});
    }
    
    public boolean createUser() {
    	  DaoImp work =new DaoImp();
    	  boolean value;
    	  String firstname = tfFirstName.getText();
    	  String lastname = tfSecondName.getText();
    	  String username = tfUsername.getText();
    	  String password = pfPassword.getText();
    	  
    	  Users user = new Users(firstname,lastname,username,password);
    	  
    	  value = work.signUpUser(user);
    	  
    	  if(value) {
    		  return true;
    	  }else {
    		  return false;
    	  }

    }
   
    
    public void changeToLogin(ActionEvent event){
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        Scene scene= new Scene(root);
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
//    public void showLogin() {
//		// take users to the addItem screen
//		btnSignUp.getScene().getWindow().hide();
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(getClass().getResource("/view/Login.fxml"));
//		try {
//			loader.load();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Parent root = loader.getRoot();
//		Stage stage = new Stage();
//		stage.setScene(new Scene(root));
//		stage.setTitle("2DO!!");
//		stage.showAndWait();
//	}

}
