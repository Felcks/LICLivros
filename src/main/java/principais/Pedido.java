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
	private int[] qtdDosLivrosComprados;
	private Double preco;
	private Double precoNormal;
	private int desconto;
	private FormaDePagamento formaDePagamento;
	private TipoPedido tipoPedido;
	private Status status;
	private String obs;
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
			
			Cliente c = new Cliente();
			c.setNome(rs.getString("CLIENTE"));
			this.setCliente(c);
			
			this.setPacote(PacoteManager.getInstance().getPacotePeloId(rs.getInt("PACOTE")));
			this.setIdsDosLivrosComprados(rs.getString("IDS_DOS_LIVROS"));
			this.setQtdDosLivrosCompradosEmString(rs.getString("QTD_DOS_LIVROS"));
			this.setPreco(rs.getDouble("PRECO"));
			
			try{
				this.setFormaDePagamento(FormaDePagamento.valueOf(rs.getString("FORMA_DE_PAGAMENTO")));
			}catch(java.lang.NullPointerException e){ /*Algo de errado*/ }
			
			try{
				this.setStatus(Status.valueOf(rs.getString("STATUS")));
			}catch(java.lang.NullPointerException e){ /*Algo de errado*/  }
			
			this.obs = rs.getString("OBS");
			this.data = rs.getString("DATA");
			
			try{
				this.setTipoPedido(TipoPedido.valueOf(rs.getString("TIPO")));
			}catch(java.lang.NullPointerException e){ /*Algo de errado*/  }
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
		if(this.getTipoPedido() == TipoPedido.NORMAL){
			for(int i = 0; i < this.getPacote().getLivros().size(); i++)
				if(i < this.getIdsDosLivrosComprados().length)
					if(this.getIdsDosLivrosComprados()[i] >= 0)
						livros += this.getPacote().getLivros().get(i).getNome() + ", ";
		}
		else{
			for(int i = 0; i < idsDosLivrosComprados.length; i++){
				int id = idsDosLivrosComprados[i];
				if(id >= 0)
					livros += EstoqueManager.getInstance().getLivroPeloId(id).getNome();
			}
		}
		
		object[2] = livros; 
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (this.getPreco());
		object[3] = formatado;
		
		object[4] = this.getFormaDePagamento().getNome();
		object[5] = this.getStatus().getNome();
		object[6] = this.getTipoPedido();
		object[7] = this.getData();
		
		
		return object;
	}
	
	public Pedido(String stEntrega, String stPagamento){
		
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
	
	public String getQtdDosLivrosCompradosEmString(){
		String s = "";
		for(int i = 0; i < this.getQtdDosLivrosComprados().length; i++){
			s = s.concat(this.getQtdDosLivrosComprados()[i] + "_");
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
	
	public void setQtdDosLivrosCompradosEmString(String qtdDosLivrosCompradosEmString){
		String number = "";
		int currentIndex = 0;
		int tamanho = 0;
		
		for(int i = 0; i < qtdDosLivrosCompradosEmString.length(); i++){
			if(qtdDosLivrosCompradosEmString.charAt(i) == '_')
			{
				tamanho++;
			}
		}
		this.qtdDosLivrosComprados = new int[tamanho];
		
		for(int i = 0; i < qtdDosLivrosCompradosEmString.length(); i++){
			if(qtdDosLivrosCompradosEmString.substring(i, i+1).equals("_"))
			{
				int n = Integer.parseInt(number);
				this.qtdDosLivrosComprados[currentIndex] = n;
				number = "";
				currentIndex++;
				
				//System.out.println();
			}
			else{
				number = number.concat(qtdDosLivrosCompradosEmString.substring(i, i+1));
			}
		}
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
		//System.out.println(data);
	}

	public TipoPedido getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(TipoPedido tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public Double getPrecoNormal() {
		return precoNormal;
	}

	public void setPrecoNormal(Double precoNormal) {
		this.precoNormal = precoNormal;
	}

	public int getDesconto() {
		return desconto;
	}

	public void setDesconto(int desconto) {
		this.desconto = desconto;
	}

	public int[] getQtdDosLivrosComprados() {
		return qtdDosLivrosComprados;
	}

	public void setQtdDosLivrosComprados(int[] qtdDosLivrosComprados) {
		this.qtdDosLivrosComprados = qtdDosLivrosComprados;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

}
