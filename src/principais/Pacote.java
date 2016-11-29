package principais;

import java.util.List;
import java.util.ArrayList;

public class Pacote {
	
	private Escola escola;
	private AnoEscolar anoEscolar;
	private List<Livro> livros;
	
	public Pacote(Escola escola, AnoEscolar anoEscolar, List<Livro> livros){
		this.escola = escola;
		this.anoEscolar = anoEscolar;
		this.livros =  livros;
	}
	
	public Escola getEscola(){
		return this.escola;
	}
	
	public AnoEscolar getAnoEscolar(){
		return this.anoEscolar;
	}
	
	public List<Livro> getLivros(){
		return this.livros;
	}
	
	public int adicionarLivro(Livro livro){
		for(int i = 0; i < this.livros.size(); i++){
			if(this.livros.contains(livro)){
				return -1;
			}
		}
		this.livros.add(livro);
		
		return 0;
	}
}
