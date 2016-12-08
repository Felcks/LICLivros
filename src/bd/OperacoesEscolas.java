package bd;

import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Escola;

public class OperacoesEscolas extends JavaConnection {

	private Statement stmt;
	
	public void INSERT_TODASESCOLAS(List<Escola> escolas){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();
			String delete = "DELETE FROM ESCOLAS";
			stmt.executeUpdate(delete);
			
			for(Escola escola : escolas){
				int id = escola.getId();
				String nome = escola.getNome();
				
				String sql = "INSERT INTO ESCOLAS (ID, NOME)" +
				"VALUES (" + id + "," + 
						"'" + nome + "'" + ");";
				
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
