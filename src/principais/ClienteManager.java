package principais;

import java.util.ArrayList;
import java.util.List;

public class ClienteManager
{
	private static ClienteManager clienteManager;
	private List<Cliente> clientes;
	
	private ClienteManager(){
		this.clientes = new ArrayList<Cliente>();
		
		/*
		 * Só para teste
		 */
		this.clientes.add(new Cliente(this.clientes.size(),"Matheus", "Guadalupe", "Monteiro da Silva", "158", "", ""));
		this.clientes.add(new Cliente(this.clientes.size(),"Pedro", "Guadalupe", "Monteiro da Silva", "158", "", ""));
		this.clientes.add(new Cliente(this.clientes.size(),"c", "Guadalupe", "Monteiro da Silva", "158", "", ""));
	}
	
	public static ClienteManager getInstance(){
		if(clienteManager == null)
			clienteManager = new ClienteManager();
		
		return  clienteManager;
	}

	
	public void getTodosClientesDoBD(){
		//Pega a conexão com o BD - outro código
		//Faz o request - Pega as informações
		//Outro metodo de pegar todas as informacoes e criar clientes
		//Joga na lista
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
		Cliente c = new Cliente();
		
		return c;
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
				System.out.println(this.clientes.get(i).getNome() + "bb" + this.clientes.get(j).getNome());
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
