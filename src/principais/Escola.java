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
	
	public static Escola getEscolaDeUmaString(String valor){
		Escola escola = Escola.ESCOLA_1;
		for(int i = 0; i < Escola.values().length; i++){
			if(valor == Escola.values()[i].getNome()){
				escola = Escola.values()[i];
			}
		}
		
		return escola;
	}
}
