package principais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.JavaConnection;

/**
 * Esquema parecido com a EstoqueManager
 * Pega a dataBase do Ano e da Escola selecionada
 * 
 * @author felcks
 *
 */

public class PacoteManager {
	
	private static PacoteManager pacoteManager;
	private List<Pacote> pacotes;
	private Statement stmt;
	private Connection conn = null;
	
	public static PacoteManager getInstance(){
		if(pacoteManager == null)
			pacoteManager = new PacoteManager();
		
		return pacoteManager;
	}
	
	public List<Pacote> getPacotes(){
		return this.pacotes;
	}
	
	private PacoteManager(){
		this.pacotes = new ArrayList<Pacote>();
	}
	
	public Pacote getPacotePeloId(int id){
		Pacote p = new Pacote();
		for(int i = 0; i < this.pacotes.size(); i++){
			if(this.pacotes.get(i).getId() == id){
				p = this.pacotes.get(i);
				break;
			}
		}
		
		return p;
	}
	
	public void getTodosOsPacotesDoBD(){
		try{
			JavaConnection.getInstance().ConnectBd();
			conn = JavaConnection.getInstance().connection;
			stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM PACOTES");
			this.pacotes.clear();
			while (resultSet.next()){
				Pacote pacote = new Pacote(resultSet);
				this.pacotes.add(pacote);
			}
			resultSet.close();
			stmt.close();
		} catch(Exception e){}
	}
	
	public Pacote getPacote(Escola escola, AnoEscolar anoEscolar){
		Pacote pacote = new Pacote(-1, new Escola("EscolaInexistente"), anoEscolar, new ArrayList<Livro>());
	
		for(int i = 0; i < this.pacotes.size(); i++){
			if(escola.getNome().equals(this.pacotes.get(i).getEscola().getNome()) && anoEscolar == this.pacotes.get(i).getAnoEscolar()){
				pacote = this.pacotes.get(i);
			}
		}
		
		if(pacote.getEscola().getNome().equals("EscolaInexistente")){
			pacote = new Pacote(this.pacotes.size(), escola, anoEscolar, new ArrayList<Livro>());
			this.pacotes.add(pacote);
		}
			
		return pacote;
	}

}
