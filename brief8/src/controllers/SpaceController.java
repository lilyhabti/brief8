package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DaoImp;
import service.Tasks;

public class SpaceController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnActions;

    @FXML
    private TableColumn<Tasks, String> colCategorie;

    @FXML
    private TableColumn<Tasks, String> colDeadline;

    @FXML
    private TableColumn<Tasks, String> colDescription;

    @FXML
    private TableColumn<Tasks, String> colStatus;

    @FXML
    private TableColumn<Tasks, String> colTitle;

    @FXML
    private TableView<Tasks> tvTasks;

    SignUpController change = new SignUpController();
    DaoImp dao = new DaoImp();
    
    @FXML
    void initialize() {
    	 
        showTasks();
        
        btnLogOut.setOnAction(event ->{
        	change.changeToLogin(event);
        });

        btnActions.setOnAction(event ->{
        	changeSpaceToMain(event);
        });
      
    }
    
   
    
    public void changeSpaceToMain(ActionEvent event) {
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        Scene scene= new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("2DO!!");
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void showTasks(){
    	ObservableList<Tasks> list = dao.TasksList();
        
    	colTitle.setCellValueFactory(new PropertyValueFactory<Tasks, String>("title"));
    	colDescription.setCellValueFactory(new PropertyValueFactory<Tasks, String>("description"));
    	colStatus.setCellValueFactory(new PropertyValueFactory<Tasks, String>("status"));
    	colDeadline.setCellValueFactory(new PropertyValueFactory<Tasks, String>("deadline"));
    	colCategorie.setCellValueFactory(new PropertyValueFactory<Tasks, String>("categorie"));
        
        
        tvTasks.setItems(list);
        
    } 
}
