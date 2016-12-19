package principais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.JavaConnection;

public class PedidoManager {

	private static PedidoManager pedidoManager;
	private List<Pedido> pedidos;
	private Statement stmt;
	private Connection conn = null;
	
	private PedidoManager(){
		this.pedidos = new ArrayList<Pedido>();	
	}
	
	public static PedidoManager getInstance(){
		if(pedidoManager == null)
			pedidoManager = new PedidoManager();
		
		return  pedidoManager;
	}

	
	public void getTodosPedidosBD(){
		try{
			JavaConnection.getInstance().ConnectBd();
			conn = JavaConnection.getInstance().connection;
			stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM PEDIDOS");
			this.pedidos.clear();
			while (resultSet.next()){
				Pedido pedido = new Pedido(resultSet);
				this.pedidos.add(pedido);
			}
			resultSet.close();
			stmt.close();
		} catch(Exception e){}
	}
	
	public Pedido getPedidoPeloId(int id){
		Pedido pedido = new Pedido();
		for(int i = 0; i < this.getPedidos().size(); i++){
			if(this.pedidos.get(i).getId() == id)
				return this.pedidos.get(i);
		}
		
		return pedido;
	}
	
	public void atualizarPedido(int id, Pedido pedido){
		this.pedidos.set(id, pedido);
	}
	
	public List<Pedido> getPedidos(){
		return this.pedidos;
	}
	
	public void adicionarPedido(Pedido pedido){
		this.pedidos.add(pedido);
	}
	
	public int gerarId(){
		return this.pedidos.size();
	}

}
