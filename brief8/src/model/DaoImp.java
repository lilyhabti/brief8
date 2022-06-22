package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class DaoImp implements Dao {
	
	Connection conn = DBconnection.getconnect();
	PreparedStatement ps;
	Statement st;

	@Override
	public void signUpUser(Users user) {
//		SignUpController change = new SignUpController();
		
		PreparedStatement psInsert = null;
		PreparedStatement psExists = null;
		ResultSet resultset = null;
		String firstname = user.getFirstname();
		String lastname = user.getLastname();
		String username = user.getUsername();
		String password = user.getPassword();
		//String insert ="INSERT INTO users(firstname,lastname,username,password) VALUES(?,?,?,?)";
		
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
	}

	@Override
	public ResultSet getUser(Users user) {
		ResultSet rs = null;
		
		if(!user.getUsername().equals("") || !user.getPassword().equals("")) {
			String query = "SELECT * FROM users WHERE username = ? AND password = ?";
			
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1,user.getUsername());
				ps.setString(2,user.getPassword());
				
				rs = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("Please Enter your credentials!");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Enter your credentials!");
			alert.show();
		}
		return rs;
	}

	@Override
	public ObservableList<Tasks> TasksList() {
		ObservableList<Tasks> List = FXCollections.observableArrayList();
        String query = "SELECT * FROM tasks" ;
        ResultSet rs;
        
        try{
			st = conn.createStatement();
            rs = st.executeQuery(query);
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
	
	

}
