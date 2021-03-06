package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.DaoImp;
import service.Tasks;

public class UpdateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnMySpace;

    @FXML
    private Button btnUpdate;

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
    private TextArea currentTitle;

    @FXML
    private RadioButton doing;

    @FXML
    private RadioButton done;

    @FXML
    private RadioButton other;

    @FXML
    private RadioButton presentation;

    @FXML
    private RadioButton research;

    @FXML
    private RadioButton standby;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfdeadline;

    @FXML
    private RadioButton todo;

    @FXML
    private TableView<Tasks> tvTasks;

    DaoImp dao = new DaoImp();
    MainController change = new MainController();
    

    @FXML
    void initialize() {
		currentTitle.setEditable(false);
		
		showTasks();
		
    	btnUpdate.setOnAction(event -> {
			updateTask();
		    change.changeToSpace(event);
			showTasks();
	});
    	btnMySpace.setOnAction(event -> {
			change.changeToSpace(event);
		});
    }
    
    @FXML
    void handleMouseAction(MouseEvent event) {
	 
		Tasks task = tvTasks.getSelectionModel().getSelectedItem();
		currentTitle.setText(task.getTitle());
		tfTitle.setText(task.getTitle());
		tfDescription.setText(task.getDescription());
		
		ToggleGroup stat = new ToggleGroup();
		todo.setToggleGroup(stat);
		todo.setSelected(true);
		doing.setToggleGroup(stat);
		done.setToggleGroup(stat);
		
		ToggleGroup cat = new ToggleGroup();
		presentation.setToggleGroup(cat);
		presentation.setSelected(true);
		research.setToggleGroup(cat);
		standby.setToggleGroup(cat);
		other.setToggleGroup(cat);
		
		if(task.getStatus().equals("To Do")) {
			todo.setSelected(true);
		}else if(task.getStatus().equals("Doing")) {
			doing.setSelected(true);
		}else if(task.getStatus().equals("Done")) {
			done.setSelected(true);
		}
		colStatus.setText(task.getStatus());
		tfdeadline.setText(task.getDeadline());
		if(task.getCategorie().equals("Presentation")) {
			presentation.setSelected(true);
		}else if(task.getCategorie().equals("Research")) {
			research.setSelected(true);
		}else if(task.getCategorie().equals("Standby")) {
			standby.setSelected(true);
		}else if(task.getCategorie().equals("Other")) {
			other.setSelected(true);
		}
    }


    public void updateTask() {
        String currenttitle = currentTitle.getText();
		String title = tfTitle.getText();
		String description = tfDescription.getText();
		String deadline = tfdeadline.getText();
		String status = null;
		String categorie = null;
		
		ToggleGroup stat = new ToggleGroup();
		todo.setToggleGroup(stat);
		todo.setSelected(true);
		doing.setToggleGroup(stat);
		done.setToggleGroup(stat);
		
		ToggleGroup cat = new ToggleGroup();
		presentation.setToggleGroup(cat);
		presentation.setSelected(true);
		research.setToggleGroup(cat);
		standby.setToggleGroup(cat);
		other.setToggleGroup(cat);
		
		if (todo.isSelected()) {
			 status = "To Do";
		} else if (doing.isSelected()) {
			 status = "Doing";
		} else if (done.isSelected()) {
			 status = "Done";
		}

		if (presentation.isSelected()) {
			 categorie = "Presentation";
		} else if (research.isSelected()) {
			 categorie = "Research";
		} else if (standby.isSelected()) {
			 categorie = "Standby";
		}else if (other.isSelected()) {
			 categorie = "Other";
		}

		Tasks task = new Tasks(currenttitle,title, description, status, deadline,categorie);
		dao.update(task);
	}
    
    public void showTasks() {
		ObservableList<Tasks> list = dao.TasksList();

		colTitle.setCellValueFactory(new PropertyValueFactory<Tasks, String>("title"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Tasks, String>("description"));
		colStatus.setCellValueFactory(new PropertyValueFactory<Tasks, String>("status"));
		colDeadline.setCellValueFactory(new PropertyValueFactory<Tasks, String>("deadline"));
		colCategorie.setCellValueFactory(new PropertyValueFactory<Tasks, String>("categorie"));

		tvTasks.setItems(list);

	}
}
