package principais;

import java.text.Normalizer;
import java.util.Comparator;

public class NomeEditoraComparator implements Comparator<Editora> 
{
	public int compare(Editora arg0, Editora arg1) {
		// TODO Auto-generated method stub
		CharSequence cs0 = arg0.getNome().toUpperCase();
		String nome0 = Normalizer.normalize(cs0, Normalizer.Form.NFKD);
		
		CharSequence cs1 = arg1.getNome().toUpperCase();
		String nome1 = Normalizer.normalize(cs1, Normalizer.Form.NFKD);
		
		return nome0.compareToIgnoreCase(nome1);
	}
}

