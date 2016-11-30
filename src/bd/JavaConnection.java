package bd;

import java.sql.*;
import javax.swing.*;

import principais.EstoqueManager;

public class JavaConnection {
	
	/*COMENTÁRIO DE FELCKS: 
	 * ESSA CLASSE ESTÁ BEM LEGAL. PORÉM ACHO QUE ELA NÃO DEVE SER UM SINGLETON.
	 * A CLASSE OPERACOESCLIENTES ESTÁ HERDANDO DESSA CLASSE E USANDO OS MÉTODOS DE MANEIRA BEM ORIENTADA.
	 * JÁ AS CLASSES OPERAÇÕES E OPERACOESLIVROS USAM A CONNECTION PEGANDO ESSA CONEXÃO COMO SINGLETON.
	 * COMO EU ACHO QUE DEVERIA SER:
	 * 1) ABSTRACT CLASS JAVACONNECTION
	 * 2) Protected Connection connection; 
	 * 3) Protected void ConnectBD();
	 * 4) Tirar toda a parte de sigleton;
	 */
	
	public static JavaConnection javaConnection;
	
	public static JavaConnection getInstance(){
		if(javaConnection == null)
			javaConnection = new JavaConnection();
		
		return javaConnection;
	}
	
	public Connection connection = null;
	public void ConnectBd(){
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:liclivros.sqlite");
			System.out.println("CONEXÃO ESTABELECIDA");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
