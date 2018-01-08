package utilidades;

import javax.swing.JTextField;
public class ServicoDeDigito {
	
	public String[] transformarCamposEmTexto(JTextField[] textFields){
		String[] camposEmTexto = new String[textFields.length];
		for(int i = 0; i < textFields.length; i++)
			camposEmTexto[i] = textFields[i].getText();
		
		return camposEmTexto;
	}
	
	public int transformarStringEmInt(String textoId)  {
		int id = -1;
		try{
			id = Integer.parseInt(textoId);
		}
		catch(NumberFormatException e){ }
		
		return id;
	}
	
	public void limparCampos(JTextField[] textFields){
		for(int i = 0; i < textFields.length; i++)
			textFields[i].setText("");
	}
	public void limparCampos(JTextField[] textFields, int excessao){
		for(int i = 0; i < textFields.length; i++)
			if(i != excessao)
				textFields[i].setText("");
	}
	
	
	
	

}
