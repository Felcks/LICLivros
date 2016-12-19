package utilidades;

import principais.AnoEscolar;

public enum FormaDePagamento {
	DINHEIRO("Dinheiro"), CARTAO("Cartão"), CHEQUE("Cheque");
	
	String nome;
	
	FormaDePagamento(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public static FormaDePagamento getFormaDePagamentoPeloNome(String nome){
		FormaDePagamento forma = FormaDePagamento.DINHEIRO;
		System.out.println("FORMA DE PAGAMENTO" + nome);
		for(int i = 0, n = FormaDePagamento.values().length; i < n; i++){
			if(nome.equals(FormaDePagamento.values()[i].getNome()))
				forma = FormaDePagamento.values()[i];
		}
		
		return forma;
	}
}
