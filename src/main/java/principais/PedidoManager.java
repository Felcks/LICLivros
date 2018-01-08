package principais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;

import bd.JavaConnection;
import bd.Operacoes;
import bd.OperacoesPedidos;
import utilidades.Print;

public class PedidoManager {

	private static PedidoManager pedidoManager;
	private List<Pedido> pedidos;
	Operacoes operacoes;
	
	private PedidoManager(){
		this.pedidos = new ArrayList<Pedido>();
		this.operacoes = new OperacoesPedidos();
	}
	
	public static PedidoManager getInstance(){
		if(pedidoManager == null)
			pedidoManager = new PedidoManager();
		
		return  pedidoManager;
	}

	
	public void getTodosPedidosBD(){
		this.pedidos.clear();
		
		operacoes.GET_AND_SET_ALL_DATA();
	}
	
	public Operacoes getOperacoes(){
		return this.operacoes;
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
	
	public void adicionarPedidoEAbrirDoc(Pedido pedido){
		this.pedidos.add(pedido);
		try {
			Print.getInstance().printDocument(pedido);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int gerarId(){
		return this.pedidos.size();
	}

}
