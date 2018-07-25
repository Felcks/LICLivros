package gui;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import principais.ClienteManager;
import principais.EditoraManager;
import principais.EscolaManager;
import principais.EstoqueManager;
import principais.PacoteManager;
import principais.PedidoManager;
import utilidades.Screen;

public class GUIManager{
	private JFrame janela;
	private JPanel cards;
	private TelaInicial telaInicial;
	private TelaEstoque telaEstoque;
	private TelaPacote telaPacote;
	private TelaCliente telaCliente;
	private TelaEditora telaEditora;
	private TelaEscola telaEscola;
	private TelaPedidoCliente telaPedidoCliente;
	private TelaPedidoPacote telaPedidoPacote;
	private TelaPedidoPacoteAvulso telaPedidoPacoteAvulso;
	private TelaPedidoFinalizacao telaPedidoFinalizacao;
	private TelaPedidoUnico telaPedidoUnico;
	private TelaPedidoUnicoAvulso telaPedidoUnicoAvulso;
	private TelaPedido telaPedido;
	private TelaInicial2 telaInicial2;
	private TelaRelatorios telaRelatorios;
	private TelaCredito telaCredito;
	
	public GUIManager(){
		this.criarJanela();
		this.criarCards();
		this.createMenuBar();

		this.prepararComponentes();
		
		this.telaInicial = new TelaInicial(this);
		this.telaEstoque = new TelaEstoque(this);
		this.telaPacote = new TelaPacote(this);
		this.telaCliente = new TelaCliente(this);
		this.telaEditora = new TelaEditora(this);
		this.telaEscola = new TelaEscola(this);
		this.telaPedidoCliente = new TelaPedidoCliente(this);
		this.telaPedidoPacote = new TelaPedidoPacote(this);
		this.telaPedidoFinalizacao = new TelaPedidoFinalizacao(this);
		this.telaPedidoUnico = new TelaPedidoUnico(this);
		this.telaPedidoUnicoAvulso = new TelaPedidoUnicoAvulso(this);
		this.telaPedido = new TelaPedido(this);
		this.telaInicial2 = new TelaInicial2(this);
		this.telaRelatorios = new TelaRelatorios(this);
		this.telaCredito = new TelaCredito(this);
		this.cards.add(this.telaEstoque, "telaEstoque");
		this.cards.add(this.telaPacote, "telaPacote");
		this.cards.add(this.telaCliente, "telaCliente");
		this.cards.add(this.telaEditora, "telaEditora");
		this.cards.add(this.telaPedidoCliente, "telaPedidoCliente");
		//this.cards.add(this.telaPedidoPacote, "telaPedidoPacote");
		//this.cards.add(this.telaPedidoPacoteAvulso, "telaPedidoPacoteAvulso");
		this.cards.add(this.telaPedidoFinalizacao, "telaPedidoFinalizacao");
		this.cards.add(this.telaEscola, "telaEscola");		
		this.cards.add(this.telaPedido, "telaPedido");
		this.cards.add(this.telaPedidoUnico, "telaPedidoUnico");
		this.cards.add(this.telaPedidoUnicoAvulso, "telaPedidoUnicoAvulso");
		this.cards.add(this.telaInicial, "telaInicial");
		this.cards.add(this.telaRelatorios, "telaRelatorios");
		this.cards.add(this.telaCredito, "telaCredito");
		this.cards.add(this.telaInicial2, "telaInicial2");
		this.mudarParaTela("telaInicial2");
		
	}
	
	private void criarJanela(){
		this.janela = new JFrame("Livraria");
		this.janela.setSize(Screen.width, Screen.height);
		this.janela.setResizable(true);
		this.janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.janela.setVisible(true);
		this.janela.setTitle("LIC - LIVROS IDEIAS CULTURA");
		this.janela.setLocationRelativeTo(null);
	}
	
	private void criarCards(){
		this.cards = new JPanel(new CardLayout());
		this.janela.getContentPane().add(this.cards, BorderLayout.CENTER);
	}
	
	public void mudarParaTela(String tela){
		// ESSE IF AQUI AINDA É RUIM. MAS FOI A MELHOR FORMA QUE ENCONTREI. PELO MENOS O MÉTODO ESTÁ NA INTERFACE.
		if(tela.equals("telaCliente")){
			this.telaCliente.prepararComponentes();
		}
		else if(tela.equals("telaPedidoCliente")){
			this.telaPedidoCliente.prepararComponentes();
		}
		else if(tela.equals("telaEstoque")){
			this.telaEstoque.prepararComponentes();
		}
		else if(tela.equals("telaEditora")){
			this.telaEditora.prepararComponentes();
		}
		else if(tela.equals("telaEscola")){
			this.telaEscola.prepararComponentes();
		}
		else if(tela.equals("telaPacote")){
			this.telaPacote.prepararComponentes();
		}
		else if(tela.equals("telaPedidoPacote")){
			this.telaPedidoPacote.prepararComponentes();
		}
		else if(tela.equals("telaPedidoPacoteAvulso")){
			this.telaPedidoPacoteAvulso.prepararComponentes();
		}
		else if(tela.equals("telaPedido")){
			this.telaPedido.prepararComponentes();
		}
		else if(tela.equals("telaPedidoFinalizacao")){
			this.telaPedidoFinalizacao.prepararComponentes();
		}
		else if(tela.equals("telaPedidoUnico")){
			this.telaPedidoUnico.prepararComponentes();
		}
		else if(tela.equals("telaPedidoUnicoAvulso")){
			this.telaPedidoUnicoAvulso.prepararComponentes();
		}
		
		CardLayout cl = (CardLayout)(this.cards.getLayout());
		cl.show(cards, tela);
	}
	
