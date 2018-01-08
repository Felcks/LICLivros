package principais;

import java.sql.ResultSet;

public class Editora {
	private String nome;
	private int id;
	
	public Editora(int id, String nome){
		this.nome = nome;
		this.id = id;
	}
	
	public Editora(String nome){
		this.setNome(nome);
	}
	
	public Editora(){}
	
	public String getNome(){
		return this.nome;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public int getId(){
		return this.id;
	}
	public void setId(int id){	
		this.id = id;
	}
	
	public Editora(ResultSet rs){
		try{
			int id = rs.getInt("ID");
			this.setId(id);
			String nome = rs.getString("NOME");
			this.setNome(nome);
		}
		catch (Exception e){}
	}
	
	public Object[] pegarTodosParametros(){
		Object[] object = new Object[2];
		object[0] = this.getId();
		object[1] = this.getNome();
		
		return object;
	}
}
