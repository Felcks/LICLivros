package utilidades;

import principais.AnoEscolar;

public enum FormaDePagamento {
	DINHEIRO("Dinheiro"), CARTAO_1x("Cartão 1x"), CARTAO_2x("Cartão 2x"), CARTAO_3x("Cartão 3x"), 
	CARTAO_4x("Cartão 4x"), CARTAO_5x("Cartão 5x"), CARTAO_6x("Cartão 6x");
	
	String nome;
	
	FormaDePagamento(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public static FormaDePagamento getFormaDePagamentoPeloNome(String nome){
		FormaDePagamento forma = FormaDePagamento.DINHEIRO;
		for(int i = 0, n = FormaDePagamento.values().length; i < n; i++){
			if(nome.equals(FormaDePagamento.values()[i].getNome()))
				forma = FormaDePagamento.values()[i];
		}
		
		return forma;
	}
}
