import gui.GUIManager;
import utilidades.Screen;

import java.sql.*;
import java.util.Locale;

import bd.JavaConnection;
public class Main {
	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt", "BR"));
		Screen.start();
		new GUIManager();
		//JavaConnection.getInstance().ConnectBd();
	}

}
