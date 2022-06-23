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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DaoImp;
import service.UserService;
import service.Users;

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

		btnLogIn.setOnAction(event -> {
			DaoImp work = new DaoImp();

			String loginUser = tfusername.getText().trim();
			String loginPwd = tfpassword.getText().trim();

			Users user = new Users();
			user.setUsername(loginUser);
			user.setPassword(loginPwd);

			Users userRow = work.getUser(user);

			if (userRow != null) {
				UserService.setCurrentUserId(userRow.getId());
				showMain();
			} else {
				Shaker shaker = new Shaker(tfusername);
				shaker.shake();
				Shaker shaker1 = new Shaker(tfpassword);
				shaker1.shake();
			}

		});

		btnSignUp.setOnAction(event -> {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void showMain() {
		// take users to the addItem screen
		btnLogIn.getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Space.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parent root = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("2DO!!");
		stage.showAndWait();
	}
}
