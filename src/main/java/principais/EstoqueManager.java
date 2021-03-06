package principais;
import java.util.ArrayList;
import java.util.List;

import bd.Operacoes;
import bd.OperacoesLivros;

public class EstoqueManager {
	private static EstoqueManager estoqueManager;
	private List<Livro> livros;
	private OperacoesLivros operacoes;
	
	private EstoqueManager(){
		this.livros = new ArrayList<Livro>();
		this.operacoes = new OperacoesLivros();
	}
	
	public static EstoqueManager getInstance(){
		if(estoqueManager == null)
			estoqueManager = new EstoqueManager();
		
		return estoqueManager;
	}
	
	public List<Livro> getLivros(){
		return this.livros;
	}
	
	public Livro getLivroPeloId(int id){

		for(Livro livro : this.livros) {
			if(livro.getId() == id)
				return livro;
		}

		return null;
	}

	private void setLivroPeloId(int id, Livro novoLivro){

		for(int i = 0; i < this.livros.size(); i++){
			Livro livro = this.livros.get(i);
			if(livro.getId() == id)
				this.livros.set(i, novoLivro);
		}
	}
	
	public void removerLivro(int id){
		this.livros.remove(id);
	}
	
	public OperacoesLivros getOperacoes(){
		return this.operacoes;
	}
	
	public void atualizarLivro(int index, Livro livro){

		this.setLivroPeloId(index, livro);
	}
	
	public void adicionarNovoLivro(Livro livro){
		this.livros.add(livro);
	}
	
	public void reorganizarLista(){
		for(int i = 0; i < this.livros.size(); i++){
			this.livros.get(i).setId(i);
		}
	}
	
	public List<String> getTodosLivrosNomes(){
		List<String> nomes = new ArrayList<String>();
		for(int i = 0; i < this.livros.size(); i++)
			nomes.add(this.livros.get(i).getNome());
		
		return nomes;
	}

	public List<Livro> getTodosLivros(){

		/*List<String> nomes = new ArrayList<String>();
		for(int i = 0; i < this.livros.size(); i++)
			nomes.add(this.livros.get(i).getNome());

		return nomes;*/

		return operacoes.GET_ALL();
	}
	
	public void getLivrosDoBancoDeDados(){
		this.livros.clear();
		
		operacoes.GET_AND_SET_ALL_DATA();	
	}
	public List<Livro> getLivrosDeUmaEditora(String editora){

		List<Livro> livrosDeEditora = new ArrayList<Livro>();
		List<Livro> livros = this.operacoes.GET_ALL();
		/*for(int i = 0; i < this.getLivros().size(); i++){
			if(this.getLivros().get(i).getEditora().equals(editora)){
				livrosDeEditora.add(this.getLivros().get(i));
			}
		}*/

		//return livrosDeEditora;
		return operacoes.GET_ALL_LIVROS_DE_EDITORA(editora);
	}
	
	public String[] getTodosOsNomesDosLivros(){
		String[] todosOsNomesDosLivros = new String[this.livros.size()];
		for(int i = 0; i < todosOsNomesDosLivros.length; i++){
			todosOsNomesDosLivros[i] = this.livros.get(i).getNome();
		}
		
		return todosOsNomesDosLivros;
	}
	
	public Livro getLivroPeloNome(String nome){
		System.out.println(nome);
		Livro livro = new Livro("LivroInexistente");
		if(nome.length() > 0){
			nome = nome.toUpperCase();
			String nomeSemUmEspaco = nome.substring(0, nome.length() - 1);
			
			for(int i = 0; i < this.livros.size() ; i++){
				if(nome.equals(this.livros.get(i).getNome().toUpperCase()) || nomeSemUmEspaco.equals(this.livros.get(i).getNome().toUpperCase())){
					livro = this.livros.get(i);
					break;
				}
			}
		}
		
		return livro;
	}
	
	public void retirarDoEstoque(int id){
		Livro livro = this.getLivroPeloId(id);
		livro.setReservado(livro.getReservado() + 1);

		/*
		if(livro.getQuantidade() > 0)
			livro.setQuantidade(livro.getQuantidade() - 1);
		else
			livro.setComprar(livro.getComprar() + 1);*/
		
		
		this.operacoes.UPDATE_DATA(livro);
	}
	
	public void adicionarDoEstoque(int[] id){
		/*for(int i = 0; i < id.length; i++)
		{
			Livro livro = this.getLivroPeloId(id[i]);
			if(livro.getComprar() > 0){
				livro.setComprar(livro.getComprar() - 1);
			}
			livro.setVendidos(livro.getVendidos() + 1);
			this.operacoes.UPDATE_DATA(livro);
		}*/
	}
	
	public void limparAposRelatorioFinal(){

		/*for(int i = 0; i < this.livros.size(); i++){
			Livro livro = livros.get(i);
			//livro.setComprar(0);
			livro.setVendidos(0);
			this.operacoes.UPDATE_DATA(livro);
		}*/

		this.operacoes.RESETAR_VENDIDOS();
	}
}
