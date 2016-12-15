package principais;

import java.sql.ResultSet;

import utilidades.FormaDeEntrega;
import utilidades.FormaDePagamento;
import utilidades.Status;
import utilidades.StatusDaEntrega;
import utilidades.StatusDoPagamento;

public class Pedido {
	/*escrevendo um pouco
	 * essa classe terá 1 CLIENTE - id do cliente no banco de dados!
	 * essa classe terá 1 Pacote - id do pacote no banco de dados!
	 * essa classe terá um array com os livros comprados - Varios IDs no banco de dados
	 * essa classe terá uma forma de entrega, forma de pegamento e string obs
	 */
	
	private int id;
	private Cliente cliente;
	private Pacote pacote;
	private int[] idsDosLivrosComprados;
	private Double preco;
	private FormaDeEntrega formaDeEntrega;
	private FormaDePagamento formaDePagamento;
	private String obs;
	private Status status;
	private StatusDoPagamento statusDoPagamento;
	private StatusDaEntrega statusDaEntrega;
	//private date hehe faer isso depois
	
	public static Pedido pedidoAtual;
	
	public Pedido(Cliente cliente){
		this.setCliente(cliente);
	}
	
	public static void concluirPedido(){
		//Jogar pra base de dados
	}
	
	public Pedido(ResultSet rs){
		try{
			this.setId(rs.getInt("ID"));
			this.setCliente(ClienteManager.getInstance().getClientePeloId(rs.getInt("CLIENTE")));
			this.setPacote(PacoteManager.getInstance().getPacotePeloId(rs.getInt("PACOTE")));
			this.setIdsDosLivrosComprados(rs.getString("IDS_DOS_LIVROS"));
			this.setPreco(rs.getDouble("PRECO"));
			this.setFormaDeEntrega(FormaDeEntrega.valueOf(rs.getString("FORMA_DE_ENTREGA")));
			this.setFormaDePagamento(FormaDePagamento.valueOf(rs.getString("FORMA_DE_PAGAMENTO")));
			this.setObs(rs.getString("OBS"));
			this.setStatus(Status.valueOf(rs.getString("STATUS")));
			this.setStatusDoPagamento(StatusDoPagamento.valueOf(rs.getString("STATUS_DO_PAGAMENTO")));
			this.setStatusDaEntrega(StatusDaEntrega.valueOf(rs.getString("STATUS_DA_ENTREGA")));
		}
		catch(Exception e){}
	}
	
	public Object[] pegarTodosParametros(){
		Object[] object = new Object[10];
		object[0] = this.getId();
		object[1] = this.getCliente().getNome();
		
		String livros = "";
		for(int i = 0; i < this.getPacote().getLivros().size(); i++)
			if(this.getIdsDosLivrosComprados()[i] >= 0)
				livros += this.getPacote().getLivros().get(i).getNome() + ", ";
		
		object[2] = livros; 
		
		object[3] = this.getPreco();
		object[4] = this.getFormaDeEntrega().getNome();
		object[5] = this.getFormaDePagamento().getNome();
		object[6] = this.getObs();
		object[7] = this.getStatusDaEntrega().getNome();
		object[8] = this.getStatusDoPagamento().getNome();
		object[9] = this.getStatus().getNome();
		
		return object;
	}
	
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pacote getPacote() {
		return pacote;
	}

	public void setPacote(Pacote pacote) {
		this.pacote = pacote;
	}

	public int[] getIdsDosLivrosComprados() {
		return idsDosLivrosComprados;
	}
	public String getIdsDosLivrosCompradosEmString(){
		String s = "";
		for(int i = 0; i < this.getIdsDosLivrosComprados().length; i++){
			s = s.concat(this.getIdsDosLivrosComprados()[i] + "_");
		}
		
		return s;
	}

	public void setIdsDosLivrosComprados(int[] idsDosLivrosComprados) {
		this.idsDosLivrosComprados = idsDosLivrosComprados;
	}
	public void setIdsDosLivrosComprados(String idsDosLivrosComprados){
		String number = "";
		int currentIndex = 0;
		int tamanho = 0;
		
		for(int i = 0; i < idsDosLivrosComprados.length(); i++){
			if(idsDosLivrosComprados.charAt(i) == '_')
			{
				tamanho++;
			}
		}
		this.idsDosLivrosComprados = new int[tamanho];
		
		for(int i = 0; i < idsDosLivrosComprados.length(); i++){
			if(idsDosLivrosComprados.substring(i, i+1).equals("_"))
			{
				int n = Integer.parseInt(number);
				this.idsDosLivrosComprados[currentIndex] = n;
				number = "";
				currentIndex++;
			}
			else{
				number = number.concat(idsDosLivrosComprados.substring(i, i+1));
			}
		}
	}

	public FormaDeEntrega getFormaDeEntrega() {
		return formaDeEntrega;
	}

	public void setFormaDeEntrega(FormaDeEntrega formaDeEntrega) {
		this.formaDeEntrega = formaDeEntrega;
	}

	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public StatusDoPagamento getStatusDoPagamento() {
		return statusDoPagamento;
	}

	public void setStatusDoPagamento(StatusDoPagamento statusDoPagamento) {
		this.statusDoPagamento = statusDoPagamento;
	}

	public StatusDaEntrega getStatusDaEntrega() {
		return statusDaEntrega;
	}

	public void setStatusDaEntrega(StatusDaEntrega statusDaEntrega) {
		this.statusDaEntrega = statusDaEntrega;
	}

}
