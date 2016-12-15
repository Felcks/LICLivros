package bd;

import java.sql.*;
import java.util.List;

import javax.swing.JOptionPane;

import principais.Pedido;

public class OperacoesPedidos extends JavaConnection
{
	private Statement stmt;
	
	public void INSERT_PEDIDOS(List<Pedido> pedidos) {
		try{
			ConnectBd();
			connection = JavaConnection.getInstance().connection;
			connection.setAutoCommit(false);
			stmt  = connection.createStatement();
			String delete = "DELETE FROM PEDIDOS";
			stmt.executeUpdate(delete);
			
			for(Pedido pedido : pedidos){
				System.out.println(pedido.getCliente().getNome());
				int id = pedido.getId();
				System.out.println(pedido.getId());
				int cliente = pedido.getCliente().getId();
				int pacote = pedido.getPacote().getId();
				System.out.println(pedido.getPacote());
				String idsLivros = pedido.getIdsDosLivrosCompradosEmString();
				System.out.println(idsLivros);
				double preco = pedido.getPreco();
				System.out.println(preco);
				String formaEntrega = pedido.getFormaDeEntrega().toString();
				String formaPagamento = pedido.getFormaDePagamento().toString();
				String obs = pedido.getObs();
				obs = obs.concat(" ");
				String status = pedido.getStatus().toString();
				String statusDoPagamento = pedido.getStatusDoPagamento().toString();
				String statusDaEntrega = pedido.getStatusDaEntrega().toString();
				
				String sql = "INSERT INTO PEDIDOS (ID, CLIENTE, PACOTE, IDS_DOS_LIVROS, PRECO, FORMA_DE_ENTREGA, "
						+ "FORMA_DE_PAGAMENTO, OBS, STATUS, STATUS_DA_ENTREGA, STATUS_DO_PAGAMENTO)" +
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
						"'" + statusDoPagamento + "'" + ");";
				stmt.executeUpdate(sql);
			}
			connection.commit();
			stmt.close();
			connection.close();
			} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
}
