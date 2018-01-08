package utilidades;

public enum Status {
	EM_ANDAMENTO("EM ANDAMENTO"), PRONTO("PRONTO"), CANCELADO("CANCELADO");
	
	private String nome;
	
	Status(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
}
