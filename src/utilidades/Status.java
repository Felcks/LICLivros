package utilidades;

public enum Status {
	EM_ANDAMENTO("Em andamento"), PRONTO("Pronto!"), CANCELADO("Cancelado");
	
	private String nome;
	
	Status(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
}
