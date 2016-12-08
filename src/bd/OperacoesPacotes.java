package bd;

import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Pacote;

public class OperacoesPacotes extends JavaConnection
{
	private Statement stmt;

	public void INSERT_TODOSPACOTES(List<Pacote> pacotes){
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();
			String delete = "DELETE FROM PACOTES";
			stmt.executeUpdate(delete);
			
			for(Pacote p : pacotes){
				int id = p.getId();
				String escola = p.getEscola().getNome();
				String ano = p.getAnoEscolar().toString();
				int[] livros = new int[31];
				for(int i = 0; i < p.getLivros().size(); i++)
					livros[i] = p.getLivros().get(i).getId();
				
				livros[p.getLivros().size()] = -1;
				
				int quantidade = 1;
				
				String sql = "INSERT INTO PACOTES (ID, ESCOLA, ANO, LIVRO_0, LIVRO_1, LIVRO_2, LIVRO_3, LIVRO_4, LIVRO_5, LIVRO_6,"
										    	+ "LIVRO_7, LIVRO_8, LIVRO_9, LIVRO_10, LIVRO_11, LIVRO_12, LIVRO_13, LIVRO_14, " 
										    	+ "LIVRO_15, LIVRO_16, LIVRO_17, LIVRO_18, LIVRO_19, LIVRO_20, LIVRO_21, LIVRO_22,"
										    	+ "LIVRO_23, LIVRO_24, LIVRO_25, LIVRO_26, LIVRO_27, LIVRO_28, LIVRO_29)" 
				+ "VALUES (" + id + "," + 
						"'" + escola + "'" + "," + 
						"'" + ano + "'" + "," +
						livros[0] + "," + livros[1] + "," + livros[2] + "," + livros[3] + "," + livros[4] + "," + livros[5] + "," +
						livros[6] + "," + livros[7] + "," + livros[8] + "," + livros[9] + "," + livros[10] + "," + livros[11] + "," +
						livros[12] + "," + livros[13] + "," + livros[14] + "," + livros[15] + "," + livros[16] + "," + livros[17] + "," +
						livros[18] + "," + livros[19] + "," + livros[20] + "," + livros[21] + "," + livros[22] + "," +
						livros[23] + "," + livros[24] + "," + livros[25] + "," + livros[26] + "," + livros[27] + "," +
						livros[28] + "," + livros[29] + ");";
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
