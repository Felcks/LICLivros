package bd;

import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Cliente;

public class OperacoesClientes extends JavaConnection{
	
	private Statement stmt;

	public void INSERT_CLIENTES(Cliente cliente){
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
	
	public void INSERT_TODOSCLIENTES(List<Cliente> clientes){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();
			String delete = "DELETE FROM CLIENTES";
			stmt.executeUpdate(delete);
			
			for(Cliente c : clientes){
				int id = c.getId();
				String nome = c.getNome();
				String bairro = c.getBairro();
				String rua = c.getRua();
				String complemento = c.getComplemento();
				String telefone = c.getTelefone();
				String celular = c.getCelular();
				
				String sql = "INSERT INTO CLIENTES (ID, NOME, BAIRRO, RUA, COMPLEMENTO, TELEFONE, CELULAR)" +
				"VALUES (" + id + "," + 
						"'" + nome + "'" + "," + 
						"'" + bairro + "'" + "," +
						"'" + rua + "'" + "," +
						"'" + complemento + "'" + "," + 
						"'" + telefone + "'" +  "," + 
						"'" + celular + "'" + ");";
				stmt.executeUpdate(sql);
			}
		     
		    connection.commit();
			stmt.close();
			connection.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
}