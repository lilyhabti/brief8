package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controllers.LoginController;
import controllers.SignUpController;
import database.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import service.Tasks;
import service.UserService;
import service.Users;

public class DaoImp implements Dao {
	
	Connection conn = DBconnection.getconnect();
	PreparedStatement ps;
	Statement st;

	@Override
	public boolean signUpUser(Users user) {
//		SignUpController change = new SignUpController();
//		LoginController change = new LoginController();
		
		int container=0;
		
		PreparedStatement psInsert = null;
		PreparedStatement psExists = null;
		ResultSet resultset = null;
		String firstname = user.getFirstname();
		String lastname = user.getLastname();
		String username = user.getUsername();
		String password = user.getPassword();
		
		
		try {
			if(!firstname.equals("") && !lastname.equals("") && !username.equals("") && !password.equals("")) {
				String query = "SELECT * FROM users WHERE username = ?";
				psExists = conn.prepareStatement(query);
				psExists.setString(1,user.getUsername());
				resultset = psExists.executeQuery();
				
				
				if(resultset.isBeforeFirst()) {
					System.out.println("User already exists!!");
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Username Not valid!!");
					alert.show();
					
				}else {
					String query1 = "INSERT INTO users(firstname , lastname , username , password) VALUES (?,?,?,?)";
					psInsert=conn.prepareStatement(query1);
					psInsert.setString(1,firstname);
					psInsert.setString(2,lastname);
					psInsert.setString(3,username);
					psInsert.setString(4,password);
					psInsert.executeUpdate();
					container=1;
				}
			}else {
				System.out.println("Fill in information!!");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Fill in information!!");
				alert.show();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(container==1) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Users getUser(Users user) {
		ResultSet rs = null;
		Users user1 = null;
		
		if(!user.getUsername().equals("") || !user.getPassword().equals("")) {
			String query = "SELECT * FROM users WHERE username = ? AND password = ?";
			
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1,user.getUsername());
				ps.setString(2,user.getPassword());
				
				rs = ps.executeQuery();
				
			    while(rs.next()) {
			    	user1 = new Users();
			    	user1.setId(rs.getInt("userid"));
			    
			    }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("Please Enter your credentials!");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Enter your credentials!");
			alert.show();
		}
		return user1;
	}

	@Override
	public ObservableList<Tasks> TasksList() {
		ObservableList<Tasks> List = FXCollections.observableArrayList();
		
        String query = "SELECT * FROM tasks WHERE userid = ?" ;
        PreparedStatement st;
        ResultSet rs;
        
        try{
        	st=conn.prepareStatement(query);
			st.setInt(1,UserService.getCurrentUserId());
            rs = st.executeQuery();
     
            Tasks task;
            while(rs.next()){
                task = new Tasks(rs.getString("title"),rs.getString("description"),rs.getString("status"),rs.getString("deadline"),rs.getString("categorie"));
                List.add(task);
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return List;
	}

	@Override
	public void create(Tasks task) {
		String title = task.getTitle();
		String description = task.getDescription();
		String status = task.getStatus();
		String deadline = task.getDeadline();
		String categorie = task.getCategorie();
		
		String query = "INSERT INTO tasks(title,description,status,deadline,categorie,userid) VALUES ('" +title + "','" + description + "','" + status + "','" + deadline + "','" + categorie +"',"+UserService.getCurrentUserId()+ ")";
	       
		try{
			st = conn.createStatement();
			st.executeUpdate(query);
			System.out.println("Creating is Done.");
		}catch(Exception ex){
            ex.printStackTrace();
			System.out.println("Creating Did not go will.");
        }
	}

	@Override
	public void update(Tasks task) {
		String currentTitle = task.getCurrentTitle();
		String title = task.getTitle();
		String description = task.getDescription();
		String status = task.getStatus();
		String deadline = task.getDeadline();
		String categorie = task.getCategorie();
		
		String query = "UPDATE  tasks  SET title = '" +title + "',description='" + description + "',status='" + status + "',deadline='" + deadline + "',categorie='" + categorie + "' WHERE title ='" + currentTitle+"'";
		//String query = "UPDATE  tasks SET description='" + description  + "' WHERE title ='" + title+"'";
		try{
			st = conn.createStatement();
			st.executeUpdate(query);
			System.out.println("Updating is Done.");
		}catch(Exception ex){
            ex.printStackTrace();
			System.out.println("Updating Did not go will.");
        }
	}
	
	@Override
	public void delete(String title) {
		
        String query = "DELETE FROM tasks WHERE title ='" + title +"'" ;
        
		try{
			st = conn.createStatement();
			st.executeUpdate(query);
			  System.out.println("Deleting is Done.");
		}catch(Exception ex){
            ex.printStackTrace();
        }
		
	}

}
