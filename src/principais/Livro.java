package principais;

import java.sql.*;

public class Livro {
	
	private int id;
	private String nome;
	private Editora editora;
	private int quantidade;
	private int comprar;
	private double preco;
	
	public Livro(String nome, Editora editora, int id){
		this.setNome(nome);
		this.setEditora(editora);
		this.setId(id);
	}
	
	public Livro(String nome, Editora editora){
		this.setNome(nome);
		this.setEditora(editora);
	}
	
	public Livro(ResultSet rs)
	{
		try{
			int id = rs.getInt("ID");
			this.setId(id);
			String nome = rs.getString("NOME");
			this.setNome(nome);
			String editora = rs.getString("EDITORA");
			System.out.println(editora);
			this.setEditora(Editora.getEditoraDeUmaString(editora));
			int quantidade = rs.getInt("QUANTIDADE");
			this.setQuantidade(quantidade);
			int comprar = rs.getInt("COMPRAR");
			this.setComprar(comprar);
			double preco = rs.getDouble("PRECO");
			this.setPreco(preco);
		}catch(Exception e){}
	}
	
	public Livro(int id, String nome, Editora editora, int quantidade, int comprar, double preco){
		this.setId(id);
		this.setNome(nome);
		this.setEditora(editora);
		this.setQuantidade(quantidade);
		this.setComprar(comprar);
		this.setPreco(preco);
	}
	
	public Object[] pegarTodosParametros(){
		Object[] todosParametros = new Object[6];
		todosParametros[0] = this.getId();
		todosParametros[1] = this.getNome();
		todosParametros[2] = this.getEditora().getNome();
		todosParametros[3] = this.getQuantidade();
		todosParametros[4] = this.getComprar();
		todosParametros[5] = this.getPreco();
		return todosParametros;
	}
	
	public Object[] pegarParametrosDePacote(){
		Object[] parametrosDePacote = new Object[4];
		parametrosDePacote[0] = this.getId();
		parametrosDePacote[1] = this.getNome();
		parametrosDePacote[2] = this.getEditora().getNome();
		parametrosDePacote[3] = this.getPreco();
		return parametrosDePacote;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	public Editora getEditora() {
		return editora;
	}
	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public void setEditoraComoString(String s){
		for(int i = 0; i < Editora.values().length;  i++){
			if(s.equals(Editora.values()[i])){
				this.editora = Editora.values()[i];
			}
		}
	}

	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}


	public int getComprar() {
		return comprar;
	}
	public void setComprar(int comprar) {
		this.comprar = comprar;
	}

	public double getPreco(){
		return this.preco;
	}
	public void setPreco(double preco){
		this.preco = preco;
	}
}
