package principais;

public enum Escola {
	ESCOLA_1("Escola 1"), ESCOLA_2("Escola 2");
	
	private String nome;
	
	Escola(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
}
