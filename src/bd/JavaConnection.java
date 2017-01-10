package bd;

import java.sql.*;
import javax.swing.*;

import principais.EstoqueManager;

public class JavaConnection {
	
	public Connection connection = null;
	public Statement stmt = null;
	public ResultSet resultSet = null;
	
	public void ConnectBd(){
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:liclivros.sqlite");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void closeConnections(){;
	  	if (resultSet != null) {
	        try {
	        	resultSet.close();
	        } catch (SQLException e) { /* ignored */}
	    }
	    if (stmt != null) {
	        try {
	            stmt.close();
	        } catch (SQLException e) { /* ignored */}
	    }
	    if (connection != null) {
	        try {
	            connection.close();
	        } catch (SQLException e) { /* ignored */}
	    }
	}
}
