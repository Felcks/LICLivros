package principais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.JavaConnection;
import bd.OperacoesClientes;

public class ClienteManager
{
	private static ClienteManager clienteManager;
	private List<Cliente> clientes;
	private Statement stmt;
	private Connection conn = null;
	
	private ClienteManager(){
		this.clientes = new ArrayList<Cliente>();	
	}
	
	public static ClienteManager getInstance(){
		if(clienteManager == null)
			clienteManager = new ClienteManager();
		
		return  clienteManager;
	}

	
	public void getTodosClientesDoBD(){
		try{
			JavaConnection.getInstance().ConnectBd();
			conn = JavaConnection.getInstance().connection;
			stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM CLIENTES");
			this.clientes.clear();
			while (resultSet.next()){
				Cliente cliente = new Cliente(resultSet);
				this.clientes.add(cliente);
			}
			resultSet.close();
			stmt.close();
		} catch(Exception e){}
	}
	
	
	
	public List<String> getTodosNomesClientes(){
		List<String> todosNomes = new ArrayList<String>();
		for(int i = 0; i < this.clientes.size(); i++){
			todosNomes.add(clientes.get(i).getNome());
		}
		
		return todosNomes;
	}
	public List<Cliente> getTodosClientes(){
		return this.clientes;
	}
	public Cliente getClientePeloNome(String nome){
		Cliente cliente = new Cliente();
		for(Cliente c : this.clientes)
			if(c.getNome().equals(nome))
				cliente = c;
		
		return cliente;
	}
	public void adicionarNovoCliente(Cliente cliente){
		this.clientes.add(cliente);
	}
	public void removerCliente(int id){
		this.clientes.remove(this.getIndexPeloId(id));
	}
	public void atualizarCliente(int index, Cliente cliente){
		this.clientes.set(index, cliente);	
	}
	public void reorganizarLista(){
		for(int i= 0; i < this.clientes.size(); i++){
			this.clientes.get(i).setId(i);
		}
	}
	public Cliente getClientePeloId(int id){
		Cliente c = new Cliente();
		for(int i = 0; i < this.clientes.size(); i++){
			if(this.clientes.get(i).getId() == id){
				c = this.clientes.get(i);
				break;
			}
		}
		
		return c;
	}
	
	public int getIndexPeloId(int id){
		int index = 0;
		for(int i = 0; i < this.clientes.size(); i++){
			if(this.clientes.get(i).getId() == id){
				index = this.clientes.get(i).getId();
				break;
			}
		}
		return index;
	}
	
	public void organizarEmOrdemAlfabetica(){
		for(int i = 0; i < this.clientes.size() - 1; i++){
			for(int j = i + 1; j < this.clientes.size(); j++){
				if(this.clientes.get(i).compareTo(this.clientes.get(j)) > 0 ){
					Cliente temp = clientes.get(i);
					clientes.set(i,clientes.get(j));
					clientes.set(j, temp);
				}
			}
		}
	}
	
	public void organizarEmOrdemDeId(){
		for(int i = 0; i < this.clientes.size() - 1; i++){
			for(int j = i + 1; j < this.clientes.size(); j++){
				if(this.clientes.get(i).getId() > this.clientes.get(j).getId()){
					Cliente temp = clientes.get(i);
					clientes.set(i,clientes.get(j));
					clientes.set(j, temp);
				}
			}
		}
	}
	
}
