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
			int vendidos = livro.getVendidos();
			double preco = livro.getPreco();
			
			stmt  = connection.createStatement();
			String sql = "INSERT INTO LIVROS (ID, NOME, EDITORA, QUANTIDADE, COMPRAR, VENDIDOS, PRECO)" +
			"VALUES (" + id + "," + 
					"'" + nome + "'" + "," + 
					"'" + editora + "'" + "," +
					quantidade + "," + 
					comprar + "," + 
					vendidos + "," +
					preco + ");";
			stmt.executeUpdate(sql);
		     
			connection.commit();
			stmt.close();
			connection.close();
			} catch(Exception e) {
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
			int vendidos = livro.getVendidos();
			double preco = livro.getPreco();
			
			stmt = connection.createStatement();
			String sql = "UPDATE LIVROS set NOME = " + "'" + nome + "'," +
			"EDITORA = " + "'" + editora + "'," +
			"QUANTIDADE = " + quantidade + "," +
			"COMPRAR = " + comprar + "," +
			"PRECO = " + preco + "," +
			"VENDIDOS = " + vendidos  +
			" WHERE ID = " + id ;
			
			stmt.executeUpdate(sql);
			connection.commit();
			stmt.close();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
