package utilidades;

public enum StatusDoPagamento {

	 NAO_PAGO("Não pago"), PAGO("Pago");
	
	private String nome;
	
	StatusDoPagamento(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
}
