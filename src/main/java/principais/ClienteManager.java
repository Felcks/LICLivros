package principais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.JavaConnection;
import bd.Operacoes;
import bd.OperacoesClientes;
import bd.OperacoesLivros;

public class ClienteManager
{
	private static ClienteManager clienteManager;
	private List<Cliente> clientes;
	private OperacoesClientes operacoes;
	
	private ClienteManager(){
		this.clientes = new ArrayList<Cliente>();	
		this.operacoes = new OperacoesClientes();
	}
	
	public static ClienteManager getInstance(){
		if(clienteManager == null)
			clienteManager = new ClienteManager();
		
		return  clienteManager;
	}

	
	public void getTodosClientesDoBD(){
		this.clientes.clear();
		
		operacoes.GET_AND_SET_ALL_DATA();
	}
	
	public List<String> getTodosNomesClientes(){

		return this.operacoes.GET_ALL_NOMES();
	}
	
	public Operacoes getOperacoes(){
		return this.operacoes;
	}
	public List<Cliente> getTodosClientes(){
		return this.operacoes.GET_ALL();
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

	public void inserirClienteNoBD(Cliente cliente){
		this.operacoes.INSERT_DATA(cliente);
	}

	public void removerCliente(int id){
		//this.clientes.remove(this.getIndexPeloId(id));
		this.operacoes.REMOVER(id);
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

		Cliente c = this.operacoes.GET_BY_ID(id);
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
