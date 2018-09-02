package bd;

import java.sql.*;
import java.util.ArrayList;
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
			int reservado = livro.getReservado();
			int vendidos = livro.getVendidos();
			double preco = livro.getPreco();

			String sql = "INSERT INTO LIVROS (NOME, EDITORA, QUANTIDADE, RESERVADO, VENDIDOS, PRECO)" +
			"VALUES ('" + nome + "'" + "," +
					"'" + editora + "'" + "," +
					quantidade + "," +
					reservado + "," +
					vendidos + "," +
					preco + ");";

			stmt.executeUpdate(sql);
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
			int reservado = livro.getReservado();
			int vendidos = livro.getVendidos();
			double preco = livro.getPreco();
			
			stmt = connection.createStatement();
			String sql = "UPDATE LIVROS set NOME = " + "'" + nome + "'," +
			"EDITORA = " + "'" + editora + "'," +
			"QUANTIDADE = " + quantidade + "," +
			"RESERVADO = " + reservado + "," +
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
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM LIVROS WHERE NOME != '' ");
			while (resultSet.next()){
				Livro livro = new Livro(resultSet);
				EstoqueManager.getInstance().adicionarNovoLivro(livro);
			}
			closeConnections();
		}catch(Exception e){}
	}

	public void ATUALIZAR_NOME_DE_EDITORA(String velho, String novo){

		try{
			this.ConnectBd();
			connection.setAutoCommit(false);


			stmt = connection.createStatement();
			String sql = "UPDATE LIVROS set EDITORA = " + "'" + novo + "'" +
					" WHERE EDITORA = '" + velho + "'";

			stmt.executeUpdate(sql);
			connection.commit();
			this.closeConnections();

		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}

    public ArrayList<Livro> GET_ALL_LIVROS_VENDIDOS(){

	    ArrayList<Livro> livros = new ArrayList<Livro>();
	    try{
            this.ConnectBd();
            stmt = connection.createStatement();

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM LIVROS WHERE NOME != '' AND VENDIDOS > 0 OR " +
                    "NOME != '' AND RESERVADO > QUANTIDADE");
            while (resultSet.next()){
                Livro livro = new Livro(resultSet);
                livros.add(livro);
            }
            closeConnections();
        }catch(Exception e){}

        return livros;
    }

	public ArrayList<Livro> GET_ALL(){

		ArrayList<Livro> livros = new ArrayList<Livro>();
		try{
			this.ConnectBd();
			stmt = connection.createStatement();

			ResultSet resultSet = stmt.executeQuery("SELECT * FROM LIVROS WHERE NOME != '' ORDER BY EDITORA ASC");
			while (resultSet.next()){
				Livro livro = new Livro(resultSet);
				livros.add(livro);
			}
			closeConnections();
		}catch(Exception e){}

		return livros;
	}

	public ArrayList<Livro> GET_ALL_LIVROS_DE_EDITORA(String editora){

		ArrayList<Livro> livros = new ArrayList<Livro>();
		try{
			this.ConnectBd();
			stmt = connection.createStatement();

			ResultSet resultSet = stmt.executeQuery("SELECT * FROM LIVROS WHERE NOME != '' AND EDITORA = '" + editora +
					"' ORDER BY NOME ASC");
			while (resultSet.next()){
				Livro livro = new Livro(resultSet);
				livros.add(livro);
			}
			closeConnections();
		}catch(Exception e){}

		return livros;
	}

    public void RESETAR_VENDIDOS(){

        try{
            this.ConnectBd();
            connection.setAutoCommit(false);


            stmt = connection.createStatement();
            String sql = "UPDATE LIVROS set VENDIDOS = 0, RESERVADO = 0 WHERE NOME != ''";

            stmt.executeUpdate(sql);
            connection.commit();
            this.closeConnections();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
	
}
