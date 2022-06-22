package model;

import java.sql.ResultSet;

import javafx.collections.ObservableList;

public interface Dao {
	
	public void signUpUser(Users user);
	
	public ResultSet getUser(Users user);
	
	public ObservableList<Tasks> TasksList();

}
