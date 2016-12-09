package utilidades;

import javax.swing.JTextField;
public class ServicoDeDigito {
	
	public String[] transformarCamposEmTexto(JTextField[] textFields){
		String[] camposEmTexto = new String[textFields.length];
		for(int i = 0; i < textFields.length; i++)
			camposEmTexto[i] = textFields[i].getText();
		
		return camposEmTexto;
	}
	
	public Acao conferirAcao(String textoId){
		if(textoId.length() == 0)
			return Acao.ADICIONAR;
		if(textoId.charAt(0) == '-')
			return Acao.REMOVER;
		if(transformarStringEmInt(textoId) != -1)
			return Acao.ATUALIZAR;
		
		return Acao.NENHUMA;
	}
	
	public int transformarStringEmInt(String textoId)  {
		int id = -1;
		try{
			id = Integer.parseInt(textoId);
		}
		catch(NumberFormatException e){ }
		
		return id;
	}
	
	
	
	

}
