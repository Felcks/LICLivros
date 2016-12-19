package utilidades;

public enum StatusDaEntrega {
	ENTREGUE("Entregue"), NÂO_ENTREGUE("Não entregue"), AGUARDANDO_BUSCA("Aguardando a busca"), CANCELADO("Cancelado");
	
	
	private String nome;
	
	StatusDaEntrega(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public static String[] getTodosNomesStatus(){
		String[] todosNomes = new String[StatusDaEntrega.values().length]; 
		for(int i = 0; i < StatusDaEntrega.values().length; i++){
			todosNomes[i] = StatusDaEntrega.values()[i].getNome();
		}
		return todosNomes;
	}
	
	public static StatusDaEntrega getStatusPeloNome(String nome){
		StatusDaEntrega st = StatusDaEntrega.NÂO_ENTREGUE;
		for(int i = 0; i < StatusDaEntrega.values().length; i++){
			if(StatusDaEntrega.values()[i].getNome().equals(nome)){
				st = StatusDaEntrega.values()[i];
				break;
			}
		}
		return st;
	}
}
