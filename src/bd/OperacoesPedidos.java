package bd;

import java.sql.*;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Pedido;
import principais.PedidoManager;

public class OperacoesPedidos extends JavaConnection implements Operacoes
{
	private Statement stmt;
	
	public void INSERT_DATA(Object obj) {
		Pedido pedido = (Pedido)obj;
		
		try{
				ConnectBd();
				connection.setAutoCommit(false);
				stmt  = connection.createStatement();
			
				int id = pedido.getId();
				String cliente = pedido.getCliente().getNome();
				int pacote = pedido.getPacote().getId();
				String idsLivros = pedido.getIdsDosLivrosCompradosEmString();
				String qtdLivros = pedido.getQtdDosLivrosCompradosEmString();
				double preco = pedido.getPreco();
				String formaPagamento = pedido.getFormaDePagamento().toString();
				String obs = pedido.getObs();
				obs = obs.concat(" ");
				String tipo = pedido.getTipoPedido().toString();
				String status = pedido.getStatus().toString();
				String data = pedido.getData();
				
				String sql = "INSERT INTO PEDIDOS (ID, CLIENTE, PACOTE, IDS_DOS_LIVROS, QTD_DOS_LIVROS, PRECO,  "
						+ "FORMA_DE_PAGAMENTO, OBS, STATUS, TIPO, DATA)" +
				"VALUES (" + id + "," + 
						 "'" + cliente + "'" + "," + 
						  pacote  + "," +
						"'" + idsLivros + "'" + "," + 
						"'" + qtdLivros + "'" + "," + 
						preco + "," + 
						"'" + formaPagamento + "'" + "," +
						"'" + obs + "'" + "," +
						"'" + status + "'" + "," +
						"'" + tipo + "'" + "," +
						"'" + data + "'" + ");";
				stmt.executeUpdate(sql);
			
				connection.commit();
				this.closeConnections();
			} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void UPDATE_DATA(Object obj){
		Pedido pedido = (Pedido)obj;
		
		try{
			ConnectBd();
			connection.setAutoCommit(false);
			int id = pedido.getId();
			String status = pedido.getStatus().toString();
			
			stmt  = connection.createStatement();
			String sql = "UPDATE PEDIDOS SET STATUS=? WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, status);
			statement.setInt(2, id);
			
			int a  = statement.executeUpdate();
		     
		    connection.commit();
		    statement.close();
		    this.closeConnections();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void GET_AND_SET_ALL_DATA(){
		try{
			this.ConnectBd();
			stmt = connection.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM PEDIDOS");
			while (resultSet.next()){
				Pedido pedido = new Pedido(resultSet);
				PedidoManager.getInstance().adicionarPedido(pedido);
			}
			this.closeConnections();
		} catch(Exception e){}
	}
		
	
}
