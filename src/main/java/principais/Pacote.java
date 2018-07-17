package principais;

import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Pacote {
	
	private int escolaId;
	private AnoEscolar anoEscolar;
	private int id;
	private List<Livro> livros;
	
	public Pacote(int id, int escola, AnoEscolar anoEscolar){
		this.setId(id);
		this.escolaId = escola;
		this.anoEscolar = anoEscolar;
		this.livros = new ArrayList<Livro>();
	}
	
	public Pacote(){}
	
	public Pacote(ResultSet rs){
		this.livros = new ArrayList<Livro>();
		try{
			int id = rs.getInt("ID");
			this.setId(id);
			int escolaId = rs.getInt("ESCOLA");
			this.setEscolaId(escolaId);
			AnoEscolar anoEscolar = AnoEscolar.valueOf(rs.getString("ANO"));
			this.setAnoEscolar(anoEscolar);
		}
		catch (Exception e){}
	}
	
	public Livro getLivroPeloId(int id){
		Livro livro = new Livro("LivroInexistente");
		
		for(int i = 0; i < this.livros.size(); i++){
			if(this.livros.get(i).getId() == id)
				livro = this.livros.get(i);
		}
		
		return livro;
	}

	public int getEscolaId() {
		return escolaId;
	}

	public void setEscolaId(int escolaId) {
		this.escolaId = escolaId;
	}

	public AnoEscolar getAnoEscolar(){
		return this.anoEscolar;
	}
	public void setAnoEscolar(AnoEscolar anoEscolar){
		this.anoEscolar = anoEscolar;
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
	
	public void removerLivro(Livro livro){
		for(int i = 0; i < this.livros.size(); i++){
			if(livro.getId() == this.livros.get(i).getId()){
				this.livros.remove(i);
				break;
			}
		}
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
