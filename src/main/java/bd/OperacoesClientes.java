package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
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
			String nome = cliente.getNome();
			String bairro = cliente.getBairro();
			String rua = cliente.getRua();
			String complemento = cliente.getComplemento();
			String telefone = cliente.getTelefone();
			String celular = cliente.getCelular();
			
			stmt  = connection.createStatement();
			String sql = "INSERT INTO CLIENTES (NOME, BAIRRO, RUA, COMPLEMENTO, TELEFONE, CELULAR)" +
			"VALUES ('" + nome + "'" + "," +
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

	public List<String> GET_ALL_NOMES(){

		ArrayList<String> nomes = new ArrayList<String>();

		try{
			this.ConnectBd();
			stmt = connection.createStatement();

			ResultSet resultSet = stmt.executeQuery("SELECT * FROM CLIENTES ORDER BY NOME ASC");
			while (resultSet.next()){

				String nome = resultSet.getString("NOME");
				nomes.add(nome);
			}
			this.closeConnections();

		} catch(Exception e){}

		return nomes;
	}

	public List<Cliente> GET_ALL(){

		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		try{
			this.ConnectBd();
			stmt = connection.createStatement();

			ResultSet resultSet = stmt.executeQuery("SELECT * FROM CLIENTES ORDER BY NOME ASC");
			while (resultSet.next()){
				Cliente cliente = new Cliente(resultSet);
				clientes.add(cliente);
			}
			this.closeConnections();
			return clientes;

		} catch(Exception e){}

		return clientes;
	}

	public Cliente GET_BY_ID(int id){

		Cliente cliente = null;
		try{
			this.ConnectBd();
			stmt = connection.createStatement();

			ResultSet resultSet = stmt.executeQuery("SELECT * FROM CLIENTES WHERE ID = " + id);
			while (resultSet.next()){
				cliente = new Cliente(resultSet);
				break;
			}
			this.closeConnections();

		} catch(Exception e){}

		return cliente;

	}
}