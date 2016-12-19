package bd;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Cliente;

public class OperacoesClientes extends JavaConnection{
	
	private Statement stmt;

	public void INSERT_CLIENTE(Cliente cliente){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			int id = cliente.getId();
			String nome = cliente.getNome();
			String bairro = cliente.getBairro();
			String rua = cliente.getRua();
			String complemento = cliente.getComplemento();
			String telefone = cliente.getTelefone();
			String celular = cliente.getCelular();
			
			stmt  = connection.createStatement();
			String sql = "INSERT INTO CLIENTES (ID, NOME, BAIRRO, RUA, COMPLEMENTO, TELEFONE, CELULAR)" +
			"VALUES (" + id + "," + 
					"'" + nome + "'" + "," + 
					"'" + bairro + "'" + "," +
					"'" + rua + "'" + "," +
					"'" + complemento + "'" + "," + 
					"'" + telefone + "'" +  "," + 
					"'" + celular + "'" + ");";
			stmt.executeUpdate(sql);
		     
		    connection.commit();
			stmt.close();
			connection.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void UPDATE_CLIENTE(Cliente cliente){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			int id = cliente.getId();
			String nome = cliente.getNome();
			String bairro = cliente.getBairro();
			String rua = cliente.getRua();	
			String complemento = cliente.getComplemento();
			String telefone = cliente.getTelefone();
			String celular = cliente.getCelular();
			
			stmt  = connection.createStatement();
			String sql = "UPDATE CLIENTES SET NOME=?, BAIRRO=?, RUA=?, COMPLEMENTO=?, TELEFONE=?, CELULAR=? WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setString(2, bairro);
			statement.setString(3, rua);
			statement.setString(4, complemento);
			statement.setString(5, telefone);
			statement.setString(6, celular);
			statement.setInt(7, id);
			
			int a  = statement.executeUpdate();
		     
		    connection.commit();
			stmt.close();
			connection.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
}