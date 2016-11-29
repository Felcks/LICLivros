package bd;

import java.sql.*;

import javax.swing.JOptionPane;

import principais.Editora;
import principais.Livro;

public class OperacoesLivros 
{
	private Statement stmt;
	private Connection conn = null;
	
	public void INSERT_LIVROS(Livro livro) {
		try{
			conn = JavaConnection.getInstance().connection;
			conn.setAutoCommit(false);
			int id = livro.getId();
			String nome = livro.getNome();
			String editora = livro.getEditora().getNome();
			int quantidade = livro.getQuantidade();
			int comprar = livro.getComprar();
			double preco = livro.getPreco();
			
			stmt  = conn.createStatement();
			String sql = "INSERT INTO LIVROS (ID, NOME, EDITORA, QUANTIDADE, COMPRAR, PRECO)" +
			"VALUES (" + id + "," + 
					"'" + nome + "'" + "," + 
					"'" + editora + "'" + "," +
					quantidade + "," + 
					comprar + "," + 
					preco + ");";
			stmt.executeUpdate(sql);
		     
		    conn.commit();
			stmt.close();
			conn.close();
			} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void UPADTE_LIVROS(Livro livro)
	{
		try{
			conn = JavaConnection.getInstance().connection;
			conn.setAutoCommit(false);
			int id = livro.getId();
			String nome = livro.getNome();
			String editora = livro.getEditora().getNome();
			int quantidade = livro.getQuantidade();
			int comprar = livro.getComprar();
			double preco = livro.getPreco();
			
			stmt = conn.createStatement();
			String sql = "UPDATE LIVROS set NOME = " + "'" + nome + "'," +
			"EDITORA = " + "'" + editora + "'," +
			"QUANTIDADE = " + quantidade + "," +
			"COMPRAR = " + comprar + "," +
			"PRECO = " + preco + " " +
			"WHERE ID = " + id +";";
			
			stmt.executeUpdate(sql);
			conn.commit();
			stmt.close();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
