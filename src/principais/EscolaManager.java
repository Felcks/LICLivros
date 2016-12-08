package principais;

import java.util.List;

import bd.JavaConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EscolaManager {
	
	private List<Escola> escolas;
	private Statement stmt;
	private Connection conn = null;
	private static EscolaManager escolaManager;
	
	private EscolaManager(){
		this.escolas = new ArrayList<Escola>();
	}
	
	public static EscolaManager getInstance(){
		if(escolaManager == null)
			escolaManager = new EscolaManager();
		
		return escolaManager;
	}
	
	public void getTodasEscolasDoBD(){
		try{
			JavaConnection.getInstance().ConnectBd();
			conn = JavaConnection.getInstance().connection;
			stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM ESCOLAS");
			this.escolas.clear();
			while (resultSet.next()){
				Escola escola = new Escola(resultSet);
				this.escolas.add(escola);
			}
			resultSet.close();
			stmt.close();
		} catch(Exception e){}
	}
	
	public void adicionarNovaEscola(Escola escola){
		this.escolas.add(escola);
	}
	public void removerEscola(int index){
		this.escolas.remove(index);
	}
	public void atualizarEscola(int index, Escola escola){
		this.escolas.set(index, escola);
	}
	public void reorganizarLista(){
		for(int i= 0; i < this.escolas.size(); i++){
			this.escolas.get(i).setId(i);
		}
	}
	public List<Escola> getEscolas(){
		return this.escolas;
	}
	
	public String[] getTodosNomesEscolas(){
		String[] nomes = new String[this.escolas.size()];
		for(int i = 0; i < nomes.length; i++){
			nomes[i] = this.escolas.get(i).getNome();
		}
		return nomes;
	}

}
