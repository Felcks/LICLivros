package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Editora;
import principais.Escola;
import principais.EscolaManager;

public class OperacoesEscolas extends JavaConnection implements Operacoes {

	public void INSERT_DATA(Object obj){
		Escola escola = (Escola)obj;
		
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();
			
			//int id = escola.getId();
			String nome = escola.getNome();
			
			String sql = "INSERT INTO ESCOLAS (NOME)" +
			"VALUES ('" + nome + "'" + ");";
			
			stmt.executeUpdate(sql);
			
		     
		    connection.commit();
			stmt.close();
			connection.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void UPDATE_DATA(Object obj){
		Escola escola = (Escola)obj;
		
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			int id = escola.getId();
			String nome = escola.getNome();
			
			stmt  = connection.createStatement();
			String sql = "UPDATE ESCOLAS SET NOME=? WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setInt(2, id);
			
			int a  = statement.executeUpdate();
			
			connection.commit();
			stmt.close();
			connection.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void GET_AND_SET_ALL_DATA(){
		try{
			this.ConnectBd();
			stmt = connection.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM ESCOLAS WHERE NOME != '' ");
			
			while (resultSet.next()){
				Escola escola = new Escola(resultSet);
				EscolaManager.getInstance().adicionarNovaEscola(escola);
			}
			if(EscolaManager.getInstance().getEscolas().size() == 0)
			{
				Escola escola = new Escola(0, "ESCOLA PADRÃO");
				INSERT_DATA(escola);
				EscolaManager.getInstance().adicionarNovaEscola(escola);
			}
			
			this.closeConnections();
		} catch(Exception e){}
	
	}

}
