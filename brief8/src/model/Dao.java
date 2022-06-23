package model;

import java.sql.ResultSet;

import javafx.collections.ObservableList;
import service.Tasks;
import service.Users;

public interface Dao {
	
	public void signUpUser(Users user);
	
	public Users getUser(Users user);
	
	public ObservableList<Tasks> TasksList();
	
	public void create(Tasks task);

	public void update(Tasks task);
	
	public void delete(String title);
}
