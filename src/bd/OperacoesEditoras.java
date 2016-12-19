package bd;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Cliente;
import principais.Editora;

public class OperacoesEditoras extends JavaConnection {

	private Statement stmt;
	
	public void INSERT_EDITORA(Editora editora){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();
			
			int id = editora.getId();
			String nome = editora.getNome();
			
			String sql = "INSERT INTO EDITORAS (ID, NOME)" +
			"VALUES (" + id + "," + 
					"'" + nome + "'" + ");";
			
			stmt.executeUpdate(sql);
			
		     
		    connection.commit();
			stmt.close();
			connection.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void UPDATE_EDITORA(Editora editora){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			int id = editora.getId();
			String nome = editora.getNome();
			
			stmt  = connection.createStatement();
			String sql = "UPDATE EDITORAS SET NOME=? WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setInt(2, id);
			
			int a  = statement.executeUpdate();
			
			connection.commit();
			stmt.close();
			connection.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}

}
