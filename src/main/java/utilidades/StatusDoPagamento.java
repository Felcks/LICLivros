package utilidades;

public enum StatusDoPagamento {

	 NAO_PAGO("Não pago"), PAGO("Pago"), CANCELADO("Cancelado");
	
	private String nome;
	
	StatusDoPagamento(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public static String[] getTodosNomesStatus(){
		String[] todosNomes = new String[StatusDoPagamento.values().length]; 
		for(int i = 0; i < StatusDoPagamento.values().length; i++){
			todosNomes[i] = StatusDoPagamento.values()[i].getNome();
		}
		return todosNomes;
	}
	
	public static StatusDoPagamento getStatusPeloNome(String nome){
		StatusDoPagamento st = StatusDoPagamento.NAO_PAGO;
		for(int i = 0; i < StatusDoPagamento.values().length; i++){
			if(StatusDoPagamento.values()[i].getNome().equals(nome)){
				st = StatusDoPagamento.values()[i];
				break;
			}
		}
		return st;
	}
}
