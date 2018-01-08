package principais;

import java.sql.ResultSet;

public class Escola {
	private int id;
	private String nome;
	
	public Escola(ResultSet rs){
		try{
			int id = rs.getInt("ID");
			this.setId(id);
			String nome = rs.getString("NOME");
			this.setNome(nome);
		}
		catch (Exception e){}
	}
	
	public Escola(){}
	
	public Escola(int id, String nome){
		this.setId(id);
		this.setNome(nome);
	}
	
	public Escola(String nome){
		this.setNome(nome);
	}
	
	public Object[] pegarTodosParametros(){
		Object[] object = new Object[2];
		object[0] = this.getId();
		object[1] = this.getNome();
		
		return object;
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
	
	
}
