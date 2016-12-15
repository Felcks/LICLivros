package utilidades;

public enum StatusDaEntrega {
	ENTREGUE("Entregue"), NÂO_ENTREGUE("Não entregue"), AGUARDANDO_BUSCA("Aguardando a busca");
	
	
	private String nome;
	
	StatusDaEntrega(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
}
