package bd;

import java.sql.*;
import javax.swing.*;

public class JavaConnection {
	
	Connection connection = null;
	public static Statement stmt = null;
	public static Connection ConnectBd(){
		try{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:liclivros.sqlite");
			JOptionPane.showMessageDialog(null, "CONEXÃO ESTABELECIDA");
			
			/*stmt = connection.createStatement();
			String sql = "CREATE TABLE PACOTES " +
                   "(ID INT PRIMARY KEY     NOT NULL," +
                   " ESCOLA           STRING  NOT NULL, " + 
                   " ANOESCOLAR        STRING  NOT NULL, " + 
                   " QUANTIDADE     INT, " + 
                   " COMPRAR        INT, " +
                   " PRECO          DOUBLE   NOT NULL)"; 
			
			stmt.executeUpdate(sql);
			stmt.close();
			*/
			return connection;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
