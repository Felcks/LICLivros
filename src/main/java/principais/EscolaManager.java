package principais;

import java.util.List;

import bd.JavaConnection;
import bd.Operacoes;
import bd.OperacoesEscolas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EscolaManager {
	
	private List<Escola> escolas;
	private static EscolaManager escolaManager;
	private Operacoes operacoes;
	
	private EscolaManager(){
		this.escolas = new ArrayList<Escola>();
		this.operacoes = new OperacoesEscolas();
	}
	
	public static EscolaManager getInstance(){
		if(escolaManager == null)
			escolaManager = new EscolaManager();
		
		return escolaManager;
	}
	
	public Operacoes getOperacoes(){
		return this.operacoes;
	}
	
	public void getTodasEscolasDoBD(){
		this.escolas.clear();
		
		this.operacoes.GET_AND_SET_ALL_DATA();
	}
	
	public Escola getEscolaPeloId(int id){
		for(int i = 0; i < this.escolas.size(); i++)
			if(this.escolas.get(i).getId() == id)
				return this.escolas.get(i);
		
		return new Escola();
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
