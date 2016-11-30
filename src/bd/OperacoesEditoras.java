package bd;

import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Cliente;
import principais.Editora;

public class OperacoesEditoras extends JavaConnection {

	private Statement stmt;
	
	public void INSERT_TODOSCLIENTES(List<Editora> editoras){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();
			String delete = "DELETE FROM EDITORAS";
			stmt.executeUpdate(delete);
			
			for(Editora ed : editoras){
				int id = ed.getId();
				String nome = ed.getNome();
				
				String sql = "INSERT INTO EDITORAS (ID, NOME)" +
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
