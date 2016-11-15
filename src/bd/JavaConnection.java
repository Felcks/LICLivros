package bd;

import java.sql.*;
import javax.swing.*;

import principais.EstoqueManager;

public class JavaConnection {
	
	public static JavaConnection javaConnection;
	
	public static JavaConnection getInstance(){
		if(javaConnection == null)
			javaConnection = new JavaConnection();
		
		return javaConnection;
	}
	
	public Connection connection = null;
	public void ConnectBd(){
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:liclivros.sqlite");
			JOptionPane.showMessageDialog(null, "CONEXÃO ESTABELECIDA");
			EstoqueManager.getInstance().getLivrosDoBancoDeDados();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
