package principais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.JavaConnection;
import bd.Operacoes;
import bd.OperacoesPacotes;

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
	OperacoesPacotes operacoes;
	
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
		this.operacoes = new OperacoesPacotes();
	}
	
	public Operacoes getOperacoes(){
		return this.operacoes;
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
	
	public void adicionarNovoPacote(Pacote pacote){
		this.pacotes.add(pacote);
	}
	
	public void getTodosOsPacotesDoBD(){
		this.pacotes.clear();
		
		operacoes.GET_AND_SET_ALL_DATA();
	}
	
	public Pacote getPacote(int escola, AnoEscolar anoEscolar){
		//Pacote pacote = new Pacote(-1, -1, anoEscolar);
		Pacote pacote = operacoes.GET_PACOTE(escola, anoEscolar.toString());
		if(pacote == null){
			pacote = new Pacote(-1, escola, anoEscolar);
			this.operacoes.INSERT_DATA_RETORNO_ID(pacote);
			this.getPacote(escola, anoEscolar);
		}

		/*for(int i = 0; i < this.pacotes.size(); i++){
			if(escola == this.pacotes.get(i).getEscolaId() && anoEscolar == this.pacotes.get(i).getAnoEscolar()){
				pacote = this.pacotes.get(i);
			}
		}
		
		if(pacote.getEscolaId() == -1){
			pacote = new Pacote(-1, escola, anoEscolar);
			this.pacotes.add(pacote);
            pacote.setId(this.operacoes.INSERT_DATA_RETORNO_ID(pacote));
		}*/
			
		return pacote;
	}

}
