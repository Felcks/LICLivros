package bd;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Pacote;
import principais.PacoteManager;

public class OperacoesPacotes extends JavaConnection implements Operacoes
{
	
	public void UPDATE_DATA(Object obj)
	{}

	public void INSERT_DATA(Object obj){
		Pacote pacote = (Pacote)obj;
		
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();

			int escolaId = pacote.getEscolaId();
			String ano = pacote.getAnoEscolar().toString();
			
			String sql = "INSERT INTO PACOTES (ESCOLA, ANO)"
			+ "VALUES (" + "'" + escolaId + "'" + "," +
					"'" + ano + "');";

			stmt.executeUpdate(sql);
		    connection.commit();
			stmt.close();
			connection.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public int INSERT_DATA_RETORNO_ID(Object obj){
		Pacote pacote = (Pacote)obj;

		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();

			int escolaId = pacote.getEscolaId();
			String ano = pacote.getAnoEscolar().toString();

			String sql = "INSERT INTO PACOTES (ESCOLA, ANO)"
					+ "VALUES (" + "'" + escolaId + "'" + "," +
					"'" + ano + "');";

			int novoId = stmt.executeUpdate(sql);
			connection.commit();
			stmt.close();
			connection.close();
			return novoId;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}

		return 0;
	}
	
	public void DELETE_PACOTE(Pacote pacote){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();
			
			int id = pacote.getId();
			
			String sql = "DELETE FROM PACOTES WHERE ID = " + id;
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
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM PACOTES");
			while (resultSet.next()){
				Pacote pacote = new Pacote(resultSet);
				PacoteManager.getInstance().adicionarNovoPacote(pacote);
			}
			this.closeConnections();
		} catch(Exception e){}
	}
	
}
