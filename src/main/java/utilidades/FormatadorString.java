package utilidades;

import java.text.Normalizer;

public class FormatadorString {

    public static String tirarAcentoColocarCaixaAlta(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        s = s.toUpperCase();
        return s;
    }
}
