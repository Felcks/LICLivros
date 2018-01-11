import gui.GUIManager;
import utilidades.Print;
import utilidades.Screen;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import java.io.File;
import java.io.FileOutputStream;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import java.text.SimpleDateFormat;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt", "BR"));
		Screen.start();
		try {
			//Print.getInstance().printDocument(0);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// INICIAR TEMA
		try {
            // AQUI INFORMO A CLASSE DO LOOK_AND_FEEL NIMBUS
            String nimbus = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";

            // AQUI SETO O LOOK_AND_FEEL
            UIManager.setLookAndFeel(nimbus);

            //AQUI CHAMA SEU FORM PRINCIPAL
	    }
        catch (ClassNotFoundException e) {
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        } catch (UnsupportedLookAndFeelException e) {
        } 
	 
		
		new GUIManager();
		 
	}
}
