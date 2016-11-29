import gui.GUIManager;
import utilidades.Screen;

import java.sql.*;
import java.util.Locale;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import bd.JavaConnection;
public class Main {
	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt", "BR"));
		Screen.start();
		//JavaConnection.getInstance().ConnectBd();
		
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
