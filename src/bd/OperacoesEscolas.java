package bd;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Editora;
import principais.Escola;

public class OperacoesEscolas extends JavaConnection {

	private Statement stmt;
	
	public void INSERT_ESCOLA(Escola escola){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();
			
			int id = escola.getId();
			String nome = escola.getNome();
			
			String sql = "INSERT INTO ESCOLAS (ID, NOME)" +
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
	
	public void UPDATE_ESCOLA(Escola escola){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			int id = escola.getId();
			String nome = escola.getNome();
			
			stmt  = connection.createStatement();
			String sql = "UPDATE ESCOLAS SET NOME=? WHERE ID=?";
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
