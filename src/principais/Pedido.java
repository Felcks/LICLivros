package principais;

import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import utilidades.FormaDeEntrega;
import utilidades.FormaDePagamento;
import utilidades.Status;
import utilidades.StatusDaEntrega;
import utilidades.StatusDoPagamento;

public class Pedido {
	
	private int id;
	private Cliente cliente;
	private Pacote pacote;
	private int[] idsDosLivrosComprados;
	private Double preco;
	private FormaDeEntrega formaDeEntrega;
	private FormaDePagamento formaDePagamento;
	private TipoPedido tipoPedido;
	private Status status;
	private StatusDoPagamento statusDoPagamento;
	private StatusDaEntrega statusDaEntrega;
	private String data;
	
	public static Pedido pedidoAtual;
	public static TipoPedido tipoProximoPedido = TipoPedido.NORMAL;
	
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
			this.setStatus(Status.valueOf(rs.getString("STATUS")));
			this.setStatusDoPagamento(StatusDoPagamento.valueOf(rs.getString("STATUS_DO_PAGAMENTO")));
			this.setStatusDaEntrega(StatusDaEntrega.valueOf(rs.getString("STATUS_DA_ENTREGA")));
			this.data = rs.getString("DATA");
			this.setTipoPedido(TipoPedido.valueOf(rs.getString("OBS")));
		}
		catch(Exception e){
		}
	}
	
	public Pedido(){}
	
	public Object[] pegarTodosParametros() throws java.lang.NullPointerException {
		Object[] object = new Object[11];
		object[0] = this.getId();
		object[1] = this.getCliente().getNome();
		
		String livros = "";
		for(int i = 0; i < this.getPacote().getLivros().size(); i++)
			if(i < this.getIdsDosLivrosComprados().length)
				if(this.getIdsDosLivrosComprados()[i] >= 0)
					livros += this.getPacote().getLivros().get(i).getNome() + ", ";
		
		object[2] = livros; 
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (this.getPreco());
		object[3] = formatado;
		object[4] = this.getFormaDeEntrega().getNome();
		object[5] = this.getFormaDePagamento().getNome();
		System.out.println(this.cliente.getNome());
		object[6] = this.getStatusDaEntrega().getNome();
		object[7] = this.getStatusDoPagamento().getNome();
		object[8] = this.getStatus().getNome();
		object[9] = this.getData();
		object[10] = this.getTipoPedido().toString();
		
		return object;
	}
	
	public Pedido(String stEntrega, String stPagamento){
		this.statusDaEntrega = StatusDaEntrega.getStatusPeloNome(stEntrega);
		this.statusDoPagamento = StatusDoPagamento.getStatusPeloNome(stPagamento);
	}
	
	public List<Livro> getLivrosComprados(){
		List<Livro> livrosComprados = new ArrayList<Livro>();
		List<Livro> livrosDoPacote = this.pacote.getLivros();
		List<Integer> idsEmLista = new ArrayList<Integer>();
		//Primeiro passo os IdsPara uma lista
		for(int i = 0; i < idsDosLivrosComprados.length; i++)
			idsEmLista.add(idsDosLivrosComprados[i]);
		//Depois verifica-se usando contains se o ID de qualquer livro do pacote contém nessa lista de idsComprados
		for(int i = 0; i < livrosDoPacote.size(); i++)
			if(idsEmLista.contains(livrosDoPacote.get(i).getId()))
					livrosComprados.add(livrosDoPacote.get(i));
			
		return livrosComprados;
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
		
		if(this.statusDoPagamento == StatusDoPagamento.PAGO){
			if(this.statusDaEntrega != null){
				if(this.statusDaEntrega == StatusDaEntrega.ENTREGUE)
					this.setStatus(Status.PRONTO);
			}
		}
		
		if(this.statusDoPagamento == StatusDoPagamento.CANCELADO){
			if(this.statusDaEntrega != null){
				if(this.statusDaEntrega == StatusDaEntrega.CANCELADO)
					this.setStatus(Status.CANCELADO);
			}
		}
	}

	public StatusDaEntrega getStatusDaEntrega() {
		return statusDaEntrega;
	}

	public void setStatusDaEntrega(StatusDaEntrega statusDaEntrega) {
		this.statusDaEntrega = statusDaEntrega;
	}
	
	public String getData(){
		return this.data;
	}
	public void setData(){
		Locale locale = new Locale("pt","BR");
		GregorianCalendar calendar = new GregorianCalendar(); 
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		
		String concatHora = (hour > 9) ? "" : "0";
		String concatDay = (day > 9) ? "" : "0";
		String concatMonth = (month > 9) ? "" : "0";
		String concatMin = (min > 9) ? "" : "0";
		
		this.data = concatDay + day + "/" + concatMonth + month + "/" + year + " - " + concatHora + hour + ":" + concatMin + min + "h";
		System.out.println(data);
	}

	public TipoPedido getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(TipoPedido tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

}
