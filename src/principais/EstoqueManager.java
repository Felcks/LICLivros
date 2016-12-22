package principais;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.Operacoes;
import bd.OperacoesLivros;
import principais.Livro;

public class EstoqueManager {
	private static EstoqueManager estoqueManager;
	private List<Livro> livros;
	private Operacoes operacoes;
	
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
		return this.livros.get(id);
	}
	
	public void removerLivro(int id){
		this.livros.remove(id);
	}
	
	public Operacoes getOperacoes(){
		return this.operacoes;
	}
	
	public void atualizarLivro(int index, Livro livro){
		this.livros.set(index, livro);
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
	
	public void getLivrosDoBancoDeDados(){
		this.livros.clear();
		
		operacoes.GET_AND_SET_ALL_DATA();	
	}
	public List<Livro> getLivrosDeUmaEditora(String editora){
		List<Livro> livrosDeEditora = new ArrayList<Livro>();
		for(int i = 0; i < this.getLivros().size(); i++){
			if(this.getLivros().get(i).getEditora().equals(editora)){
				livrosDeEditora.add(this.getLivros().get(i));
			}
		}

		return livrosDeEditora;
	}
	
	public String[] getTodosOsNomesDosLivros(){
		String[] todosOsNomesDosLivros = new String[this.livros.size()];
		for(int i = 0; i < todosOsNomesDosLivros.length; i++){
			todosOsNomesDosLivros[i] = this.livros.get(i).getNome();
		}
		
		return todosOsNomesDosLivros;
	}
	
	public int gerarId(Editora editora){
		int id = 0;
		
		//id = getLivrosDeUmaEditora(editora).size() + editora.getIdInicial();
		
		return 0;
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
		
		if(livro.getQuantidade() > 0)
			livro.setQuantidade(livro.getQuantidade() - 1);
		else
			livro.setComprar(livro.getComprar() + 1);
		
		livro.setVendidos(id);
		
		this.operacoes.UPDATE_DATA(livro);
	}
	
	public void adicionarDoEstoque(int[] id){
		for(int i = 0; i < id.length; i++)
		{
			Livro livro = this.getLivroPeloId(id[i]);
			livro.setComprar(livro.getComprar() - 1);
			livro.setVendidos(livro.getVendidos() + 1);
			
			this.operacoes.UPDATE_DATA(livro);
		}
	}
}
