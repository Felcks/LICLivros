package principais;

import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Pacote {
	
	private Escola escola;
	private AnoEscolar anoEscolar;
	private int id;
	private List<Livro> livros;
	
	public Pacote(int id, Escola escola, AnoEscolar anoEscolar, List<Livro> livros){
		this.setId(id);
		this.escola = escola;
		this.anoEscolar = anoEscolar;
		this.livros =  livros;
	}
	
	public Pacote(){}
	
	public Pacote(ResultSet rs){
		this.livros = new ArrayList<Livro>();
		try{
			int id = rs.getInt("ID");
			this.setId(id);
			Escola escola = new Escola(rs.getString("ESCOLA"));
			this.setEscola(escola);
			AnoEscolar anoEscolar = AnoEscolar.valueOf(rs.getString("ANO"));
			this.setAnoEscolar(anoEscolar);
			
			for(int i = 0; i < 30; i++){
				int idLivro = -1;
				idLivro = rs.getInt("LIVRO_"+i);
				if(idLivro != -1){
					Livro livro = EstoqueManager.getInstance().getLivroPeloId(idLivro);
					this.livros.add(livro);
				}
				else{
					break;
				}
			}
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
	
	public Escola getEscola(){
		return this.escola;
	}
	public void setEscola(Escola escola){
		this.escola = escola;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
