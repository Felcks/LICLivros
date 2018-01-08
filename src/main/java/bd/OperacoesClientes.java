package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Cliente;
import principais.ClienteManager;

public class OperacoesClientes extends JavaConnection implements Operacoes{
	
	public void INSERT_DATA(Object obj){
		Cliente cliente = (Cliente)obj;
		
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
		    this.closeConnections();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void UPDATE_DATA(Object obj){
		Cliente cliente = (Cliente)obj;
		
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
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM CLIENTES");
			while (resultSet.next()){
				Cliente cliente = new Cliente(resultSet);
				ClienteManager.getInstance().adicionarNovoCliente(cliente);
			}
		    this.closeConnections();
		    
		} catch(Exception e){}
	}
}