package principais;

import java.util.ArrayList;
import java.util.List;

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
	
	public static PacoteManager getInstance(){
		if(pacoteManager == null)
			pacoteManager = new PacoteManager();
		
		return pacoteManager;
	}
	
	private PacoteManager(){
		/*
		 * No construtor desse cara aqui você deve pegar todas as informações da dataBase e transforma-las em pacotes
		 * Lembrando, essa dataBase é constituida de: int id, String Escola, String Ano, int livro1, int livro2, ..., livro30
		 * Esses campos, "livro1, etc" são os IDs dos livros.
		 * Então você deve pegar o livro pertecente(EstoqueManagerMechanicas) a esse id e jogar na classe Pacote =D
		 */
		this.pacotes = new ArrayList<Pacote>();
		
		//Só para teste pelo amor de Deus -- Tirar essa parte -- Ler comentário acima
		List<Livro> livros = new ArrayList<Livro>();
		livros.add(EstoqueManager.getInstance().getLivros().get(0));
		livros.add(EstoqueManager.getInstance().getLivros().get(1));
		
		List<Livro> livros2 = new ArrayList<Livro>();
		livros2.add(EstoqueManager.getInstance().getLivros().get(0));
		
		List<Livro> livros3 = new ArrayList<Livro>();
		livros3.add(EstoqueManager.getInstance().getLivros().get(1));
		
		
		this.pacotes.add(new Pacote(Escola.ESCOLA_1, AnoEscolar.JARDIM_1, livros)); //Esse é para testar a entrada
		this.pacotes.add(new Pacote(Escola.ESCOLA_1, AnoEscolar.JARDIM_2, livros2)); //Esse é para testar - Mudando só a escola se ele vai mudar
		this.pacotes.add(new Pacote(Escola.ESCOLA_2, AnoEscolar.JARDIM_1, livros3)); //Esse é para testar - Mudando só o ano se ele vai trocar
	}
	
	public Pacote getPacote(Escola escola, AnoEscolar anoEscolar){
		Pacote pacote = new Pacote(Escola.ESCOLA_1, AnoEscolar.JARDIM_1, new ArrayList<Livro>());
		for(int i = 0; i < this.pacotes.size(); i++){
			if(escola == this.pacotes.get(i).getEscola() && anoEscolar == this.pacotes.get(i).getAnoEscolar()){
				pacote = this.pacotes.get(i);
			}
		}
		
		return pacote;
	}

}
