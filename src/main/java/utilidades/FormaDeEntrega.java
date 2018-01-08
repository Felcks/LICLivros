package utilidades;

import javax.swing.plaf.synth.SynthSeparatorUI;

public enum FormaDeEntrega {
	EM_DOMICILIO("Em domicílio"), BUSCAR("Buscar");
	
	String nome;
	
	FormaDeEntrega(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public static FormaDeEntrega getFormaDeEntregaPeloNome(String nome){
		FormaDeEntrega forma = FormaDeEntrega.EM_DOMICILIO;
		
		System.out.println("forma de entrega" + nome);
		for(int i = 0, n = FormaDeEntrega.values().length; i < n; i++){
			if(nome.equals(FormaDeEntrega.values()[i].getNome()))
				forma = FormaDeEntrega.values()[i];
		}
		
		return forma;
	}
}
