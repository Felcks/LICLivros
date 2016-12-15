import gui.GUIManager;
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
		//	createTableTest();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Locale locale = new Locale("pt","BR");
		GregorianCalendar calendar = new GregorianCalendar(); 
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println(calendar.get(Calendar.MONTH));
		System.out.println(calendar.get(Calendar.YEAR));
		System.out.println(calendar.get(Calendar.HOUR));
		System.out.println(calendar.get(Calendar.MINUTE));
		SimpleDateFormat formatador = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy' - 'HH':'mm'h'",locale);
		System.out.println(formatador.format(calendar.getTime()));
		
		
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
/*
	public static void createTableTest() throws Exception{
	
			//Blank Document
			   XWPFDocument document= new XWPFDocument();
			        
			   //Write the Document in file system
			   FileOutputStream out = new FileOutputStream(
			   new File("create_table.docx"));
			        
			   //create table
			   XWPFTable table = document.createTable();
			   //create first row
			   XWPFTableRow tableRowOne = table.getRow(0);
			   tableRowOne.getCell(0).setText("col one, row one");
			   tableRowOne.addNewTableCell().setText("col two, row one");
			   tableRowOne.addNewTableCell().setText("col three, row one");
			   //create second row
			   XWPFTableRow tableRowTwo = table.createRow();
			   tableRowTwo.getCell(0).setText("col one, row two");
			   tableRowTwo.getCell(1).setText("col two, row two");
			   tableRowTwo.getCell(2).setText("col three, row two");
			   //create third row
			   XWPFTableRow tableRowThree = table.createRow();
			   tableRowThree.getCell(0).setText("col one, row three");
			   tableRowThree.getCell(1).setText("col two, row three");
			   tableRowThree.getCell(2).setText("col three, row three");
				
			   document.write(out);
			   out.close();
			   System.out.println("create_table.docx written successully");
	}
	*/
}
