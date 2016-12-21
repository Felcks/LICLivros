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
				int cliente = pedido.getCliente().getId();
				int pacote = pedido.getPacote().getId();
				String idsLivros = pedido.getIdsDosLivrosCompradosEmString();
				double preco = pedido.getPreco();
				String formaEntrega = pedido.getFormaDeEntrega().toString();
				String formaPagamento = pedido.getFormaDePagamento().toString();
				String obs = pedido.getTipoPedido().toString();
				//obs = obs.concat(" ");
				String status = pedido.getStatus().toString();
				String statusDoPagamento = pedido.getStatusDoPagamento().toString();
				String statusDaEntrega = pedido.getStatusDaEntrega().toString();
				String data = pedido.getData();
				
				String sql = "INSERT INTO PEDIDOS (ID, CLIENTE, PACOTE, IDS_DOS_LIVROS, PRECO, FORMA_DE_ENTREGA, "
						+ "FORMA_DE_PAGAMENTO, OBS, STATUS, STATUS_DA_ENTREGA, STATUS_DO_PAGAMENTO, DATA)" +
				"VALUES (" + id + "," + 
						  cliente + "," + 
						  pacote  + "," +
						"'" + idsLivros + "'" + "," + 
						  preco + "," + 
						"'" + formaEntrega + "'" + "," +
						"'" + formaPagamento + "'" + "," +
						"'" + obs + "'" + "," +
						"'" + status + "'" + "," +
						"'" + statusDaEntrega + "'" + "," +
						"'" + statusDoPagamento + "'" + "," + 
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
			String statusEntrega = pedido.getStatusDaEntrega().toString();
			String statusPagamento = pedido.getStatusDoPagamento().toString();
			String status = pedido.getStatus().toString();
			
			stmt  = connection.createStatement();
			String sql = "UPDATE PEDIDOS SET STATUS_DA_ENTREGA=?, STATUS_DO_PAGAMENTO=?, STATUS=? WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, statusEntrega);
			statement.setString(2, statusPagamento);
			statement.setString(3, status);
			statement.setInt(4, id);
			
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
