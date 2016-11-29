package principais;

public enum Editora {
	ED_ATICA("Editora Ã�tica", 0), EDITORA_CONSTRUIR("Editora Construir", 100), EDITORA_DO_BRASIL("Editora do Brasil", 200);
	
	private String nome;
	private final int idInicial;
	
	Editora(String nome, int idInicial){
		this.nome = nome;
		this.idInicial = idInicial;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public int getIdInicial(){
		return this.idInicial;
	}
	
	public static Editora getEditoraDeUmaString(String valor){
		Editora editora = Editora.ED_ATICA;
		for(int i = 0; i < Editora.values().length; i++){
			if(valor.equalsIgnoreCase(Editora.values()[i].getNome())){
				editora = Editora.values()[i];
			}
		}
		
		return editora;
	}
}
