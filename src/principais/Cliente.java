package principais;

public class Cliente implements Comparable<Cliente> {
	
	private int id;
	private String nome;
	private String bairro;
	private String rua;
	private String complemento;
	private String telefone;
	private String celular;
	
	public Cliente(int id, String nome, String bairro, String rua, String complemento, String telefone, String celular){
		this.id = id;
		this.nome = nome;
		this.bairro = bairro;
		this.rua = rua;
		this.complemento = complemento;
		this.telefone = telefone;
		this.celular = celular;
	}
	
	public Cliente(String[] info){
		this.id = ClienteManager.getInstance().getTodosClientes().size();
		this.nome = info[1];
		this.bairro = info[2];
		this.rua = info[3];
		this.complemento = info[4];
		this.telefone = info[5];
		this.celular = info[6];
	}

	public Cliente(){
		this.id = -1;
	}
	
	public Boolean isValidId(){
		if(id != -1)
			return true;
		else
			return false;
	}
	
	public Boolean isValidCliente(){
		Object[] object = pegarTodosParametros();
		
		if(this.getId() == -1)
			return false;
		
		for(int i = 1; i < object.length - 1; i++){
			if(object[i].toString().length() == 0)
				return false;
		}
		
		return true;
		
	}
	
	public Object[] pegarTodosParametros(){
		Object[] object = new Object[7];
		object[0] = this.getId();
		object[1] = this.getNome();
		object[2] = this.getBairro();
		object[3] = this.getRua();
		object[4] = this.getComplemento();
		object[5] = this.getTelefone();
		object[6] = this.getCelular();
		return object;
	}
	public void setarTodosParametros(Object[] object){
		this.setNome(object[1].toString());
		this.setBairro(object[2].toString());
		this.setRua(object[3].toString());
		this.setComplemento(object[4].toString());
		this.setTelefone(object[5].toString());
		this.setCelular(object[6].toString());
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Override
	public int compareTo(Cliente cliente) {
		System.out.println( this.getNome().compareToIgnoreCase(cliente.getNome()));
		return this.getNome().compareToIgnoreCase(cliente.getNome());
		
	}

}
