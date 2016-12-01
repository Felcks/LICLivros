package bd;

import java.sql.*;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Cliente;
import principais.Editora;
import principais.Livro;

public class OperacoesLivros extends JavaConnection
{
	private Statement stmt;
	
	public void INSERT_LIVROS(Livro livro) {
		try{
			connection = JavaConnection.getInstance().connection;
			connection.setAutoCommit(false);
			int id = livro.getId();
			String nome = livro.getNome();
			String editora = livro.getEditora();
			int quantidade = livro.getQuantidade();
			int comprar = livro.getComprar();
			double preco = livro.getPreco();
			
			stmt  = connection.createStatement();
			String sql = "INSERT INTO LIVROS (ID, NOME, EDITORA, QUANTIDADE, COMPRAR, PRECO)" +
			"VALUES (" + id + "," + 
					"'" + nome + "'" + "," + 
					"'" + editora + "'" + "," +
					quantidade + "," + 
					comprar + "," + 
					preco + ");";
			stmt.executeUpdate(sql);
		     
			connection.commit();
			stmt.close();
			connection.close();
			} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void INSERT_TODOSLIVROS(List<Livro> livros){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();
			String delete = "DELETE FROM LIVROS";
			stmt.executeUpdate(delete);
			
			for(Livro l : livros){
				int id = l.getId();
				String nome = l.getNome();
				String editora = l.getEditora();
				int quantidade = l.getQuantidade();
				int comprar = l.getComprar();
				double preco = l.getPreco();
				
				String sql = "INSERT INTO LIVROS (ID, NOME, EDITORA, QUANTIDADE, COMPRAR, PRECO)" +
				"VALUES (" + id + "," + 
						"'" + nome + "'" + "," + 
						"'" + editora + "'" + "," +
						"'" + quantidade + "'" + "," +
						"'" + comprar + "'" + "," + 
						"'" + preco + "'" + ");";
				stmt.executeUpdate(sql);
			}
		     
		    connection.commit();
			stmt.close();
			connection.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void UPADTE_LIVROS(Livro livro)
	{
		try{
			connection = JavaConnection.getInstance().connection;
			connection.setAutoCommit(false);
			int id = livro.getId();
			String nome = livro.getNome();
			String editora = livro.getEditora();
			int quantidade = livro.getQuantidade();
			int comprar = livro.getComprar();
			double preco = livro.getPreco();
			
			stmt = connection.createStatement();
			String sql = "UPDATE LIVROS set NOME = " + "'" + nome + "'," +
			"EDITORA = " + "'" + editora + "'," +
			"QUANTIDADE = " + quantidade + "," +
			"COMPRAR = " + comprar + "," +
			"PRECO = " + preco + " " +
			"WHERE ID = " + id +";";
			
			stmt.executeUpdate(sql);
			connection.commit();
			stmt.close();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
