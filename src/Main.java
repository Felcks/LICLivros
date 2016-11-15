import gui.GUIManager;
import java.sql.*;

import bd.JavaConnection;
public class Main {
	public static void main(String[] args) {
		new GUIManager();
		Connection connection = null;
		connection = JavaConnection.ConnectBd();
	}

}