	public JPanel getCards(){
		return this.cards;
	}
	
	public JFrame getJanela(){
		return this.janela;
	}
	
	private void prepararComponentes(){
		ClienteManager.getInstance().getTodosClientesDoBD();
		EstoqueManager.getInstance().getLivrosDoBancoDeDados();
		EditoraManager.getInstance().getTodasEditorasDoBD();
		EscolaManager.getInstance().getTodasEscolasDoBD();
		PacoteManager.getInstance().getTodosOsPacotesDoBD();
		PedidoManager.getInstance().getTodosPedidosBD();
	}
	
	public TelaPedidoCliente getTelaPedidoCliente(){
		return this.telaPedidoCliente;
	}
	
	public TelaPedidoUnico getTelaPedidoUnico(){
		return this.telaPedidoUnico;
	}
	
	public TelaPedidoUnicoAvulso getTelaPedidoUnicoAvulso(){
		return this.telaPedidoUnicoAvulso;
	}
	
	private void createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
	    janela.setJMenuBar(menuBar);
	    
	    JMenu register = new JMenu("Registrar");
	    JMenu about = new JMenu("Sobre");
	    JMenu pedido = new JMenu("Pedido");
	    JMenu estoque = new JMenu("Estoque");
	    JMenu relatorio = new JMenu("Relatórios");
	    menuBar.add(register);
	    menuBar.add(pedido);
	    menuBar.add(estoque);
	    menuBar.add(relatorio);
	    menuBar.add(about);
	    
	    JMenuItem menuItem0 = new JMenuItem("Livros");
	    menuItem0.setIcon(new ImageIcon("Images/Books.png"));
	    JMenuItem menuItem1 = new JMenuItem("Editoras");
	    menuItem1.setIcon(new ImageIcon("Images/Editora.png"));
	    JMenuItem menuItem2 = new JMenuItem("Escolas");
	    menuItem2.setIcon(new ImageIcon("Images/Escola.png"));
	    JMenuItem menuItem3 = new JMenuItem("Pacote de Livros");
	    menuItem3.setIcon(new ImageIcon("Images/Pacote.png"));
		JMenuItem menuItem4 = new JMenuItem("Clientes");
		menuItem4.setIcon(new ImageIcon("Images/Cliente.png"));
		register.add(menuItem4);
	    register.add(menuItem0);
	    register.add(menuItem1);
	    register.add(menuItem2);
	    register.add(menuItem3);
	    
	    JMenuItem menuItemFazerPedido = new JMenuItem("Fazer Pedido");
	    menuItemFazerPedido.setIcon(new ImageIcon("Images/Pedido2.png"));
	    JMenuItem menuItemFazerPedidoAvulso = new JMenuItem("Fazer Pedido Avulso");
	    menuItemFazerPedidoAvulso.setIcon(new ImageIcon("Images/Pedido2.png"));
	    JMenuItem menuItemVerPedidos = new JMenuItem("Ver Pedidos");
	    menuItemVerPedidos.setIcon(new ImageIcon("Images/Pedido.png"));
	    pedido.add(menuItemFazerPedido);
	    pedido.add(menuItemFazerPedidoAvulso);
	    pedido.add(menuItemVerPedidos);
	    
	    JMenuItem menuItemCredit = new JMenuItem("Créditos");
	    menuItemCredit.setIcon(new ImageIcon("Images/Coffe.png"));
	    about.add(menuItemCredit);
	    
	    JMenuItem menuItemEstoque = new JMenuItem("Ver Estoque");
	    menuItemEstoque.setIcon(new ImageIcon("Images/Books.png"));
	    estoque.add(menuItemEstoque);
	    
	    JMenuItem menuItemRelatorio = new JMenuItem("Imprimir relatórios");
	    menuItemRelatorio.setIcon(new ImageIcon("Images/Relatorio.png"));
	    relatorio.add(menuItemRelatorio);
	    
	    menuItem0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mudarParaTela("telaEstoque");	
			}
		});
	    
	    menuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mudarParaTela("telaEditora");	
			}
		});
	    
	    menuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mudarParaTela("telaEscola");	
			}
		});
	    
	    menuItem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mudarParaTela("telaPacote");
			}
		});

	    menuItem4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mudarParaTela("telaCliente");
			}
		});
	    
	    menuItemFazerPedido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mudarParaTela("telaPedidoUnico");
			}
		});
	    
	    menuItemVerPedidos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mudarParaTela("telaPedido");
			}
		});
	    
	    menuItemEstoque.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mudarParaTela("telaEstoque");
			}
		});
	    
	    menuItemRelatorio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mudarParaTela("telaRelatorios");
			}
		});
	    
	    menuItemCredit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mudarParaTela("telaCredito");
			}
		});
	    
	    menuItemFazerPedidoAvulso.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mudarParaTela("telaPedidoUnicoAvulso");
			}
		});
	}
	
}
