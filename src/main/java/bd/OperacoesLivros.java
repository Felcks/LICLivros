package bd;

import java.sql.*;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Cliente;
import principais.Editora;
import principais.EstoqueManager;
import principais.Livro;

public class OperacoesLivros extends JavaConnection implements Operacoes 
{
	
	public void INSERT_DATA(Object obj) {
		Livro livro = (Livro)obj;
		
		try{
			this.ConnectBd();
			
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();

			int id = livro.getId();
			String nome = livro.getNome();
			String editora = livro.getEditora();
			int quantidade = livro.getQuantidade();
			int comprar = livro.getComprar();
			int vendidos = livro.getVendidos();
			double preco = livro.getPreco();

			String sql = "INSERT INTO LIVROS (NOME, EDITORA, QUANTIDADE, COMPRAR, VENDIDOS, PRECO)" +
			"VALUES ('" + nome + "'" + "," +
					"'" + editora + "'" + "," +
					quantidade + "," + 
					comprar + "," + 
					vendidos + "," +
					preco + ");";

			stmt.executeUpdate(sql);
			stmt.close();
			connection.commit();
			this.closeConnections();
			
			} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void UPDATE_DATA(Object obj)
	{
		Livro livro = (Livro)obj;
		
		try{
			this.ConnectBd();
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
			this.closeConnections();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void GET_AND_SET_ALL_DATA(){
		try{
			this.ConnectBd();
			stmt = connection.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM LIVROS");
			while (resultSet.next()){
				Livro livro = new Livro(resultSet);
				EstoqueManager.getInstance().adicionarNovoLivro(livro);
			}
			closeConnections();
		}catch(Exception e){}
	}
	
}
