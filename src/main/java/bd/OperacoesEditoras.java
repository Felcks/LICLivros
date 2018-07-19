package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Cliente;
import principais.Editora;
import principais.EditoraManager;
import principais.Pedido;

public class OperacoesEditoras extends JavaConnection implements Operacoes{

	public void INSERT_DATA(Object obj){
		Editora editora = (Editora)obj;
		
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();

			String nome = editora.getNome();
			
			String sql = "INSERT INTO EDITORAS (NOME)" +
			"VALUES ('" + nome + "'" + ");";
			
			stmt.executeUpdate(sql);
			
		     
		    connection.commit();
		    this.closeConnections();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void UPDATE_DATA(Object obj){
		Editora editora = (Editora)obj;
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
			statement.close();
			this.closeConnections();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void GET_AND_SET_ALL_DATA(){
		try{
			this.ConnectBd();
			stmt = connection.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM EDITORAS WHERE NOME != ''");
			while (resultSet.next()){
				Editora editora = new Editora(resultSet);
				EditoraManager.getInstance().adicionarNovaEditora(editora);
			}
			this.closeConnections();
		} catch(Exception e){}
	}

	public Editora GET_EDITORA(String nome){

		Editora editora = null;
		try{
			this.ConnectBd();
			stmt = connection.createStatement();

			ResultSet resultSet = stmt.executeQuery("SELECT * FROM EDITORAS WHERE NOME = '"+ nome + "'");
			while (resultSet.next()){
				editora = new Editora(resultSet);
			}
			this.closeConnections();
		} catch(Exception e){}

		return editora;
	}

}
